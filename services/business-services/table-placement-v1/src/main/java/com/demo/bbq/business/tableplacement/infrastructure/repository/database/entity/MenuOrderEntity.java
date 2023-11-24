package com.demo.bbq.business.tableplacement.infrastructure.repository.database.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_orders")
public class MenuOrderEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_code") // should be unique=true
  private String productCode;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "table_id")
  private Long tableId;
}
