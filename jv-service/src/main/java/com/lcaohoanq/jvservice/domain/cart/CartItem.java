package com.lcaohoanq.jvservice.domain.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import com.lcaohoanq.jvservice.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @Id
    @SequenceGenerator(name = "cartproducts_seq", sequenceName = "cartproducts_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartproducts_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private CartItemStatus status;

    public enum CartItemStatus {

        IN_CART,
        ALL,
        WAIT_FOR_CONFIRMATION,
        WAIT_FOR_GETTING,
        IN_PROGRESS,
        DELIVERED,
        CANCELLED;

        @JsonValue
        public int toValue() {
            return ordinal();
        }
        
    }
}