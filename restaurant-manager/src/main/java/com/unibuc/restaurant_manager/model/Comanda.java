package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comanda")
public class Comanda {

    @Id
    @Column(name = "id_comanda")
    private Integer id;

    private String status;
    private LocalDate data;
    private LocalTime ora;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_restaurant")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "comanda")
    private List<Contine> produse;

}
