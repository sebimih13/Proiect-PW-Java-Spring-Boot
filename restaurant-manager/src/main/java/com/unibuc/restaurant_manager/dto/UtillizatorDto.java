package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UtillizatorDto {

    @NotBlank(message = "username is required and cannot be blank")
    private String username;

    @NotBlank(message = "password is required and cannot be blank")
    private String password;

    @NotBlank(message = "nume is required and cannot be blank")
    private String nume;

    @NotBlank(message = "prenume is required and cannot be blank")
    private String prenume;

    @NotBlank(message = "email is required and cannot be blank")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "nrTelefon is required and cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "nrTelefon must be exactly 10 digits")
    private String nrTelefon;

}
