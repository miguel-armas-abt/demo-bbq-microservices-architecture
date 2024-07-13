package com.demo.bbq.entrypoint.menu.dto.request;

import com.demo.bbq.commons.doc.DocumentationConfig.DocumentationExample;
import com.demo.bbq.entrypoint.menu.constant.MenuRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuUpdateRequestDTO implements Serializable {

  @Schema(example = DocumentationExample.DESCRIPTION)
  @Size(min = 3, max = 300)
  @NotNull(message = "description cannot be null")
  private String description;

  @Schema(example = DocumentationExample.CATEGORY_MAIN_DISH)
  @Pattern(regexp = MenuRegex.CATEGORY, message = "Invalid menu option category")
  @NotNull(message = "category cannot be null")
  private String category;

  @Schema(example = DocumentationExample.UNIT_PRICE)
  @NotNull(message = "unitPrice cannot be null")
  private BigDecimal unitPrice;
}