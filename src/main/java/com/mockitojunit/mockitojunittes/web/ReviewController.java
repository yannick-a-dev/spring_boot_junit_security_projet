package com.mockitojunit.mockitojunittes.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockitojunit.mockitojunittes.dto.ReviewDto;
import com.mockitojunit.mockitojunittes.service.ReviewService;

@RestController
@RequestMapping("/api/")
public class ReviewController {
	private ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping("/pokemon/{pokemonId}/reviews")
	public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId,
			@RequestBody ReviewDto reviewDto) {
		return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
	}

	@GetMapping("/pokemon/{pokemonId}/reviews")
	public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonId") int pokemonId) {
		return reviewService.getReviewsByPokemonId(pokemonId);
	}

	@GetMapping("/pokemon/{pokemonId}/reviews/{id}")
	public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId,
			@PathVariable(value = "id") int reviewId) {
		ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@PutMapping("/pokemon/{pokemonId}/reviews/{id}")
	public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId,
			@PathVariable(value = "id") int reviewId, @RequestBody ReviewDto reviewDto) {
		ReviewDto updatedReview = reviewService.updateReview(pokemonId, reviewId, reviewDto);
		return new ResponseEntity<>(updatedReview, HttpStatus.OK);
	}

	@DeleteMapping("/pokemon/{pokemonId}/reviews/{id}")
	public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId,
			@PathVariable(value = "id") int reviewId) {
		reviewService.deleteReview(pokemonId, reviewId);
		return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
	}
}
