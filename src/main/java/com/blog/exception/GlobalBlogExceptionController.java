package com.blog.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jboss.logging.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.ServletException;

@RestControllerAdvice
public class GlobalBlogExceptionController {
  private final Logger logger = Logger.getLogger(GlobalBlogExceptionController.class);

  @ExceptionHandler(NoSuchMethodError.class)
  public ResponseEntity<?> handleNoSuchMethodError(NoSuchMethodError ex) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<?> handleBlogException(NoResourceFoundException ex) {
    CustomException customException =
        CustomException.builder()
            .errorCode(HttpStatus.NOT_FOUND.value())
            .errorMessage(ex.getMessage())
            .field(ex.getResourcePath())
            .statusCode(ex.getStatusCode().value())
            .timestamp(LocalDateTime.now())
            .build();

    return ResponseEntity.badRequest().body(new ExceptionResponseDTO(List.of(customException)));
  }

  @ExceptionHandler(ServletException.class)
  public ResponseEntity<?> handleServletException(ServletException ex) {
    Throwable rootCause = ex.getRootCause();
    logger.info("rootCause--> "+ rootCause.getMessage());
    // Handle other wrapped exceptions or return a generic error
    CustomException customException = CustomException.builder()
        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .errorMessage("Internal Server Error")
        .timestamp(LocalDateTime.now())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ExceptionResponseDTO(List.of(customException)));
  }


  @ExceptionHandler(BlogException.class)
  public ResponseEntity<?> handleBlogException(BlogException ex) {

    logger.info("ex"+ ex.getMessage());
    CustomException customException =
        CustomException.builder()
            .errorCode(ex.getStatusCode())
            .errorMessage(ex.getMessage())
            .field(ex.getField())
            .statusCode(ex.getStatusCode())
            .timestamp(ex.getTimestamp())
            .build();

    return ResponseEntity.badRequest().body(new ExceptionResponseDTO(List.of(customException)));
  }

  /**
   * The function handles validation errors in a Spring application by logging the errors and
   * constructing a list of custom exceptions to be returned in the response.
   *
   * @param ex The `ex` parameter in your `handleValidationErrors` method of the `@ExceptionHandler`
   *     annotation represents the `HandlerMethodValidationException` that is being caught by this
   *     exception handler. This exception is typically thrown when validation errors occur during
   *     the processing of a request in a Spring MVC controller method.
   * @return A list of CustomException objects containing error code, error message, and field
   *     information for each validation error encountered. This list is returned in a
   *     ResponseEntity with HTTP status code 401 (UNAUTHORIZED).
   */
  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<?> handleValidationErrors(HandlerMethodValidationException ex) {
    logger.info("inside handle validation error.");
    logger.debug("Printing HandlerMethodValidationException Error ", ex);
    int errorCode = HttpStatus.BAD_REQUEST.value();
    List<?> errList =
        ex.getAllErrors().stream()
            .map(
                error ->
                    CustomException.builder()
                        .errorCode(errorCode)
                        .errorMessage(error.getDefaultMessage())
                        .field(error.toString().substring(0, error.toString().indexOf(";")))
                        .build())
            .toList();

    logger.debug("response List in handleValidationErrors " + errList);
    return new ResponseEntity<>(errList, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleValidationErrors(
      HttpMessageNotReadableException ex, WebRequest request) {
    logger.info("inside handle JSON parsing error.");
    // logger.info("inside handle JSON parsing error."+ request.getContextPath());
    logger.debug("Printing HttpMessageNotReadableException Error ", ex);
    int errorCode = HttpStatus.BAD_REQUEST.value();

    CustomException customException =
        CustomException.builder()
            .errorCode(errorCode)
            .errorMessage(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .build();

    logger.debug("response List in HttpMessageNotReadableException " + customException);
    return new ResponseEntity<>(
        List.of(customException), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("timestamp", System.currentTimeMillis());
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("error", "Bad Request");
    response.put("message", "Validation failed");
    response.put("path", ex.getParameter().getParameterName()); // Add the path if needed

    List<FieldError> errors = ex.getBindingResult().getFieldErrors();

    response.put(
        "errors",
        errors.stream()
            .map(
                error -> {
                  Map<String, Object> errorDetails = new HashMap<>();
                  errorDetails.put("field", error.getField());
                  errorDetails.put("rejectedValue", error.getRejectedValue());
                  errorDetails.put("message", error.getDefaultMessage());
                  return errorDetails;
                })
            .toList());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
