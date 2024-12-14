package com.lcaohoanq.shoppe.domain.review;

import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.order.Order;
import com.lcaohoanq.shoppe.domain.order.OrderRepository;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserRepository;
import com.lcaohoanq.shoppe.mapper.ReviewMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponse create(ReviewDTO feedbackDTO) {

        if (reviewRepository.existsByUserIdAndOrderId(feedbackDTO.userId(),
                                                      feedbackDTO.orderId())) {
            throw new DataAlreadyExistException("Feedback already exists");
        }
        User user = userRepository.findById(feedbackDTO.userId())
            .orElseThrow(
                () -> new DataNotFoundException("User not found: " + feedbackDTO.userId()));
        Order order = orderRepository.findById(feedbackDTO.orderId())
            .orElseThrow(
                () -> new DataNotFoundException("Order not found: " + feedbackDTO.orderId()));

        if (order.getUser().getId() != user.getId()) {
            throw new DataNotFoundException("User not found in order");
        }

        Review feedback = Review.builder()
            .content(feedbackDTO.content())
            .rating(feedbackDTO.rating())
            .user(user)
            .order(order)
            .build();
        return reviewMapper.toReviewResponse(reviewRepository.save(feedback));
    }

    @Override
    public List<ReviewResponse> getAll() {
        return reviewRepository.findAll().stream()
            .map(reviewMapper::toReviewResponse)
            .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse update(long id, ReviewDTO feedbackDTO) {
        Review feedback = reviewRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        User existUser = userRepository.findById(feedbackDTO.userId())
            .orElseThrow(() -> new DataNotFoundException("User not found"));
        Order existOrder = orderRepository.findById(feedbackDTO.orderId())
            .orElseThrow(() -> new DataNotFoundException("Order not found"));
        feedback.setContent(feedbackDTO.content());
        feedback.setRating(feedbackDTO.rating());
        feedback.setUser(existUser);
        feedback.setOrder(existOrder);

        return reviewMapper.toReviewResponse(reviewRepository.save(feedback));
    }

    @Override
    public void delete(long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("ReviewResponse not found"));
        reviewRepository.delete(review);
    }

    @Override
    public Optional<ReviewResponse> getById(long id) {
        return Optional.ofNullable(
            reviewRepository.findById(id)
                .map(reviewMapper::toReviewResponse)
                .orElseThrow(
                    () -> new DataNotFoundException("ReviewResponse not found")));
    }

    @Override
    public Optional<ReviewResponse> getByOrderId(long orderId) {
        return Optional.ofNullable(
            reviewRepository.findByOrderId(orderId)
                .map(reviewMapper::toReviewResponse)
                .orElseThrow(
                    () -> new DataNotFoundException("Feedback not found")));
    }
}
