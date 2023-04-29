package com.mockitojunit.mockitojunittes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mockitojunit.mockitojunittes.entity.Pokemon;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {

	@Autowired
	private PokemonRepository pokemonRepository;
	
	@Test
	public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
		//Arrange
		Pokemon pokemon = Pokemon.builder()
				.name("pikatu")
				.type("electric")
				.build();
		
		//Act
		Pokemon savedPokemon = pokemonRepository.save(pokemon);
		
		//Assert
		assertThat(savedPokemon).isNotNull();
		assertThat(savedPokemon.getId()).isGreaterThan(0);
	}
	
	@Test
    public void PokemonRepository_GetAll_ReturnMoreThenOnePokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        assertThat(pokemonList).isNotNull();
        assertThat(pokemonList.size()).isEqualTo(2);
    }
	
	@Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findById(pokemon.getId()).get();

        assertThat(pokemonList).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonList = pokemonRepository.findByType(pokemon.getType()).get();

        assertThat(pokemonList).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemonSave = pokemonRepository.findById(pokemon.getId()).get();
        pokemonSave.setType("Electric");
        pokemonSave.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(pokemonSave);

        assertThat(updatedPokemon.getName()).isNotNull();
        assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("electric").build();

        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonReturn = pokemonRepository.findById(pokemon.getId());

        assertThat(pokemonReturn).isEmpty();
    }

}
