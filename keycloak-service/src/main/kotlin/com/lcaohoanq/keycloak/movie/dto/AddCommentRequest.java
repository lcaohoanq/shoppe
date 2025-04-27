package com.lcaohoanq.keycloak.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddCommentRequest(@Schema(example = "Very good!") @NotBlank String text) {
}
