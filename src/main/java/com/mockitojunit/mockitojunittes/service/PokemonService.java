package com.mockitojunit.mockitojunittes.service;

import com.mockitojunit.mockitojunittes.dto.PokemonDto;
import com.mockitojunit.mockitojunittes.dto.PokemonResponse;

public interface PokemonService {
	
	PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonId(int id);
}
