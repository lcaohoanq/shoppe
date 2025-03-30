package com.lcaohoanq.jvservice.domain.wallet;

import com.lcaohoanq.jvservice.domain.wallet.WalletDTO.WalletResponse;

public interface IWalletService {

    WalletResponse getByUserId(Long userId);
    void updateAccountBalance(Long userId, Long payment) throws Exception;
    
}
