package com.lcaohoanq.shoppe.base.entity;

import com.lcaohoanq.shoppe.enums.Country;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public class BaseLocation extends BaseEntity {
 
    private String address;
    private String city;
    private Country country;
    
}