package com.lcaohoanq.shoppe.domain.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public interface TokenPort {

    record RefreshTokenDTO(
        @NotBlank(message = "Refresh token is required") String refreshToken
    ) {

    }

    @JsonPropertyOrder(
        {
            "id",
            "access_token",
            "refresh_token",
            "token_type",
            "expires",
            "expires_refresh_token",
            "is_mobile"
        }
    )
    record TokenResponse(
        Long id,
        @JsonProperty("access_token") String token,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        @JsonProperty("expires")
        LocalDateTime expirationDate,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        @JsonProperty("expires_refresh_token")
        LocalDateTime refreshExpirationDate,

        @JsonProperty("is_mobile") Boolean isMobile,

        @JsonIgnore Boolean revoked,
        @JsonIgnore Boolean expired
    ) {

    }

}
