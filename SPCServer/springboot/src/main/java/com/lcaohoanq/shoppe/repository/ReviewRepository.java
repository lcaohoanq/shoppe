package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.review.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Boolean existsByUserIdAndOrderId(Long userId, Long orderId);
    Optional<Review> findByOrderId(Long orderId);

}
