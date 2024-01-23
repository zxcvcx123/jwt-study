package com.example.demo.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Users;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// http://localhost:8080/login => 여기서 동작을 안함
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		Users userEntity = userRepository.findByUsername(username); 
		return new PrincipalDetails(userEntity);
	}
	

}
