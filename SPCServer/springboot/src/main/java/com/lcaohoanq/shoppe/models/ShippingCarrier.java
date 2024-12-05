package com.lcaohoanq.shoppe.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.ShippingCarrierName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "shipping_carriers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCarrier {

    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 100)
    private ShippingCarrierName name;

}
