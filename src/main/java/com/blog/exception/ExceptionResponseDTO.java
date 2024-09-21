package com.blog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@EqualsAndHashCode
public class ExceptionResponseDTO {
  @JsonProperty("error")
  private List<CustomException> customExceptionFields;

  ExceptionResponseDTO(List<CustomException> customExceptionFields) {
    this.customExceptionFields = customExceptionFields;
  }

  ExceptionResponseDTO() {
    this.customExceptionFields = new ArrayList<>();
  }
}
