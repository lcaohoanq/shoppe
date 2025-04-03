package com.lcaohoanq.jvservice.domain.wallet;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.component.LocalizationUtils;
import com.lcaohoanq.jvservice.constant.MessageKey;
import com.lcaohoanq.jvservice.domain.mail.IMailService;
import com.lcaohoanq.jvservice.domain.user.IUserService;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.repository.UserRepository;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import com.lcaohoanq.jvservice.domain.wallet.WalletDTO.WalletResponse;
import com.lcaohoanq.common.enums.EmailCategoriesEnum;
import com.lcaohoanq.jvservice.mapper.WalletMapper;
import com.lcaohoanq.jvservice.repository.WalletRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private final WalletRepository walletRepository;
    private final IUserService userService;
    private final UserRepository userRepository;
    private final LocalizationUtils localizationUtils;
    private final IMailService mailService;
    private final WalletMapper walletMapper;

    @Override
    public WalletResponse getByUserId(Long userId) {
        UserResponse existedUser = userService.findUserById(userId);
        return walletMapper.toWalletResponse(walletRepository.findByUserId(existedUser.id()));
    }

    @Transactional
    @Retryable(
        retryFor = {MessagingException.class},  // Retry only for specific exceptions
        maxAttempts = 3,                       // Maximum retry attempts
        backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    @Override
    public void updateAccountBalance(Long userId, Long payment) throws Exception {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException(
                localizationUtils.getLocalizedMessage(MessageKey.USER_NOT_FOUND)
            ));
        existingUser.getWallet().setBalance(existingUser.getWallet().getBalance() + payment);

        Context context = new Context();
        context.setVariable("name", existingUser.getName());
        context.setVariable("amount", payment);
        context.setVariable("balance", existingUser.getWallet().getBalance());

        try {
            mailService.sendMail(
                existingUser.getEmail(),
                "Account balance updated",
                EmailCategoriesEnum.BALANCE_FLUCTUATION.getType(),
                context
            );
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", existingUser.getEmail(), e);
            throw new MessagingException(String.format("Failed to send email to %s", existingUser.getEmail()));
        }

        log.info("User {} balance updated. New balance: {}", userId, existingUser.getWallet().getBalance());
        userRepository.save(existingUser);
    }
    
}
