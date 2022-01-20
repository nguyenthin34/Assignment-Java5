package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Color;

public interface ColorService {
	public boolean existsById(Long aLong);
	void deleteAll();

	void delete(Color entity);

	long count();

	<S extends Color> long count(Example<S> example);

	<S extends Color> S saveAndFlush(S entity);

	Optional<Color> findById(Long id);

	List<Color> findAll(Sort sort);

	List<Color> findAll();

	Page<Color> findAll(Pageable pageable);

	<S extends Color> S save(S entity);

	Page<Color> findByNameContaining(String name, Pageable pageable);

	List<Color> findByNameContaining(String name);

}
