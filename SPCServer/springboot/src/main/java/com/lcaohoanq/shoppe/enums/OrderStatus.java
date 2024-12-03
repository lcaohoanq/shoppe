package com.lcaohoanq.shoppe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    ALL("ALL"), // for view
    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    SHIPPING("SHIPPING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED");

    private final String status;
}
