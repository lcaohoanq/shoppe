package com.lcaohoanq.shoppe.domain.logistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.ShippingCarrierName;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
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
public class ShippingCarrier extends BaseEntity {

    @Id
    @SequenceGenerator(name = "shipping_carriers_seq", sequenceName = "shipping_carriers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_carriers_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 100)
    private ShippingCarrierName name;
    
    @Column(name = "tracking_url")
    private String trackingUrl;
    
    @Column(name = "contact_number")
    private String contactNumber;

}
