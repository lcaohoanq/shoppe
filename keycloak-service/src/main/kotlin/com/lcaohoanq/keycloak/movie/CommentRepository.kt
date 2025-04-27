package com.lcaohoanq.keycloak.movie

import com.lcaohoanq.keycloak.movie.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findByMovieImdbId(imdbId: String): List<Comment>
}
