package com.lcaohoanq.shoppe.util;

public class OtpUtil {

    public static String generateOtp() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

}
