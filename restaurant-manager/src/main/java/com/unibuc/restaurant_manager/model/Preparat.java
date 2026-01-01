package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "preparat")
public class Preparat extends Produs {

    private Integer grame;

}
