package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.models.User;

public record AuthResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("expires_refresh_token") Long expiresRefreshToken,
    @JsonProperty("expires") Long expires,
    @JsonProperty("user") User user
) {

}
