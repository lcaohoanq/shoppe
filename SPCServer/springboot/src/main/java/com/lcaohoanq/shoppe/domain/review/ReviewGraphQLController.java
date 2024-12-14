package com.lcaohoanq.shoppe.domain.review;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.domain.order.IOrderService;
import com.lcaohoanq.shoppe.domain.order.OrderResponse;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.mapper.ReviewMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReviewGraphQLController {

    private final IReviewService reviewService;
    private final LocalizationUtils localizationUtils;
    private final IOrderService orderService;
    private final ReviewMapper reviewMapper;

    @QueryMapping
    @PreAuthorize("permitAll()")
    public List<ReviewResponse> getAllFeedbacks() {
        return reviewService.getAll();
    }

    @QueryMapping
    public ReviewResponse getFeedbackById(@Argument Long id) {
        return reviewService
            .getById(id)
            .orElseThrow(() -> new DataNotFoundException("Feedback not found: " + id));
    }

    @QueryMapping
    public ReviewResponse getFeedbackByOrderId(@Argument Long orderId) {
        return reviewService
            .getByOrderId(orderId)
            .orElseThrow(() -> new DataNotFoundException("Feedback not found for order: " + orderId));
    }

    @MutationMapping
    public ReviewResponse createFeedback(@Argument ReviewDTO review) {
        // Check if the order is in DELIVERED status
        OrderResponse order = orderService.getById(review.orderId());
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new IllegalStateException("Feedback can only be submitted for delivered orders.");
        }

        // Check if review already exists for this order
        if (reviewService.getByOrderId(review.orderId()).isPresent()) {
            throw new IllegalStateException("Feedback has already been submitted for this order.");
        }

        return reviewService.create(review);
    }

    @MutationMapping
    public ReviewResponse updateFeedback(@Argument Long id, @Argument ReviewDTO review) {
        return reviewService.update(id, review);
    }

    @MutationMapping
    public Boolean deleteFeedback(@Argument Long id) {
        try {
            reviewService.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
