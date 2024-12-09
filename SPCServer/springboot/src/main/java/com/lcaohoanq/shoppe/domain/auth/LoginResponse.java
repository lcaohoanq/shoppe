package com.lcaohoanq.shoppe.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import java.time.LocalDateTime;

@JsonPropertyOrder({
    "access_token",
    "refresh_token",
    "expires_refresh_token",
    "expires",
    "user"
})
@JsonInclude(Include.NON_NULL)
public record LoginResponse(
    @JsonProperty("access_token")
    String token,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("expires_refresh_token")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime expiresRefreshToken,

    @JsonProperty("expires")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime expires,

    //user's detail
    UserResponse user
) {}
