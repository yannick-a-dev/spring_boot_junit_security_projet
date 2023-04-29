package com.mockitojunit.mockitojunittes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockitojunit.mockitojunittes.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String name);
}
