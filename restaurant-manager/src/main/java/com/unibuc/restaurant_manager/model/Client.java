package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "client")
public class Client extends Utilizator {

    @OneToMany(mappedBy = "client")
    private List<Comanda> comenzi;

    private String adresa;

}
