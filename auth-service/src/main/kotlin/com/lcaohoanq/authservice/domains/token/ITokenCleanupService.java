package com.lcaohoanq.authservice.domains.token;

public interface ITokenCleanupService {
    void cleanupExpiredTokens();
}
