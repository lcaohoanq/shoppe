package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.wallet.Wallet;
import com.lcaohoanq.shoppe.domain.wallet.WalletDTO.WalletResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse toWalletResponse(Wallet wallet);
    
}
