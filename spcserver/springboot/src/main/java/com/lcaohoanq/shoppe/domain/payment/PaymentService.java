package com.lcaohoanq.shoppe.domain.payment;

import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.domain.mail.IMailService;
import com.lcaohoanq.shoppe.domain.order.IOrderService;
import com.lcaohoanq.shoppe.domain.order.Order;
import com.lcaohoanq.shoppe.domain.order.Order.OrderStatus;
import com.lcaohoanq.shoppe.domain.payment.Payment.EPaymentStatus;
import com.lcaohoanq.shoppe.domain.payment.Payment.EPaymentType;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.wallet.WalletService;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.exception.MalformDataException;
import com.lcaohoanq.shoppe.repository.OrderRepository;
import com.lcaohoanq.shoppe.repository.PaymentRepository;
import com.lcaohoanq.shoppe.repository.UserRepository;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final IUserService userService;
    private final IOrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final com.lcaohoanq.shoppe.config.VNPayConfig VNPayConfig;
    private final IMailService mailService;
    private final WalletService walletService;

    @Override
    public Map<String, String> createDepositPayment(PaymentDTO paymentDTO, String ipAddress)
            throws UnsupportedEncodingException {
        return createVNPayPayment(paymentDTO, ipAddress, "Deposit to account:");
    }

    @Override
    public Map<String, String> createOrderPayment(PaymentDTO paymentDTO, String ipAddress)
            throws UnsupportedEncodingException {
        return createVNPayPayment(paymentDTO, ipAddress, "Payment for order:");
    }

    private Map<String, String> createVNPayPayment(PaymentDTO paymentDTO, String ipAddress, String orderInfoPrefix)
            throws UnsupportedEncodingException {
        long amount = (long) (paymentDTO.getPaymentAmount() * 100);
        String id = orderInfoPrefix.startsWith("Deposit") ? paymentDTO.getUserId().toString()
                : paymentDTO.getOrderId().toString();
        String vnp_TxnRef = com.lcaohoanq.shoppe.config.VNPayConfig.getRandomNumber(8);

        Map<String, String> vnp_Params = createBaseVnpParams(amount, vnp_TxnRef, ipAddress);
        vnp_Params.put("vnp_OrderInfo", orderInfoPrefix + id);

        String paymentUrl = createPaymentUrl(vnp_Params);

        Map<String, String> response = new HashMap<>();
        response.put("paymentUrl", paymentUrl);
        return response;
    }

    private Map<String, String> createBaseVnpParams(long amount, String vnp_TxnRef, String vnp_IpAddr) {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.getVnp_Returnurl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnp_Params;
    }

    private String createPaymentUrl(Map<String, String> vnp_Params) throws UnsupportedEncodingException {
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = com.lcaohoanq.shoppe.config.VNPayConfig.hmacSHA512(VNPayConfig.getVnp_HashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return VNPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }

    @Override
    public Map<String, Object> handlePaymentReturn(Map<String, String> requestParams) {
        String vnp_ResponseCode = requestParams.get("vnp_ResponseCode");
        String vnp_TxnRef = requestParams.get("vnp_TxnRef");
        String vnp_Amount = requestParams.get("vnp_Amount");
        String vnp_OrderInfo = requestParams.get("vnp_OrderInfo");

        Map<String, Object> result = new HashMap<>();
        result.put("success", "00".equals(vnp_ResponseCode));

        if ("00".equals(vnp_ResponseCode)) {
            processSuccessfulPayment(result, vnp_Amount, vnp_OrderInfo);
        } else {
            result.put("message", "Payment failed");
            result.put("responseCode", vnp_ResponseCode);
        }

        return result;
    }

    private void processSuccessfulPayment(Map<String, Object> result, String vnp_Amount, String vnp_OrderInfo) {
        long amount = Long.parseLong(vnp_Amount) / 100;
        float amountFloat = Float.parseFloat(vnp_Amount) / 100;
        Payment payment = createPaymentObject(amountFloat, vnp_OrderInfo);

        if (vnp_OrderInfo.startsWith("Deposit to account:")) {
            processDepositPayment(result, amount, payment);
        } else {
            processOrderPayment(result, amount, payment);
        }
    }

    private Payment createPaymentObject(float amount, String orderInfo) {
        return Payment.builder()
                .paymentAmount(amount)
                .paymentMethod("VNPAY")
                .paymentDate(LocalDateTime.now())
                .paymentType(orderInfo.startsWith("Deposit") ? EPaymentType.DEPOSIT : EPaymentType.ORDER)
                .paymentStatus(EPaymentStatus.SUCCESS)
                .user(orderInfo.startsWith("Deposit")
                        ? userRepository.findById(Long.parseLong(orderInfo.split(":")[1])).orElse(null)
                        : orderRepository.findById(Long.parseLong(orderInfo.split(":")[1]))
                        .orElseThrow(() -> new MalformDataException("Order not found")).getUser())
                .order(orderInfo.startsWith("Payment for order")
                        ? orderRepository.findById(Long.parseLong(orderInfo.split(":")[1]))
                        .orElseThrow(() -> new MalformDataException("Order not found"))
                        : null)
                .build();
    }

    private void processDepositPayment(Map<String, Object> result, long amount, Payment payment) {
        String userId = payment.getUser().getId().toString();
        try {
            payment.setUser(userRepository.findById(Long.parseLong(userId)).orElse(null));
            paymentRepository.save(payment);
            walletService.updateAccountBalance(Long.parseLong(userId), amount);
            result.put("message", "Deposit successful");
            result.put("userId", userId);
            result.put("amount", amount);
            result.put("paymentType", "deposit");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Error updating balance");
        }
    }

    private void processOrderPayment(Map<String, Object> result, long amount, Payment payment) {
        result.put("message", "Order payment successful");
        result.put("amount", amount);
        result.put("paymentType", "order");
        String orderId = payment.getOrder().getId().toString();
        orderRepository.findById(Long.parseLong(orderId)).ifPresent(order -> {
            orderService.updateOrderStatus(order.getId(), OrderStatus.PROCESSING);
            orderRepository.save(order);
            payment.setOrder(order);
            paymentRepository.save(payment);
        });
    }

    @Override
    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = Payment.builder()
                .paymentAmount(paymentDTO.getPaymentAmount())
                .paymentMethod(paymentDTO.getPaymentMethod())
                .paymentType(EPaymentType.valueOf(paymentDTO.getPaymentType()))
                .order(paymentDTO.getOrderId() != null ? orderRepository.findById(paymentDTO.getOrderId())
                        .orElseThrow(() -> new MalformDataException("Order not found")) : null)
                .user(userRepository.findById(paymentDTO.getUserId()).orElse(null))
                .paymentStatus(EPaymentStatus.valueOf(paymentDTO.getPaymentStatus()))
                .bankNumber(paymentDTO.getBankNumber())
                .bankName(paymentDTO.getBankName())
                .paymentDate(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentByOrderID(Long id) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        return paymentRepository.findByOrder(existingOrder);
    }

    @Override
    @Transactional
    public Map<String, Object> createPaymentAndUpdateOrder(PaymentDTO paymentDTO) throws Exception {
        if (getPaymentByOrderID(paymentDTO.getOrderId()).isPresent()) {
            throw new DataAlreadyExistException("Payment already exists for order");
        }

        paymentDTO.setPaymentStatus(EPaymentStatus.PENDING.toString());
        Payment payment = createPayment(paymentDTO);
        if (payment == null) {
            throw new Exception("Failed to create payment");
        }
        orderService.updateOrderStatus(paymentDTO.getOrderId(), OrderStatus.PROCESSING);
        Map<String, Object> response = new HashMap<>();
        response.put("payment", payment);
        response.put("orderStatus", OrderStatus.PROCESSING);
        return response;
    }

    @Override
    @Transactional
    public Map<String, Object> createDrawOutRequest(PaymentDTO paymentDrawOutDTO) throws Exception {
        User user = userRepository.findById(paymentDrawOutDTO.getUserId())
                .orElseThrow(() -> new MalformDataException("User not found"));

        Float userBalance = user.getWallet().getBalance();
        
        if (userBalance < paymentDrawOutDTO.getPaymentAmount()) {
            throw new MalformDataException("Insufficient balance");
        }

        paymentDrawOutDTO.setPaymentType(EPaymentType.DRAW_OUT.toString());
        paymentDrawOutDTO.setPaymentStatus(EPaymentStatus.PENDING.toString());
        Payment payment = createPayment(paymentDrawOutDTO);

        walletService.updateAccountBalance(paymentDrawOutDTO.getUserId(), -paymentDrawOutDTO.getPaymentAmount().longValue());

        Map<String, Object> response = new HashMap<>();
        response.put("payment", payment);
        response.put("newBalance", userBalance);
        return response;
    }

    @Override
    public Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable) {
        userRepository.findById(userId).orElseThrow(() -> new MalformDataException("User not found"));
        return paymentRepository.findPaymentsByUserId(userId, pageable);
    }

    @Override
    public Page<Payment> getPaymentsByUserIdAndStatus(Long userId, EPaymentStatus status, Pageable pageable) {
        userRepository.findById(userId).orElseThrow(() -> new MalformDataException("User not found"));
        return paymentRepository.findPaymentsByUserIdAndPaymentStatus(userId, status, pageable);
    }

    @Override
    public Page<Payment> getPaymentsByKeywordAndStatus(String keyword, EPaymentStatus status, Pageable pageable) {
        return paymentRepository.findPaymentsByStatusAndKeyWord(keyword, status, pageable);
    }

    @Override
    public Page<Payment> getPaymentsByKeyword(String keyword, Pageable pageable) {
        return paymentRepository.findPaymentsByKeyword(keyword, pageable);
    }

    @Override
    public Payment updatePaymentStatus(Long id, EPaymentStatus status) throws Exception {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new MalformDataException("Payment not found"));
        if (payment.getPaymentStatus().equals(EPaymentStatus.PENDING)) {
            if (payment.getPaymentType().equals(EPaymentType.DRAW_OUT)) {
                if (status.equals(EPaymentStatus.REFUNDED)) {
                    User user = payment.getUser();
                    walletService.updateAccountBalance(user.getId(), payment.getPaymentAmount().longValue());
                    sendMail(payment, status);
                } else if (status.equals(EPaymentStatus.SUCCESS)) {
                    sendMail(payment, status);
                }
            }
        }
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }

    private void sendMail(Payment payment, EPaymentStatus paymentStatus) throws MessagingException {
        Context context = new Context();
        context.setVariable("name", payment.getUser().getName());
        context.setVariable("payment_amount", payment.getPaymentAmount());
        context.setVariable("payment_id", payment.getId());
        context.setVariable("payment_status", payment.getPaymentStatus());
        context.setVariable("payment_date", payment.getPaymentDate());
        context.setVariable("payment_type", payment.getPaymentMethod());

        if (paymentStatus.equals(EPaymentStatus.SUCCESS)) {
            mailService.sendMail(payment.getUser().getEmail(), "Payment successful", EmailCategoriesEnum.PAYMENT_SUCCESS.getType(), context);
        } else if (paymentStatus.equals(EPaymentStatus.REFUNDED)) {
            mailService.sendMail(payment.getUser().getEmail(), "Payment failed", EmailCategoriesEnum.PAYMENT_REFUND.getType(), context);
        }
    }
}
