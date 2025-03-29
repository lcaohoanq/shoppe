package com.lcaohoanq.kotlinbasics.domain.user

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class UserSpecification(private val search: String?) : Specification<User> {
    override fun toPredicate(
        root: Root<User>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        // If search is null or blank, return a conjunction (match all)
        if (search.isNullOrBlank()) {
            return criteriaBuilder.conjunction()
        }

        val searchLike = "%$search%"
        return criteriaBuilder.or(
            criteriaBuilder.like(root.get<String>("userName"), searchLike),
            criteriaBuilder.like(root.get<String>("email"), searchLike),
            criteriaBuilder.like(root.get<String>("phone"), searchLike)
        )
    }
}
