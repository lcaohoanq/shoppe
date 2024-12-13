package com.lcaohoanq.shoppe.domain.wallet;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.constant.MessageKey;
import com.lcaohoanq.shoppe.domain.mail.IMailService;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserRepository;
import com.lcaohoanq.shoppe.domain.wallet.WalletDTO.WalletResponse;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.mapper.WalletMapper;
import com.lcaohoanq.shoppe.util.DTOConverter;
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
        User existedUser = userService.findUserById(userId);
        return walletMapper.toWalletResponse(walletRepository.findByUserId(existedUser.getId()));
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
