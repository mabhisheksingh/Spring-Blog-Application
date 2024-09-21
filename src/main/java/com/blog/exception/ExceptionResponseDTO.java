package com.blog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

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
