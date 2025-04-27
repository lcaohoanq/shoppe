package com.lcaohoanq.keycloak.movie

import com.lcaohoanq.keycloak.config.SwaggerConfig
import com.lcaohoanq.keycloak.movie.dto.AddCommentRequest
import com.lcaohoanq.keycloak.movie.dto.CreateMovieRequest
import com.lcaohoanq.keycloak.movie.dto.MovieDto
import com.lcaohoanq.keycloak.movie.mapper.MovieDtoMapper
import com.lcaohoanq.keycloak.movie.model.Comment
import com.lcaohoanq.keycloak.movie.model.Movie
import com.lcaohoanq.keycloak.userextra.dto.MovieDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.Instant

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movie API", description = "Movie API")
class MoviesController(
    private val movieService: MovieService,
    private val movieMapper: MovieDtoMapper,
    private val commentRepository: CommentRepository
) {

    @Operation(
        summary = "Get all movies",
    )
    @GetMapping
    fun getMovies(): List<MovieDto> {
        return movieService.getMovies().map { movieMapper.toMovieDto(it) }
    }

    @GetMapping("/{imdbId}")
    fun getMovie(@PathVariable imdbId: String): MovieDto {
        val movie = movieService.validateAndGetMovie(imdbId)
        return movieMapper.toMovieDto(movie)
    }

    @Operation(security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createMovie(@Valid @RequestBody createMovieRequest: CreateMovieRequest): MovieDto {
        val movie = Movie.from(createMovieRequest)
        val savedMovie = movieService.saveMovie(movie)
        return movieMapper.toMovieDto(savedMovie)
    }

    @Operation(security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @PutMapping("/{imdbId}")
    fun updateMovie(
        @PathVariable imdbId: String,
        @Valid @RequestBody updateMovieRequest: MovieDTO.UpdateMovieRequest
    ): MovieDto {
        val movie = movieService.validateAndGetMovie(imdbId)
        Movie.updateFrom(updateMovieRequest, movie)
        val updatedMovie = movieService.saveMovie(movie)
        return movieMapper.toMovieDto(updatedMovie)
    }

    @Operation(security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @DeleteMapping("/{imdbId}")
    fun deleteMovie(@PathVariable imdbId: String): MovieDto {
        val movie = movieService.validateAndGetMovie(imdbId)
        movieService.deleteMovie(movie)
        return movieMapper.toMovieDto(movie)
    }

    @Operation(security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{imdbId}/comments")
    fun addMovieComment(
        @PathVariable imdbId: String,
        @Valid @RequestBody addCommentRequest: AddCommentRequest,
        principal: Principal
    ): MovieDto {
        val movie = movieService.validateAndGetMovie(imdbId)
        val comment = Comment(
            username = principal.name,
            text = addCommentRequest.text,
            timestamp = Instant.now(),
            movie = movie
        )
        // Instead of adding to the list manually, you'll need a comment repository
        commentRepository.save(comment)
        // Refresh the movie to get updated comments
        val updatedMovie = movieService.getMovie(imdbId)
        return movieMapper.toMovieDto(updatedMovie)
    }
}
