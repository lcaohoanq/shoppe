package com.lcaohoanq.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    MEMBER("MEMBER"),
    STAFF("STAFF"),
    SHOP_OWNER("SHOP_OWNER"),
    MANAGER("MANAGER");

    private final String role;

}
