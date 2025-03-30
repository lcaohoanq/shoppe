package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.token.Token;
import com.lcaohoanq.jvservice.domain.token.TokenPort.TokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    
    TokenResponse toTokenResponse(Token token);

}
