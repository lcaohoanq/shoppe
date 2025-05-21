package com.lcaohoanq.common.utils;

public class OtpUtil {

    public static String generateOtp() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

}
