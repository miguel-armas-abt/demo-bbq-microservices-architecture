package com.demo.bbq.repository.product;

import static com.demo.bbq.commons.restclient.headers.HeadersBuilderUtil.buildHeaders;

import com.demo.bbq.application.properties.ApplicationProperties;
import com.demo.bbq.repository.product.wrapper.ProductResponseWrapper;
import com.demo.bbq.commons.errors.dto.ErrorDTO;
import com.demo.bbq.commons.errors.handler.external.ExternalErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private static final String SERVICE_NAME = "product-v1";

  private final WebClient webClient;
  private final ApplicationProperties properties;
  private final ExternalErrorHandler externalErrorHandler;

  public Mono<ProductResponseWrapper> findByProductCode(ServerRequest serverRequest, String productCode) {
    return webClient.get()
        .uri(UriComponentsBuilder
            .fromUriString(properties.searchEndpoint(SERVICE_NAME).concat("{productCode}"))
            .buildAndExpand(productCode).toUriString())
        .headers(buildHeaders(properties.searchHeaderTemplate(SERVICE_NAME), serverRequest))
        .retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> externalErrorHandler.handleError(clientResponse, ErrorDTO.class, SERVICE_NAME))
        .toEntity(ProductResponseWrapper.class)
        .mapNotNull(HttpEntity::getBody);
  }
}

