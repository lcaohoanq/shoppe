package com.lcaohoanq.shoppe.domain.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
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
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Id
    @SequenceGenerator(name = "categories_seq", sequenceName = "categories_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "category")
    private Set<Subcategory> subcategory;

}
