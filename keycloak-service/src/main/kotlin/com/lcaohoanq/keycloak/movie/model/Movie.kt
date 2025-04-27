package com.lcaohoanq.keycloak.movie.model

import com.lcaohoanq.keycloak.movie.dto.CreateMovieRequest
import com.lcaohoanq.keycloak.userextra.dto.MovieDTO
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "movies")
class Movie(
    @Id
    var imdbId: String? = null,
    var title: String? = null,
    var director: String? = null,
    var year: String? = null,
    var poster: String? = null,

    @OneToMany(mappedBy = "movie", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var comments: MutableList<Comment> = mutableListOf()
) {

    companion object {
        fun from(createMovieRequest: CreateMovieRequest): Movie {
            val movie = Movie()
            movie.imdbId = createMovieRequest.imdbId()
            movie.title = createMovieRequest.title()
            movie.director = createMovieRequest.director()
            movie.year = createMovieRequest.year()
            movie.poster = createMovieRequest.poster()
            return movie
        }

        fun updateFrom(updateMovieRequest: MovieDTO.UpdateMovieRequest, movie: Movie) {
            updateMovieRequest.title.let { movie.title = it }
            updateMovieRequest.director.let { movie.director = it }
            updateMovieRequest.year.let { movie.year = it }
            updateMovieRequest.poster.let { movie.poster = it }
        }
    }
}
