package com.mockitojunit.mockitojunittes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockitojunit.mockitojunittes.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	 List<Review> findByPokemonId(int pokemonId);
}
