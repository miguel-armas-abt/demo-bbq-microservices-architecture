package com.demo.bbq.entrypoint.invoice.repository;

import static com.demo.bbq.commons.toolkit.params.filler.HeadersFiller.buildHeaders;

import com.demo.bbq.commons.properties.dto.restclient.HeaderTemplate;
import com.demo.bbq.commons.properties.ApplicationProperties;
import com.demo.bbq.entrypoint.invoice.repository.wrapper.request.PaymentSendRequestWrapper;
import com.demo.bbq.entrypoint.invoice.repository.wrapper.request.ProductRequestWrapper;
import com.demo.bbq.entrypoint.invoice.repository.wrapper.response.InvoiceResponseWrapper;
import com.demo.bbq.commons.errors.dto.ErrorDTO;
import com.demo.bbq.commons.errors.handler.external.ExternalErrorHandler;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class InvoiceRepository {

  private static final String SERVICE_NAME_INVOICE = "invoice-v1";

  private final WebClient webClient;
  private final ApplicationProperties properties;
  private final ExternalErrorHandler externalErrorHandler;

  public Mono<InvoiceResponseWrapper> generateProforma(Map<String, String> headers,
                                                       List<ProductRequestWrapper> productList) {
    return webClient.post()
        .uri(getBaseURL().concat("calculate"))
        .contentType(MediaType.APPLICATION_JSON)
        .headers(buildHeaders(properties.searchHeaderTemplate(SERVICE_NAME_INVOICE), headers))
        .body(BodyInserters.fromValue(productList))
        .retrieve()
        .onStatus(HttpStatusCode::isError, this::handleError)
        .toEntity(InvoiceResponseWrapper.class)
        .mapNotNull(HttpEntity::getBody);
  }

  public Mono<Void> sendToPay(Map<String, String> headers,
                              PaymentSendRequestWrapper paymentRequest) {
    return webClient.post()
        .uri(getBaseURL().concat("send-to-pay"))
        .contentType(MediaType.APPLICATION_JSON)
        .headers(buildHeaders(getHeaderTemplate(), headers))
        .body(BodyInserters.fromValue(paymentRequest))
        .retrieve()
        .onStatus(HttpStatusCode::isError, this::handleError)
        .toEntity(Void.class)
        .mapNotNull(HttpEntity::getBody);
  }

  private String getBaseURL() {
    return properties.searchEndpoint(SERVICE_NAME_INVOICE);
  }

  private HeaderTemplate getHeaderTemplate() {
    return properties.searchHeaderTemplate(SERVICE_NAME_INVOICE);
  }

  private Mono<? extends Throwable> handleError(ClientResponse clientResponse) {
    return externalErrorHandler.handleError(clientResponse, ErrorDTO.class, SERVICE_NAME_INVOICE);
  }
}



















