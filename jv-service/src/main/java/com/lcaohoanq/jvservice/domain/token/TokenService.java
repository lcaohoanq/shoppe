package com.lcaohoanq.jvservice.domain.token;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.component.JwtTokenUtils;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import com.lcaohoanq.jvservice.domain.user.UserService;
import com.lcaohoanq.jvservice.exception.ExpiredTokenException;
import com.lcaohoanq.jvservice.exception.TokenNotFoundException;
import com.lcaohoanq.jvservice.mapper.UserMapper;
import com.lcaohoanq.jvservice.repository.TokenRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenService implements ITokenService {

    private static final int MAX_TOKENS = 3;
    private final UserService userService;
    @Value("${jwt.expiration}")
    private int expiration; //save to an environment variable

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtil;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public Token refreshToken(String refreshToken, User user) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new TokenNotFoundException("Refresh token does not exist"));

        if (existingToken.getRefreshExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(existingToken);
            throw new ExpiredTokenException("Refresh token is expired");
        }
        String token = jwtTokenUtil.generateToken(user);
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expiration);
        existingToken.setExpirationDate(expirationDateTime);
        existingToken.setToken(token);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setRefreshExpirationDate(
            LocalDateTime.now().plusSeconds(expirationRefreshToken));
        return existingToken;
    }

    //do revoke token
    @Override
    public void deleteToken(String token, User user) throws DataNotFoundException {
        Token existingToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new TokenNotFoundException("Token does not exist"));
        
        if (existingToken.isRevoked()) {
            throw new TokenNotFoundException("Token has been revoked");
        }
        
        //check if token is attaching with user
        if (!Objects.equals(existingToken.getUser().getId(), user.getId())) {
            throw new TokenNotFoundException("Token does not exist");
        }
        existingToken.setRevoked(true);
        tokenRepository.save(existingToken);
    }

    @Override
    public Token findUserByToken(String token) throws DataNotFoundException {
        return tokenRepository.findByToken(token)
            .orElseThrow(() -> new TokenNotFoundException("Token does not exist"));
    }

    @Transactional
    @Override
    public Token addToken(long userId, String token, boolean isMobileDevice) {
        UserResponse existingUser = userService.findUserById(userId);
        List<Token> userTokens = tokenRepository.findByUserId(existingUser.id());
        int tokenCount = userTokens.size();
        // Số lượng token vượt quá giới hạn, xóa một token cũ
        if (tokenCount >= MAX_TOKENS) {
            //kiểm tra xem trong danh sách userTokens có tồn tại ít nhất
            //một token không phải là thiết bị di động (non-mobile)
            boolean hasNonMobileToken = !userTokens.stream().allMatch(Token::isMobile);
            Token tokenToDelete;
            if (hasNonMobileToken) {
                tokenToDelete = userTokens.stream()
                    .filter(userToken -> !userToken.isMobile())
                    .findFirst()
                    .orElse(userTokens.get(0));
            } else {
                //tất cả các token đều là thiết bị di động,
                //chúng ta sẽ xóa token đầu tiên trong danh sách
                tokenToDelete = userTokens.get(0);
            }
            tokenRepository.delete(tokenToDelete);
        }
        long expirationInSeconds = expiration;
        LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(expirationInSeconds);
        // Tạo mới một token cho người dùng
        Token newToken = Token.builder()
            .user(userMapper.toUser(existingUser))
            .token(token)
            .revoked(false)
            .expired(false)
            .tokenType("Bearer")
            .expirationDate(expirationDateTime)
            .isMobile(isMobileDevice)
            .build();

        newToken.setRefreshToken(UUID.randomUUID().toString());
        newToken.setRefreshExpirationDate(LocalDateTime.now().plusSeconds(expirationRefreshToken));
        tokenRepository.save(newToken);
        return newToken;
    }

}
