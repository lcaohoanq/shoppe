package com.lcaohoanq.shoppe.domain.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sub_inventories")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubInventory extends BaseLocation {

    @Id
    @SequenceGenerator(name = "sub_inventories_seq", sequenceName = "sub_inventories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_inventories_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    
}
