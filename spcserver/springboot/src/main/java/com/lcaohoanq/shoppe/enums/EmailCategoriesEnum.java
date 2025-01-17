package com.lcaohoanq.shoppe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailCategoriesEnum {
    OTP("sendOtp"),
    BLOCK_ACCOUNT("blockAccount"),
    FORGOT_PASSWORD("forgotPassword"),
    RETRY_BLOCK_ALERT("retryBlockAlert"),
    BALANCE_FLUCTUATION("balanceFluctuation"),
    AUCTION_STARTED("auctionStarted"),
    AUCTION_CLOSED("auctionClosed"),
    AUCTION_UPCOMING("auctionUpcoming"),
    AUCTION_WINNER("auctionWinner"),
    ORDER_CREATED("orderCreated"),
    KOI_APPROVED("koiApproved"),
    KOI_REJECTED("koiRejected"),
    KOI_ADDED_TO_AUCTION("koiAddedToAuction"),
    RESET_PASSWORD("resetPassword"),
    ORDER_CANCELLED("orderCancelled"),
    ORDER_CANCELLED_BREEDER("orderCancelledForBreeder"),
    PAYMENT_SUCCESS("paymentSuccess"),
    PAYMENT_REFUND("paymentRefund"),
    UPDATE_ROLE("requestUpdateRole"),
    PROCESSING_UPDATE_ROLE("processingUpdateRole");

    private final String type;
}
