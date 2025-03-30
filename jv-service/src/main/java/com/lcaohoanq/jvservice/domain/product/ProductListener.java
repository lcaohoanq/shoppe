package com.lcaohoanq.jvservice.domain.product;

import com.lcaohoanq.jvservice.domain.product.IProductRedisService;import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
Install Debezium and configure it to capture changes in the MySQL product table.

Set up a Kafka Connect destination to consume the Debezium change data events.

Implement a Spring Boot application that subscribes to the Kafka Connect destination and updates the Redis cache accordingly.
* */
@AllArgsConstructor
public class ProductListener {
    private final IProductRedisService productRedisService;
    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);
    @PrePersist
    public void prePersist(Product product) {
        logger.info("prePersist");
    }

    @PostPersist //save = persis
    public void postPersist(Product product) {
        // Update Redis cache
        logger.info("postPersist");
        productRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Product product) {
        // Update Redis cache
        logger.info("postUpdate");
        productRedisService.clear();
    }

    @PreRemove
    public void preRemove(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product) {
        // Update Redis cache
        logger.info("postRemove");
        productRedisService.clear();
    }
}
