package com.lcaohoanq.shoppe.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.models.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
    private String image;
    private String category;
    private double price;
    private double priceBeforeDiscount;
    private int quantity;
    private int sold;
    private int view;
    private double rating;
    private String images;

}
