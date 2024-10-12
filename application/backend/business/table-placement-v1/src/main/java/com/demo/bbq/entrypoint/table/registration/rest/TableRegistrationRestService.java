package com.demo.bbq.entrypoint.table.registration.rest;

import static org.springframework.http.MediaType.APPLICATION_NDJSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TableRegistrationRestService {

  private static final String BASE_URI = "/bbq/business/table-placement/v1";
  private final static String TABLES_RESOURCE = "/tables";

  @Bean
  public RouterFunction<ServerResponse> buildTablesRoutes(TableRegistrationHandler tableOrderHandler) {
    return nest(
        path(BASE_URI),
        route()
            .POST(TABLES_RESOURCE, accept(APPLICATION_NDJSON) , tableOrderHandler::createTable)
            .build()
    );
  }
}