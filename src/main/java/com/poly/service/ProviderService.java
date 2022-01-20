package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Provider;

public interface ProviderService {

	void deleteAll();

	void delete(Provider entity);

	void deleteById(Long aLong);

	long count();

	Optional<Provider> findById(Long aLong);

	<S extends Provider> S save(S entity);

	Page<Provider> findAll(Pageable pageable);

	Provider getById(Long aLong);

	List<Provider> findAll(Sort sort);

	boolean existsById(Long aLong);

	List<Provider> findAll();

	Page<Provider> findByNameContaining(String name, Pageable pageable);

	List<Provider> findByNameContaining(String name);
}
