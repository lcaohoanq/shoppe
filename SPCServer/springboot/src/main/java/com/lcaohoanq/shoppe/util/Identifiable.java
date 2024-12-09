package com.lcaohoanq.shoppe.util;

public interface Identifiable {

    default boolean isMobileDevice(String userAgent) {
        // Kiểm tra User-Agent header để xác định thiết bị di động
        if (userAgent == null) {
            return false;
        }
        return userAgent.toLowerCase().contains("mobile");
    }

}
