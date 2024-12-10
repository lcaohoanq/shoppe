package com.lcaohoanq.shoppe.base.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseLocation extends BaseEntity {
 
    private String name;
    private String address;
    private String city;
    private String country;
    
}
