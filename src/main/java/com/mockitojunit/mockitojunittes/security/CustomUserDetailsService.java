package com.mockitojunit.mockitojunittes.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mockitojunit.mockitojunittes.entity.Role;
import com.mockitojunit.mockitojunittes.entity.UserEntity;
import com.mockitojunit.mockitojunittes.repository.UserRepository;

@Service
public class CustomUserDetailsService {
	
	private UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
