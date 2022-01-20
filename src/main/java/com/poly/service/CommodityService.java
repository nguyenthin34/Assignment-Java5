package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Commodity;
import org.springframework.data.jpa.repository.Query;

public interface CommodityService {

    List<Commodity> findByCategory(Optional<Category> category);

    public boolean existsById(Long aLong);

	Commodity getById(Long id);

	void deleteAll();


	void delete(Commodity entity);

	void deleteById(Long id);

	long count();

	Optional<Commodity> findById(Long id);

	List<Commodity> findAll(Sort sort);

	List<Commodity> findAll();

	Page<Commodity> findAll(Pageable pageable);

	<S extends Commodity> S save(S entity);

	@Query("SELECT c FROM Commodity c " +
			" WHERE c.status = true and " +
			" exists (select p from Product p " +
			"               where c.commodityId = p.commodity.commodityId)")
	Page<Commodity> findByCommodity(Pageable pageable);

	List<Commodity> findByNameContaining(String name);

	Page<Commodity> findByNameContaining(String name, Pageable pageable);
}
