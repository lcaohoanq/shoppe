package com.lcaohoanq.shoppe.domain.wallet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public interface WalletDTO {
    
    @JsonPropertyOrder({
        "id",
        "balance"
    })
    record WalletResponse(
        Long id,
        Float balance,
        @JsonBackReference Long userId
    ) {}
    
    record WalletRequest(
        Float balance,
        Long userId
    ) {}

}
