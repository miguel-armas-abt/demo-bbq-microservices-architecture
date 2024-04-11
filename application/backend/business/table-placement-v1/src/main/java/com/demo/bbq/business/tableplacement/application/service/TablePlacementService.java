package com.demo.bbq.business.tableplacement.application.service;

import com.demo.bbq.business.tableplacement.application.dto.tableplacement.request.MenuOrderRequestDTO;
import com.demo.bbq.business.tableplacement.application.dto.tableplacement.response.TablePlacementResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TablePlacementService {

  Mono<Void> generateTableOrder(Flux<MenuOrderRequestDTO> requestedMenuOrderList, Integer tableNumber);

  Mono<Void> cleanTable(Integer tableNumber);

  Mono<TablePlacementResponseDTO> findByTableNumber(Integer tableNumber);
}
