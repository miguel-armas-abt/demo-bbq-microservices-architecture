#!/bin/bash

source ./../commons.sh
SCRAPING_COMPONENT="jenkins-settings-scraping-v1"
JENKINS_SCRAPING_FILE="./../web-scraping/$SCRAPING_COMPONENT/src/main/resources/application.yaml"

scraping_jenkins() {
  java_home=$(./../local/local-value-searcher.sh "java_home")
  export JAVA_HOME=$java_home
  java="$java_home/bin/java"

  component_path="./../web-scraping/$SCRAPING_COMPONENT"

  command="$java -jar ./target/$SCRAPING_COMPONENT-0.0.1-SNAPSHOT.jar"

  echo "$(get_timestamp) .......... $command" >> "./../../$LOG_FILE"
  cd $component_path
  eval $command
  cd "./../../docker/"
}

get_password() {
  container_name="bbq-jenkins"
  password=$(docker logs $container_name 2>&1 | grep -o "^[0-9a-f]\{32\}$")

  if [ -n "$password" ]; then
    echo -e "${CYAN}Jenkins password:${NC} $password${NC}"
    sed -i "s|\(unlockPassword:\s*\).*|\1$password|" "$JENKINS_SCRAPING_FILE"
  else
    echo -e "${RED}Error getting Jenkins password${NC}"
  fi
}

handle_deploy() {
  #./jenkins-processor.sh build
  ./jenkins-processor.sh up
  ./jenkins-processor.sh wait
  get_password
  #scraping_jenkins
}

handle_deploy