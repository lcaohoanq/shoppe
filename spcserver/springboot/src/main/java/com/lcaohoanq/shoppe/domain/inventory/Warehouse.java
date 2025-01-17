package com.lcaohoanq.shoppe.domain.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lcaohoanq.shoppe.base.entity.BaseLocation;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouses")
@Entity
@SuperBuilder
public class Warehouse extends BaseLocation {

    @Id
    @SequenceGenerator(name = "warehouses_seq", sequenceName = "warehouses_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouses_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    
    @Enumerated(EnumType.ORDINAL)
    private WarehouseName name;
    
//    @OneToMany(mappedBy = "warehouse")
//    @JsonManagedReference
//    private HashSet<Product> products;

    @Column(name = "quantity", columnDefinition = "bigint default 0")
    private Long quantity;
    
    @Column(name = "reserved", columnDefinition = "bigint default 0")
    private Long reserved; //Reserved quantity for orders
    
    @Column(name = "reorder_point", columnDefinition = "bigint default 0")
    private Long reorderPoint; //The quantity that triggers a reorder
    
    @Getter
    @AllArgsConstructor
    public enum WarehouseName {
        NORTH("Northern warehouse"),
        CENTRAL("Central warehouse"),
        SOUTH("Southern warehouse");
        
        private final String name;

        @JsonValue
        public int toValue() {
            return ordinal();
        }
    }
    
}
