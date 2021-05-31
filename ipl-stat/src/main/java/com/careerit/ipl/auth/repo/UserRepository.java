package com.careerit.ipl.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.careerit.ipl.auth.model.User;


public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);
	Optional<User> findByUsername(String username);
}
