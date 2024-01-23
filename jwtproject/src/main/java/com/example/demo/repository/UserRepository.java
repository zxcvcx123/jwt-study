package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	public Users findByUsername(String username);
	
}
