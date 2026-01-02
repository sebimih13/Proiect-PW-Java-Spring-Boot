package com.unibuc.restaurant_manager.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "contains")
public final class Contains {

    @EmbeddedId
    private ContainsId id;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @MapsId("idPurchaseOrder")
    @JoinColumn(name = "id_purchase_order")
    private PurchaseOrder purchaseOrder;

    private Integer quantity;

    @Embeddable
    public static class ContainsId implements Serializable {

        private Integer idProduct;
        private Integer idPurchaseOrder;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof ContainsId that)) {
                return false;
            }

            return Objects.equals(idProduct, that.idProduct) && Objects.equals(idPurchaseOrder, that.idPurchaseOrder);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idProduct, idPurchaseOrder);
        }
    }

}
