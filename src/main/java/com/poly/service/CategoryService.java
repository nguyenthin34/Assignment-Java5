package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Category;

public interface CategoryService {

	List<Category> findByNameContaining(String name);

	Page<Category> findByNameContaining(String name, Pageable pageable);

	void deleteAll();

	void delete(Category entity);

	long count();

	void flush();

	Optional<Category> findById(Long id);

	List<Category> findAll(Sort sort);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	<S extends Category> S save(S entity);

}
