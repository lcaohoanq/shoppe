package com.lcaohoanq.jvservice.domain.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.jvservice.base.entity.BaseEntity;
import com.lcaohoanq.jvservice.domain.order.Order;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.metadata.MediaMeta;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
@SuperBuilder
@AttributeOverrides({
    @AttributeOverride(name = "createdAt", column = @Column(name = "review_created_at")),
    @AttributeOverride(name = "updatedAt", column = @Column(name = "review_updated_at"))
})
public class Review extends BaseEntity {

    @Id
    @SequenceGenerator(name = "reviews_seq", sequenceName = "reviews_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq")
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "content")
    private String content;
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "is_hidden", columnDefinition = "boolean default false")
    private boolean isHidden; //hide username

    @Column(name = "product_quality")
    private String productQuality;
    
    @Column(name = "match_description")
    private String matchDescription;
    
    @Embedded
    private MediaMeta mediaMeta;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
