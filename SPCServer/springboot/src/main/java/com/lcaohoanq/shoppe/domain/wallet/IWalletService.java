package com.lcaohoanq.shoppe.domain.wallet;

import com.lcaohoanq.shoppe.domain.wallet.WalletDTO.WalletResponse;

public interface IWalletService {

    WalletResponse getByUserId(Long userId);
    void updateAccountBalance(Long userId, Long payment) throws Exception;
    
}
