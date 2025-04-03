package com.lcaohoanq.jvservice.domain.logistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.common.enums.ShippingCarrierName;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "shipping_carriers")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCarrier extends BaseEntity {

    @Id
    @SequenceGenerator(name = "shipping_carriers_seq", sequenceName = "shipping_carriers_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_carriers_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "name", length = 100)
    private ShippingCarrierName name;
    
    @Column(name = "tracking_url")
    private String trackingUrl;
    
    @Column(name = "contact_number")
    private String contactNumber;

}
