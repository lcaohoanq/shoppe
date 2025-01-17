package com.lcaohoanq.shoppe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentType {
    DEPOSIT("DEPOSIT"),
    ORDER("ORDER"),
    DRAW_OUT("DRAW_OUT");
    private final String type;
}
