package com.poly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	 	List<User> findByFullnameContaining(String name);

	    Page<User> findByFullnameContaining(String name, Pageable pageable);

		User findByEmailContaining(String email);
		List<User> findByRoleTrueAndStatusTrue();
}
