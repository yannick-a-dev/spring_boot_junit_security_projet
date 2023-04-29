package com.mockitojunit.mockitojunittes.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mockitojunit.mockitojunittes.dto.PokemonDto;
import com.mockitojunit.mockitojunittes.dto.ReviewDto;
import com.mockitojunit.mockitojunittes.entity.Pokemon;
import com.mockitojunit.mockitojunittes.entity.Review;
import com.mockitojunit.mockitojunittes.repository.PokemonRepository;
import com.mockitojunit.mockitojunittes.repository.ReviewRepository;
import com.mockitojunit.mockitojunittes.service.Impl.ReviewServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
	
	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private PokemonRepository pokemonRepository;
	
	@InjectMocks
	private ReviewServiceImpl reviewService;

	private Pokemon pokemon;
	private Review review;
	private ReviewDto reviewDto;
	private PokemonDto pokemonDto;

	@BeforeEach
	public void init() {
		pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").build();
		pokemonDto = PokemonDto.builder().id(1).name("pickachu").type("electric").build();
		review = Review.builder().id(1).title("title").content("content").stars(5).build();
		reviewDto = ReviewDto.builder().id(1).title("review title").content("test content").stars(5).build();
	}

	@Test
	public void ReviewService_CreateReview_ReturnsReviewDto() {
		when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
		when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

		ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);

		Assertions.assertThat(savedReview).isNotNull();
	}

	@Test
	public void ReviewService_GetReviewsByPokemonId_ReturnReviewDto() {
		int reviewId = 1;
		when(reviewRepository.findByPokemonId(reviewId)).thenReturn(Arrays.asList(review));

		List<ReviewDto> pokemonReturn = reviewService.getReviewsByPokemonId(reviewId);

		Assertions.assertThat(pokemonReturn).isNotNull();
	}

	@Test
	public void ReviewService_GetReviewById_ReturnReviewDto() {
		int reviewId = 1;
		int pokemonId = 1;

		review.setPokemon(pokemon);

		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

		ReviewDto reviewReturn = reviewService.getReviewById(reviewId, pokemonId);

		Assertions.assertThat(reviewReturn).isNotNull();
		Assertions.assertThat(reviewReturn).isNotNull();
	}

	@Test
	public void ReviewService_UpdatePokemon_ReturnReviewDto() {
		int pokemonId = 1;
		int reviewId = 1;
		pokemon.setReviews(Arrays.asList(review));
		review.setPokemon(pokemon);

		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
		when(reviewRepository.save(review)).thenReturn(review);

		ReviewDto updateReturn = reviewService.updateReview(pokemonId, reviewId, reviewDto);

		Assertions.assertThat(updateReturn).isNotNull();
	}

	@Test
	public void ReviewService_DeletePokemonById_ReturnVoid() {
		int pokemonId = 1;
		int reviewId = 1;

		pokemon.setReviews(Arrays.asList(review));
		review.setPokemon(pokemon);

		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.of(pokemon));
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

		assertAll(() -> reviewService.deleteReview(pokemonId, reviewId));
	}

}
