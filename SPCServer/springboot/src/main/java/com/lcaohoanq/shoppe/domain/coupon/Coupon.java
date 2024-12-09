package com.lcaohoanq.shoppe.domain.coupon;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
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
@Table(name = "coupons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coupon extends BaseEntity {

    @Id
    @SequenceGenerator(name = "coupons_seq", sequenceName = "coupons_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupons_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "active", nullable = false)
    private boolean active;

}