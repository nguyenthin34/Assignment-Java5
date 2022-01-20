package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Size;

public interface SizeService {
	public boolean existsById(Long aLong);

	void deleteAll();

	void delete(Size entity);

	long count();

	<S extends Size> long count(Example<S> example);

	void flush();

	Optional<Size> findById(Long id);

	List<Size> findAll(Sort sort);

	List<Size> findAll();

	Page<Size> findAll(Pageable pageable);

	<S extends Size> S save(S entity);

	Page<Size> findByNameContaining(String name, Pageable pageable);

	List<Size> findByNameContaining(String name);

}
