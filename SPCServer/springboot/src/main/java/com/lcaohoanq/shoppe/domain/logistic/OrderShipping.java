package com.lcaohoanq.shoppe.domain.logistic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import com.lcaohoanq.shoppe.domain.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orders_shipping")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderShipping extends BaseEntity {

    @Id
    @SequenceGenerator(name = "orders_shipping_seq", sequenceName = "orders_shipping_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_shipping_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "shipping_carrier_id", nullable = false)
    private ShippingCarrier shippingCarrier;
    
    @Column(name = "tracking_number")
    private String trackingNumber;
    
    @Column(name = "shipping_fee")
    private Float shippingFee;
    
    @Column(name = "shipping_status")
    private String shippingStatus;
    
    @Column(name = "shipping_date")
    private Date shippingDate;
    
    @Column(name = "estimated_delivery")
    private Date estimatedDelivery;
    
}
