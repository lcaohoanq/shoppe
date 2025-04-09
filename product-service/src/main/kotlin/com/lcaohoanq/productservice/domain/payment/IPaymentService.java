package com.lcaohoanq.productservice.domain.payment;

import com.lcaohoanq.common.enums.PaymentEnum;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentService {

    Map<String, String> createDepositPayment(PaymentDTO paymentDTO, String ipAddress)
        throws UnsupportedEncodingException;

    Map<String, String> createOrderPayment(PaymentDTO paymentDTO, String ipAddress)
        throws UnsupportedEncodingException;

    Map<String, Object> handlePaymentReturn(Map<String, String> requestParams);

    Payment createPayment(PaymentDTO payment);

    Optional<Payment> getPaymentByOrderID(Long id);

    Map<String, Object> createPaymentAndUpdateOrder(PaymentDTO paymentDTO) throws Exception;

    Map<String, Object> createDrawOutRequest(PaymentDTO paymentDrawOutDTO) throws Exception;

    Page<Payment> getPaymentsByUserId(Long userId, Pageable pageable);

    Page<Payment> getPaymentsByUserIdAndStatus(Long userId, PaymentEnum.Status status,
                                               Pageable pageable);

    Page<Payment> getPaymentsByKeywordAndStatus(String keyword, PaymentEnum.Status status,
                                                Pageable pageable);

    Page<Payment> getPaymentsByKeyword(String keyword, Pageable pageable);

    Payment updatePaymentStatus(Long id, PaymentEnum.Status status) throws Exception;
}
