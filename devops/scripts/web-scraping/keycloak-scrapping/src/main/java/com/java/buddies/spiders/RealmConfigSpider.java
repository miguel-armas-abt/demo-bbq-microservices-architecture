package com.java.buddies.spiders;

import com.google.inject.Inject;
import com.java.buddies.service.DriverProviderService;
import com.java.buddies.properties.PropertiesReader;
import com.java.buddies.properties.configuration.Configuration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RealmConfigSpider {

  @Inject
  private final PropertiesReader propertiesReader;

  @Inject
  private final DriverProviderService driverProviderService;

  public void configRealm() throws InterruptedException {
    log.info("start {}", this.getClass().getSimpleName());
    Thread.sleep(propertiesReader.get().getConfiguration().getWaitingTimeMillis());
    getRS256Key();
    updateAccessTokenLifespan();
  }

  private void getRS256Key() {
    WebDriverWait wait = driverProviderService.getWebDriverWait();
    Configuration properties = propertiesReader.get().getConfiguration();

    WebElement keysTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Keys')]")));
    keysTab.click();

    WebElement rs256Row = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), 'RS256')]/following-sibling::td[2]")));

    String kidValue = rs256Row.getText();
    System.out.println("El valor de Kid para RS256 es: " + kidValue);
  }

  private void updateAccessTokenLifespan() {
    WebDriverWait wait = driverProviderService.getWebDriverWait();
    Configuration properties = propertiesReader.get().getConfiguration();

    WebElement keysTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), 'Tokens')]")));
    keysTab.click();

    WebElement accessTokenLifespanInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("accessTokenLifespan")));
    accessTokenLifespanInput.clear();
    accessTokenLifespanInput.sendKeys(properties.getRealm().getTokenTtlMinutes());

    WebElement accessTokenLifespanUnit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("accessTokenLifespanUnit")));
    Select selectUnit = new Select(accessTokenLifespanUnit);
    selectUnit.selectByVisibleText("Minutes");

    WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@kc-save]")));
    saveButton.click();
  }
}
