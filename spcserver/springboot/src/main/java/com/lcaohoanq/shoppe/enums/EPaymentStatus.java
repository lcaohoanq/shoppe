package com.lcaohoanq.shoppe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    REFUNDED("REFUNDED"),
    ALL("ALL"); //for searching

    private final String status;
}
