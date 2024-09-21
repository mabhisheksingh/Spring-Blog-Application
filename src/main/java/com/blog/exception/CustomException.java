package com.blog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomException {
  private String field;
  private String errorMessage;
  private Integer statusCode;
  private LocalDateTime timestamp;
  private Integer errorCode;
  private Object value; // May send StackTrace
}
