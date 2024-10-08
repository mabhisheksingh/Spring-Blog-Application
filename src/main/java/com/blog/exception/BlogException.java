package com.blog.exception;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class BlogException extends RuntimeException {
  private String field;
  private String message;
  private Integer statusCode = HttpStatus.BAD_REQUEST.value();
  private LocalDateTime timestamp;

  public BlogException(String message) {
    super(message);
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public BlogException(String field, String message) {
    super(message);
    this.field = field;
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }

  public BlogException(String message, Integer statusCode) {
    super(message);
    this.message = message;
    this.statusCode = statusCode;
    this.timestamp = LocalDateTime.now();
  }

  public BlogException(String field, String message, Integer statusCode) {
    super(message);
    this.field = field;
    this.message = message;
    this.statusCode = statusCode;
    this.timestamp = LocalDateTime.now();
  }

  public BlogException(String field, String message, Integer statusCode, LocalDateTime localDate) {
    super(message);
    this.field = field;
    this.message = message;
    this.statusCode = statusCode;
    this.timestamp = localDate;
  }
}
