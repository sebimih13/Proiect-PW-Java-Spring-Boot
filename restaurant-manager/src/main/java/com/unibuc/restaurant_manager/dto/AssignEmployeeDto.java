package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AssignEmployeeDto {

    public enum Role {
        COOK,
        BARTENDER,
        WAITER
    }

    @NotNull(message = "employeeId cannot be null")
    private Integer employeeId;

    @NotNull(message = "Role cannot be null")
    private Role role;

}
