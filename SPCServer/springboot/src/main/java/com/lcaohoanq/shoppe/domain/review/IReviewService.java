package com.lcaohoanq.shoppe.domain.review;


import java.util.List;
import java.util.Optional;

public interface IReviewService {
    ReviewResponse create(ReviewDTO reviewDTO);
    List<ReviewResponse> getAll();
    ReviewResponse update(long id, ReviewDTO reviewDTO);
    void delete (long id);
    Optional<ReviewResponse> getById(long id);
    Optional<ReviewResponse> getByOrderId(long orderId);
}
