package com.unibuc.restaurant_manager.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto extends UtillizatorDto {

    private String adresa;

}
