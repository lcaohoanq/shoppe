package com.lcaohoanq.shoppe.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
Data crawled from https://www.xe.com/symbols/
*/

@Getter
@AllArgsConstructor
public enum Currency {
    ALL("ALL", "Lek"),
    AFN("AFN", "؋"),
    ARS("ARS", "$"),
    AWG("AWG", "ƒ"),
    AUD("AUD", "$"),
    AZN("AZN", "₼"),
    BSD("BSD", "$"),
    BBD("BBD", "$"),
    BYN("BYN", "Br"),
    BZD("BZD", "BZ$"),
    BMD("BMD", "$"),
    BOB("BOB", "$b"),
    BAM("BAM", "KM"),
    BWP("BWP", "P"),
    BGN("BGN", "лв"),
    BRL("BRL", "R$"),
    BND("BND", "$"),
    KHR("KHR", "៛"),
    CAD("CAD", "$"),
    KYD("KYD", "$"),
    CLP("CLP", "$"),
    CNY("CNY", "¥"),
    COP("COP", "$"),
    CRC("CRC", "₡"),
    HRK("HRK", "kn"),
    CUP("CUP", "₱"),
    CZK("CZK", "Kč"),
    DKK("DKK", "kr"),
    DOP("DOP", "RD$"),
    XCD("XCD", "$"),
    EGP("EGP", "£"),
    SVC("SVC", "$"),
    EUR("EUR", "€"),
    FKP("FKP", "£"),
    FJD("FJD", "$"),
    GHS("GHS", "¢"),
    GIP("GIP", "£"),
    GTQ("GTQ", "Q"),
    GGP("GGP", "£"),
    GYD("GYD", "$"),
    HNL("HNL", "L"),
    HKD("HKD", "$"),
    HUF("HUF", "Ft"),
    ISK("ISK", "kr"),
    IDR("IDR", "Rp"),
    IRR("IRR", "﷼"),
    IMP("IMP", "£"),
    ILS("ILS", "₪"),
    JMD("JMD", "J$"),
    JPY("JPY", "¥"),
    JEP("JEP", "£"),
    KZT("KZT", "лв"),
    KPW("KPW", "₩"),
    KRW("KRW", "₩"),
    KGS("KGS", "лв"),
    LAK("LAK", "₭"),
    LBP("LBP", "£"),
    LRD("LRD", "$"),
    MKD("MKD", "ден"),
    MYR("MYR", "RM"),
    MUR("MUR", "₨"),
    MXN("MXN", "$"),
    MNT("MNT", "₮"),
    MZN("MZN", "MT"),
    NAD("NAD", "$"),
    NPR("NPR", "₨"),
    ANG("ANG", "ƒ"),
    NZD("NZD", "$"),
    NIO("NIO", "C$"),
    NGN("NGN", "₦"),
    NOK("NOK", "kr"),
    OMR("OMR", "﷼"),
    PKR("PKR", "₨"),
    PAB("PAB", "B/."),
    PYG("PYG", "Gs"),
    PEN("PEN", "S/."),
    PHP("PHP", "₱"),
    PLN("PLN", "zł"),
    QAR("QAR", "﷼"),
    RON("RON", "lei"),
    RUB("RUB", "₽"),
    SHP("SHP", "£"),
    SAR("SAR", "﷼"),
    RSD("RSD", "Дин."),
    SCR("SCR", "₨"),
    SGD("SGD", "$"),
    SBD("SBD", "$"),
    SOS("SOS", "S"),
    ZAR("ZAR", "R"),
    LKR("LKR", "₨"),
    SEK("SEK", "kr"),
    CHF("CHF", "CHF"),
    SRD("SRD", "$"),
    SYP("SYP", "£"),
    TWD("TWD", "NT$"),
    THB("THB", "฿"),
    TTD("TTD", "TT$"),
    TVD("TVD", "$"),
    UAH("UAH", "₴"),
    GBP("GBP", "£"),
    USD("USD", "$"),
    UYU("UYU", "$U"),
    UZS("UZS", "лв"),
    VEF("VEF", "Bs"),
    VND("VND", "₫"),
    YER("YER", "﷼"),
    ZWD("ZWD", "Z$");

    private final String code;
    private final String symbol;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
    
    // Optional method to get currency by code
    public static Currency fromCode(String code) {
        for (Currency currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("No currency found with code: " + code);
    }
}