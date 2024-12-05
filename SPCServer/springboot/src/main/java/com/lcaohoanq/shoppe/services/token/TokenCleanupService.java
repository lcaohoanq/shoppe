package com.lcaohoanq.shoppe.services.token;

import com.lcaohoanq.shoppe.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCleanupService implements ITokenCleanupService{

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    public void cleanupExpiredTokens() {
        try{
            tokenRepository.deleteExpiredTokens(LocalDateTime.now());
        }  catch (Exception e) {
            // Log the exception and handle error
            log.error("Error auto setting Token expired", e.getCause());
        }
    }

}
