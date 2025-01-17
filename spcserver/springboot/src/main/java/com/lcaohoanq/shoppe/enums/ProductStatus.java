package com.lcaohoanq.shoppe.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {

    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    REJECTED("REJECTED");

    private final String status;

}
