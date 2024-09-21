package com.blog.dto;

import com.blog.utils.constants.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserDTO {
  @Null private String id;

  @NotNull
  @NotEmpty(message = "Name should not be empty")
  private String name;

  @NotNull
  @NotEmpty(message = "Mobile number should not be empty")
  @Size(min = 10, max = 10, message = "Mobile number should be 10 digits")
  private String mobileNumber;

  @NotNull
  @NotEmpty(message = "User name should not be empty")
  @Size(min = 6, max = 20, message = "User name should be between 6 to 20 characters")
  private String userName;

  @NotNull
  @NotBlank(message = "Password should not be empty")
  @Size(min = 8, max = 10, message = "Password should be minimum 8 characters")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @NotNull
  @Email(message = "Invalid email format")
  private String email;

  @NotNull
  @JsonProperty("gender")
  private Gender gender;
}
