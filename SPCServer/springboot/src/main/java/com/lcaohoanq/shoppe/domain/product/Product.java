package com.lcaohoanq.shoppe.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.cart.CartItem;
import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.inventory.Inventory;
import com.lcaohoanq.shoppe.enums.ProductStatus;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    
    @Enumerated(EnumType.STRING)
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
    @JoinColumn(name = "inventory_id")
    @JsonBackReference
    private Inventory inventory;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();

}
