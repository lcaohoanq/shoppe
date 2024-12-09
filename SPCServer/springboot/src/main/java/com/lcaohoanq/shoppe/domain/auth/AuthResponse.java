package com.lcaohoanq.shoppe.domain.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.user.User;

public record AuthResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("expires_refresh_token") Long expiresRefreshToken,
    @JsonProperty("expires") Long expires,
    @JsonProperty("user") User user
) {

}
