package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
    
}
