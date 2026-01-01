package com.unibuc.restaurant_manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends UserDto {

    @NotNull(message = "birthDate is required and cannot be blank")
    @Past(message = "birthDate date must be in the past")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotBlank(message = "CNP is required and cannot be blank")
    @Pattern(regexp = "\\d{13}", message = "CNP must be exactly 13 digits")
    private String CNP;

    @NotBlank(message = "IDSeries is required and cannot be blank")
    @Pattern(regexp = "[A-Z]{2}", message = "IDSeries must contain exactly 2 uppercase letters")
    private String IDSeries;

    @NotBlank(message = "IDNumber is required and cannot be blank")
    @Pattern(regexp = "\\d{6}", message = "IDNumber be exactly 6 digits")
    private String IDNumber;

}
