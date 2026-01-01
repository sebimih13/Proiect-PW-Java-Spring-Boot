package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @Column(name = "id_restaurant")
    private Integer id;

    private String nume;
    private Integer nrStele;
    private String oras;
    private String strada;
    private String nrTelefon;

    @OneToMany(mappedBy = "restaurant")
    private List<Angajat> angajati;

    @OneToMany(mappedBy = "restaurant")
    private List<Comanda> comenzi;

}
