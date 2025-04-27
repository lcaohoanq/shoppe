package com.lcaohoanq.keycloak.movie

import com.lcaohoanq.keycloak.movie.exception.MovieNotFoundException
import com.lcaohoanq.keycloak.movie.model.Movie
import org.springframework.stereotype.Service

@Service
class MovieService(
    private val movieRepository: MovieRepository
) {

    fun validateAndGetMovie(imdbId: String): Movie {
        return movieRepository.findById(imdbId).orElseThrow {
            MovieNotFoundException(
                imdbId
            )
        }
    }

    fun getMovies(): List<Movie> {
        return movieRepository.findAll()
    }

    fun getMovie(imdbId: String): Movie {
        return movieRepository.findById(imdbId).orElseThrow {
            MovieNotFoundException(
                imdbId
            )
        }
    }


    fun saveMovie(movie: Movie): Movie {
        return movieRepository.save(movie)
    }

    fun deleteMovie(movie: Movie) {
        movieRepository.delete(movie)
    }
}
