package com.lcaohoanq.shoppe.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EKoiStatus {

    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    REJECTED("REJECTED"),
    SOLD("SOLD"),;

    private final String status;

}
