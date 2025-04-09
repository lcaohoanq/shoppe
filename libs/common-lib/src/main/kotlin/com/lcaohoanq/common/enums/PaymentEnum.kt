package com.lcaohoanq.common.enums

class PaymentEnum {

    enum class Status {
        PENDING,
        SUCCESS,
        FAILED,
        REFUNDED,
        CANCELLED
    }

    enum class ActionType {
        DEPOSIT,
        ORDER,
        DRAW_OUT,
    }

    enum class Type {
        CASH,
        BANK_TRANSFER,
        CREDIT_CARD,
        DEBIT_CARD,
        PAYPAL,
        STRIPE,
        APPLE_PAY,
        GOOGLE_PAY,
        BITCOIN,
        ETHEREUM,
    }

}