package com.unibuc.restaurant_manager.dto;

import com.unibuc.restaurant_manager.validation.OnCreate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(groups = OnCreate.class, message = "username is required and cannot be blank")
    private String username;

    @NotBlank(groups = OnCreate.class, message = "password is required and cannot be blank")
    private String password;

    @NotBlank(groups = OnCreate.class, message = "firstName is required and cannot be blank")
    private String firstName;

    @NotBlank(groups = OnCreate.class, message = "lastName is required and cannot be blank")
    private String lastName;

    @NotBlank(groups = OnCreate.class, message = "email is required and cannot be blank")
    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(groups = OnCreate.class, message = "phoneNumber is required and cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "phoneNumber must be exactly 10 digits")
    private String phoneNumber;

}
