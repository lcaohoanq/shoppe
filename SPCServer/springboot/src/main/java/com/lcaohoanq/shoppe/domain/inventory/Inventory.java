package com.lcaohoanq.shoppe.domain.inventory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import com.lcaohoanq.shoppe.base.entity.BaseLocation;
import com.lcaohoanq.shoppe.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import javax.print.event.PrintJobAttributeEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventories")
@Entity
public class Inventory extends BaseLocation {

    @Id
    @SequenceGenerator(name = "inventories_seq", sequenceName = "inventories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventories_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    
    @OneToMany(mappedBy = "inventory")
    @JsonManagedReference
    private Set<Product> products;

    private Long quantity;
    
    private Long reserved; //Reserved quantity for orders
    
    @Column(name = "reorder_point")
    private Long reorderPoint; //The quantity that triggers a reorder
    
}
