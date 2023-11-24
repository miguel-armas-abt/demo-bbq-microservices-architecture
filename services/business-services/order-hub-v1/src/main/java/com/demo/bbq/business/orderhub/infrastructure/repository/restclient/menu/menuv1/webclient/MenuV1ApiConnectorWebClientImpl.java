package com.demo.bbq.business.orderhub.infrastructure.repository.restclient.menu.menuv1.webclient;

import com.demo.bbq.business.orderhub.infrastructure.repository.restclient.menu.MenuApiConnector;
import com.demo.bbq.business.orderhub.infrastructure.repository.restclient.menu.dto.MenuOptionDto;
import com.demo.bbq.business.orderhub.infrastructure.repository.restclient.menu.dto.MenuOptionSaveRequestDto;
import com.demo.bbq.business.orderhub.infrastructure.repository.restclient.menu.dto.MenuOptionUpdateRequestDto;
import com.demo.bbq.business.orderhub.infrastructure.repository.restclient.properties.RestClientBaseUrlProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Component
@RequiredArgsConstructor
public class MenuV1ApiConnectorWebClientImpl implements MenuApiConnector {

  private final WebClient.Builder webClientBuilder;
  private final RestClientBaseUrlProperties properties;

  TcpClient tcpClient = TcpClient
      .create()
      .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
      .doOnConnected(connection -> {
        connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
        connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
      });

  private WebClient buildWebClient() {
    return webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
        .baseUrl(properties.getMenuV1BaseUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultUriVariables(Collections.singletonMap("url", properties.getMenuV1BaseUrl()))
        .build();
  }

  public Flux<MenuOptionDto> findByCategory(String category) {
    return buildWebClient().method(HttpMethod.GET)
        .uri(uriBuilder -> (category != null)
            ? uriBuilder.path("menu-options").queryParam("category", category).build()
            : uriBuilder.path("menu-options").build())
        .retrieve()
        .bodyToFlux(MenuOptionDto.class);
  }

  public Mono<MenuOptionDto> findByProductCode(String productCode) {
    return buildWebClient().method(HttpMethod.GET)
        .uri(uriBuilder -> uriBuilder.path("menu-options/" + productCode).build())
        .retrieve()
        .bodyToMono(MenuOptionDto.class);
  }

  public Mono<Void> save(MenuOptionSaveRequestDto menuOption) {
    return buildWebClient().method(HttpMethod.POST)
        .uri(uriBuilder -> uriBuilder.path("menu-options").build())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(menuOption))
        .retrieve()
        .bodyToMono(Void.class);
  }

  public Mono<Void> update(String productCode, MenuOptionUpdateRequestDto menuOption) {
    return buildWebClient().method(HttpMethod.PUT)
        .uri(uriBuilder -> uriBuilder.path("menu-options/" + productCode).build())
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(menuOption))
        .retrieve()
        .bodyToMono(Void.class);
  }

  public Mono<Void> delete(String productCode) {
    return buildWebClient().method(HttpMethod.DELETE)
        .uri(uriBuilder -> uriBuilder.path("menu-options/" + productCode).build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  @Override
  public boolean supports(Class<?> selectedClass) {
    return this.getClass().isAssignableFrom(selectedClass);
  }
}
