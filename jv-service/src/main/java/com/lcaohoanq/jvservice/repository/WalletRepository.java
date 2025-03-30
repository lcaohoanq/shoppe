package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
    
}
