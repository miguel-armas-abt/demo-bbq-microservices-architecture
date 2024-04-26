package com.demo.bbq.utils.errors.exceptions;

import com.demo.bbq.utils.errors.dto.ErrorDTO;
import com.demo.bbq.utils.errors.dto.ErrorType;
import java.io.Serial;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5488908568684510286L;

  private final ErrorDTO errorDetail;

  public BusinessException(String code) {
    super(code);
    this.errorDetail = ErrorDTO.builder()
        .type(ErrorType.BUSINESS)
        .code(code)
        .build();
  }

  public BusinessException(String code, String message) {
    super(message);
    this.errorDetail = ErrorDTO.builder()
        .type(ErrorType.BUSINESS)
        .code(code)
        .message(message)
        .build();
  }
}
