package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contine")
public class Contine {

    @EmbeddedId
    private ContineId id;

    @ManyToOne
    @MapsId("idProdus")
    @JoinColumn(name = "id_produs")
    private Produs produs;

    @ManyToOne
    @MapsId("idComanda")
    @JoinColumn(name = "id_comanda")
    private Comanda comanda;

    private Integer cantitate;

    @Embeddable
    public static class ContineId implements Serializable {

        private Integer idProdus;
        private Integer idComanda;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof ContineId that)) {
                return false;
            }

            return Objects.equals(idProdus, that.idProdus) && Objects.equals(idComanda, that.idComanda);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idProdus, idComanda);
        }
    }

}
