package com.lcaohoanq.shoppe.constant;

public class Regex {

    public static final String PASSWORD_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    public static final String PHONE_NUMBER_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
    public static final String HEADQUARTER_DOMAIN_URL_REGEX = "^https:\\/\\/shopee\\.\\w{2,3}\\/?(\\.\\w{2}\\/?)?$";
}
