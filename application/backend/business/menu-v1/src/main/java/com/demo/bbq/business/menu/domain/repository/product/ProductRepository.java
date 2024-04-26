package com.demo.bbq.business.menu.domain.repository.product;

import com.demo.bbq.business.menu.application.properties.ServiceConfigurationProperties;
import com.demo.bbq.business.menu.domain.repository.product.wrapper.request.ProductSaveRequestWrapper;
import com.demo.bbq.business.menu.domain.repository.product.wrapper.request.ProductUpdateRequestWrapper;
import com.demo.bbq.business.menu.domain.repository.product.wrapper.response.ProductResponseWrapper;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private static final String SERVICE_NAME = "product-v1";

  private final RestTemplate restTemplate;
  private final ServiceConfigurationProperties properties;

  public ProductResponseWrapper findByCode(String code) {
    ResponseEntity<ProductResponseWrapper> response = restTemplate.exchange(
        getEndpoint().concat("/products/{code}"),
        HttpMethod.GET,
        null,
        ProductResponseWrapper.class,
        code
    );
    return response.getBody();
  }

  public List<ProductResponseWrapper> findByScope(String scope) {
    ResponseEntity<ProductResponseWrapper[]> response = restTemplate.exchange(
        getEndpoint().concat("/products?scope={scope}"),
        HttpMethod.GET,
        null,
        ProductResponseWrapper[].class,
        scope
    );
    return Arrays.asList(response.getBody());
  }

  public void save(ProductSaveRequestWrapper productRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ProductSaveRequestWrapper> requestEntity = new HttpEntity<>(productRequest, headers);
    restTemplate.exchange(
        getEndpoint().concat("/products"),
        HttpMethod.POST,
        requestEntity,
        Void.class
    );
  }

  public void update(String code, ProductUpdateRequestWrapper productRequest) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ProductUpdateRequestWrapper> requestEntity = new HttpEntity<>(productRequest, headers);
    restTemplate.exchange(
        getEndpoint().concat("/products/{code}"),
        HttpMethod.PUT,
        requestEntity,
        Void.class,
        code
    );
  }

  public void delete(String code) {
    restTemplate.exchange(
        getEndpoint().concat("/products/{code}"),
        HttpMethod.DELETE,
        null,
        Void.class,
        code
    );
  }

  private String getEndpoint() {
    return properties.getRestClients().get(SERVICE_NAME).getRequest().getEndpoint();
  }

}
