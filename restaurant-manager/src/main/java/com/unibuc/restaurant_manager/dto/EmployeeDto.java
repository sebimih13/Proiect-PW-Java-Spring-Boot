package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class EmployeeDto extends UserDto {

    @NotBlank(message = "birthDate is required and cannot be blank")
    private LocalDate birthDate;

}
