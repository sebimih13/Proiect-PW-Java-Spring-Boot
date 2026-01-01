package com.unibuc.restaurant_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BartenderDto extends EmployeeDto {

    private String specialization;

}
