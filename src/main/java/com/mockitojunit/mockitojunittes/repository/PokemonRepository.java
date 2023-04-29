package com.mockitojunit.mockitojunittes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockitojunit.mockitojunittes.entity.Pokemon;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

	 Optional<Pokemon> findByType(String type);
}
