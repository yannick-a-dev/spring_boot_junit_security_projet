package com.mockitojunit.mockitojunittes.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.mockitojunit.mockitojunittes.dto.PokemonDto;
import com.mockitojunit.mockitojunittes.dto.PokemonResponse;
import com.mockitojunit.mockitojunittes.entity.Pokemon;
import com.mockitojunit.mockitojunittes.repository.PokemonRepository;
import com.mockitojunit.mockitojunittes.service.Impl.PokemonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
	
	@Mock
	private PokemonRepository pokemonRepository;

	@InjectMocks
	private PokemonServiceImpl pokemonService;

	@Test
	public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
		int pokemonId = 1;
		Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").build();
		PokemonDto pokemonDto = PokemonDto.builder().id(1).name("pickachu").type("electric").build();

		when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

		PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

		Assertions.assertThat(savedPokemon).isNotNull();
	}

	@Test
	public void PokemonService_GetAllPokemon_ReturnsResponseDto() {
		Page<Pokemon> pokemons = Mockito.mock(Page.class);

		when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

		PokemonResponse savePokemon = pokemonService.getAllPokemon(1, 10);

		Assertions.assertThat(savePokemon).isNotNull();
	}

	@Test
	public void PokemonService_FindById_ReturnPokemonDto() {
		int pokemonId = 1;
		Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));

		PokemonDto pokemonReturn = pokemonService.getPokemonById(pokemonId);

		Assertions.assertThat(pokemonReturn).isNotNull();
	}

	@Test
	public void PokemonService_UpdatePokemon_ReturnPokemonDto() {
		int pokemonId = 1;
		Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();
		PokemonDto pokemonDto = PokemonDto.builder().id(1).name("pikachu").type("electric").type("this is a type")
				.build();

		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
		when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

		PokemonDto updateReturn = pokemonService.updatePokemon(pokemonDto, pokemonId);

		Assertions.assertThat(updateReturn).isNotNull();
	}

	@Test
	public void PokemonService_DeletePokemonById_ReturnVoid() {
		int pokemonId = 1;
		Pokemon pokemon = Pokemon.builder().id(1).name("pikachu").type("electric").type("this is a type").build();

		when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
		doNothing().when(pokemonRepository).delete(pokemon);

		assertAll(() -> pokemonService.deletePokemonId(pokemonId));
	}
}
