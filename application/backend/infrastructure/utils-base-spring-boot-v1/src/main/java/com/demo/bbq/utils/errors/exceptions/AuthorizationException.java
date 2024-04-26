package com.demo.bbq.utils.errors.exceptions;

import com.demo.bbq.utils.errors.dto.ErrorDTO;
import com.demo.bbq.utils.errors.dto.ErrorType;
import java.io.Serial;
import lombok.Getter;

@Getter
public class AuthorizationException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -4701249853319723248L;

  private final ErrorDTO errorDetail;

  public AuthorizationException(String code) {
    super(code);
    this.errorDetail = ErrorDTO.builder()
        .type(ErrorType.SYSTEM)
        .code(code)
        .build();
  }

  public AuthorizationException(String code, String message) {
    super(message);
    this.errorDetail = ErrorDTO.builder()
        .type(ErrorType.SYSTEM)
        .code(code)
        .message(message)
        .build();
  }
}
