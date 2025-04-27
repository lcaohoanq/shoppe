package com.lcaohoanq.keycloak.movie

import com.lcaohoanq.keycloak.movie.model.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, String>
