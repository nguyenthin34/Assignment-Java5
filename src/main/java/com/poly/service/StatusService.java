package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Status;

public interface StatusService {

	void delete(Status entity);

	void deleteById(Long aLong);

	long count();

	boolean existsById(Long aLong);

	Optional<Status> findById(Long aLong);

	<S extends Status> S save(S entity);

	Page<Status> findAll(Pageable pageable);

	Status getById(Long aLong);

	List<Status> findAll(Sort sort);

	List<Status> findAll();

	Page<Status> findByNameContaining(String name, Pageable pageable);

	List<Status> findByNameContaining(String name);
}
