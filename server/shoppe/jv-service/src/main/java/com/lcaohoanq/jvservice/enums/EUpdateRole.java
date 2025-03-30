package com.lcaohoanq.jvservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EUpdateRole {
    STAFF("STAFF"),
    BREEDER("BREEDER"),;

    private final String role;

}
