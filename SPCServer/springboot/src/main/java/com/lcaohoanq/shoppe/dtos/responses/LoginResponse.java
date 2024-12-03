package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({"token", "refresh_token", "tokenType", "id", "username", "roles"})
@JsonInclude(Include.NON_NULL)
public record LoginResponse (
    @JsonProperty("token")
    String token,

    @JsonProperty("refresh_token")
    String refreshToken,

    String tokenType,
    //user's detail
    Long id,
    String username,

    List<String> roles
) {}
