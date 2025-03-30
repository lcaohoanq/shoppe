package com.lcaohoanq.jvservice.domain.wallet;

import com.lcaohoanq.jvservice.api.ApiResponse;
import com.lcaohoanq.jvservice.domain.user.IUserService;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.wallet.WalletDTO.WalletResponse;
import com.lcaohoanq.jvservice.exception.MalformDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;
    private final IUserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<WalletResponse>> getWalletByUserId() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        
        return ResponseEntity.ok().body(
            ApiResponse.<WalletResponse>builder()
                .message("Get wallet by user id successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(walletService.getByUserId(user.getId()))
                .build()
        );
    }

    //     PUT: localhost:4000/api/v1/users/4/deposit/100
//     Header: Authorization Bearer token
    @PutMapping("/{userId}/deposit/{payment}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<?>> deposit(
        @PathVariable long userId,
        @PathVariable long payment
    ) throws Exception {

        if (payment <= 0) {
            throw new MalformDataException("Payment must be greater than 0.");
        }
        
        walletService.updateAccountBalance(userId, payment);
        
        return ResponseEntity.ok().body(
            ApiResponse.<Void>builder()
                .message("Deposit successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );

    }
    
}
