package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.User;

public interface UserService {

	public List<User> findByRoleTrueAndStatusTrue();

	void deleteAll();

	void delete(User entity);

	void deleteById(String id);

	long count();

	<S extends User> boolean exists(Example<S> example);

	List<User> findAll(Sort sort);

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	<S extends User> S save(S entity);

	Page<User> findByFullnameContaining(String name, Pageable pageable);

	List<User> findByFullnameContaining(String name);

	Optional<User> findById(String id);

	User findByEmailContaining(String email);
}
