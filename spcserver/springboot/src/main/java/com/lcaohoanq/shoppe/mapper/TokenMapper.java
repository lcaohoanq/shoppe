package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.token.Token;
import com.lcaohoanq.shoppe.domain.token.TokenPort.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    
    TokenResponse toTokenResponse(Token token);

}
