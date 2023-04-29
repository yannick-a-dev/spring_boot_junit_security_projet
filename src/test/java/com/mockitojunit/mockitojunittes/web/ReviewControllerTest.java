package com.mockitojunit.mockitojunittes.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;
import java.util.Arrays;
import org.hamcrest.CoreMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockitojunit.mockitojunittes.dto.PokemonDto;
import com.mockitojunit.mockitojunittes.dto.ReviewDto;
import com.mockitojunit.mockitojunittes.entity.Pokemon;
import com.mockitojunit.mockitojunittes.entity.Review;
import com.mockitojunit.mockitojunittes.service.ReviewService;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReviewService reviewService;
	@Autowired
	private ObjectMapper objectMapper;
	private Pokemon pokemon;
	private Review review;
	private ReviewDto reviewDto;
	private PokemonDto pokemonDto;

	@BeforeEach
	public void init() {
		pokemon = Pokemon.builder().name("pikachu").type("electric").build();
		pokemonDto = PokemonDto.builder().name("pickachu").type("electric").build();
		review = Review.builder().title("title").content("content").stars(5).build();
		reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
	}

	@Test
	public void ReviewController_GetReviewsByPokemonId_ReturnReviewDto() throws Exception {
		int pokemonId = 1;
		when(reviewService.getReviewsByPokemonId(pokemonId)).thenReturn(Arrays.asList(reviewDto));

		ResultActions response = mockMvc.perform(get("/api/pokemon/1/reviews").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pokemonDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(
				MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));
	}

	@Test
	public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
		int pokemonId = 1;
		int reviewId = 1;
		when(reviewService.updateReview(pokemonId, reviewId, reviewDto)).thenReturn(reviewDto);

		ResultActions response = mockMvc.perform(put("/api/pokemon/1/reviews/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
	}

	@Test
	public void ReviewController_CreateReview_ReturnReviewDto() throws Exception {
		int pokemonId = 1;
		when(reviewService.createReview(pokemonId, reviewDto)).thenReturn(reviewDto);

		ResultActions response = mockMvc.perform(post("/api/pokemon/1/reviews").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewDto)));

		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
	}

	@Test
	public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {
		int pokemonId = 1;
		int reviewId = 1;
		when(reviewService.getReviewById(reviewId, pokemonId)).thenReturn(reviewDto);

		ResultActions response = mockMvc
				.perform(get("/api/pokemon/1/reviews/1").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.stars", CoreMatchers.is(reviewDto.getStars())));
	}

	@Test
	public void ReviewController_DeleteReview_ReturnOk() throws Exception {
		int pokemonId = 1;
		int reviewId = 1;

		doNothing().when(reviewService).deleteReview(pokemonId, reviewId);

		ResultActions response = mockMvc
				.perform(delete("/api/pokemon/1/reviews/1").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
