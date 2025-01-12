package com.lcaohoanq.shoppe.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");
    
    private final String value;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
    
}
