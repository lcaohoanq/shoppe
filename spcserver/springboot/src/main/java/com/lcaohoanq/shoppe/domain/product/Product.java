package com.lcaohoanq.shoppe.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import com.lcaohoanq.shoppe.domain.cart.CartItem;
import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.inventory.Warehouse;
import com.lcaohoanq.shoppe.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product extends BaseEntity {

    @Id
    @SequenceGenerator(name = "products_seq", sequenceName = "products_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    private String name;
    private String description;
    private double price;

    @Column(name = "price_before_discount")
    @JsonProperty("price_before_discount")
    private double priceBeforeDiscount;

    private int quantity;
    private int sold;
    private int view;
    private double rating;
    
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "shop_owner_id")
    private User shopOwner;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference
    private Warehouse warehouse;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<CartItem> cartItems = new ArrayList<>();

    @Getter
    @AllArgsConstructor
    public enum ProductStatus {

        UNVERIFIED("UNVERIFIED"),
        VERIFIED("VERIFIED"),
        REJECTED("REJECTED");

        private final String status;
        
        @JsonValue
        public int toValue() {
            return ordinal();
        }

    }

}
