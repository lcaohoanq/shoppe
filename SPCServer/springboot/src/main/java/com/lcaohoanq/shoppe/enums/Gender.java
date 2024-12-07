package com.lcaohoanq.shoppe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");
    
    private final String value;

}
