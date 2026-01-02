package com.unibuc.restaurant_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class ManagerDto extends EmployeeDto {

    private String educationLevel;

}
