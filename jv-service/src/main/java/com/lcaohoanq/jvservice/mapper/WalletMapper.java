package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.wallet.Wallet;
import com.lcaohoanq.jvservice.domain.wallet.WalletDTO.WalletResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse toWalletResponse(Wallet wallet);
    
}
