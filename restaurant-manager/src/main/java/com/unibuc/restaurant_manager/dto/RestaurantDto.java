package com.unibuc.restaurant_manager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public final class RestaurantDto {

    @NotBlank(message = "name is required and cannot be blank")
    private String name;

    @Min(value = 1, message = "stars must be at least 1")
    @Max(value = 5, message = "stars cannot be more than 5")
    private Integer stars;

    @NotBlank(message = "city is required and cannot be blank")
    private String city;

    @NotBlank(message = "address is required and cannot be blank")
    private String address;

    @NotBlank(message = "phoneNumber is required and cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "phoneNumber must be exactly 10 digits")
    private String phoneNumber;

}
