package com.sursindmitry.repairhub.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(

    @Email(message = "invalid email address")
    String email,

    @NotBlank(message = "firstName must not be null or empty")
    String firstName,

    @NotBlank(message = "lastName must not be null or empty")
    String lastName,

    @NotBlank(message = "password must not be null or empty")
    @Size(min = 6, max = 20, message = "password length must be between 6 and 20 characters")
    String password
) {
}
