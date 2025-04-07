package com.lcaohoanq.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingCarrierName {

    SPX_EXPRESS("SPX Express"),
    VIETTEL_POST("Viettel Post"),
    VIETNAM_POST("Vietnam Post"),
    GHN("Giao Hang Nhanh"),
    GHTK("Giao Hang Tiet Kiem"),
    JandT_EXPRESS("J&T Express");

    private final String displayName;

}
