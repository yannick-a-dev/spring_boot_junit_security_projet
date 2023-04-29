package com.mockitojunit.mockitojunittes.web;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockitojunit.mockitojunittes.dto.PokemonDto;
import com.mockitojunit.mockitojunittes.dto.PokemonResponse;
import com.mockitojunit.mockitojunittes.dto.ReviewDto;
import com.mockitojunit.mockitojunittes.entity.Pokemon;
import com.mockitojunit.mockitojunittes.entity.Review;
import com.mockitojunit.mockitojunittes.service.PokemonService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PokemonService pokemonService;

	@Autowired
	private ObjectMapper objectMapper;
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
	public void PokemonController_CreatePokemon_ReturnCreated() throws Exception {
		given(pokemonService.createPokemon(ArgumentMatchers.any()))
				.willAnswer((invocation -> invocation.getArgument(0)));

		ResultActions response = mockMvc.perform(post("/api/pokemon/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pokemonDto)));

		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
	}

	@Test
	public void PokemonController_GetAllPokemon_ReturnResponseDto() throws Exception {
		PokemonResponse responseDto = PokemonResponse.builder().pageSize(10).last(true).pageNo(1)
				.content(Arrays.asList(pokemonDto)).build();
		when(pokemonService.getAllPokemon(1, 10)).thenReturn(responseDto);

		ResultActions response = mockMvc.perform(get("/api/pokemon").contentType(MediaType.APPLICATION_JSON)
				.param("pageNo", "1").param("pageSize", "10"));

		response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(
				MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
	}

	@Test
	public void PokemonController_PokemonDetail_ReturnPokemonDto() throws Exception {
		int pokemonId = 1;
		when(pokemonService.getPokemonById(pokemonId)).thenReturn(pokemonDto);

		ResultActions response = mockMvc.perform(get("/api/pokemon/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pokemonDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
	}

	@Test
	public void PokemonController_UpdatePokemon_ReturnPokemonDto() throws Exception {
		int pokemonId = 1;
		when(pokemonService.updatePokemon(pokemonDto, pokemonId)).thenReturn(pokemonDto);

		ResultActions response = mockMvc.perform(put("/api/pokemon/1/update").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pokemonDto)));

		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
	}

	@Test
	public void PokemonController_DeletePokemon_ReturnString() throws Exception {
		int pokemonId = 1;
		doNothing().when(pokemonService).deletePokemonId(1);

		ResultActions response = mockMvc
				.perform(delete("/api/pokemon/1/delete").contentType(MediaType.APPLICATION_JSON));

		response.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
