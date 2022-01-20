package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Category;
import com.poly.domain.Commodity;
import com.poly.repository.CommodityRepository;
import com.poly.service.CommodityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityRepository repository;

	@Override
	public List<Commodity> findByCategory(Optional<Category> category) {
		return repository.findByCategory(category);
	}

	@Override
	public boolean existsById(Long aLong) {
		return repository.existsById(aLong);
	}
	@Override
	@Query("SELECT c FROM Commodity c " +
			" WHERE c.status = true and " +
			" exists (select p from Product p " +
			"               where c.commodityId = p.commodity.commodityId)")
	public Page<Commodity> findByCommodity(Pageable pageable) {
		return repository.findByCommodity(pageable);
	}

	@Override
	public List<Commodity> findByNameContaining(String name) {
		return repository.findByNameContaining(name);
	}

	@Override
	public Page<Commodity> findByNameContaining(String name, Pageable pageable) {
		return repository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Commodity> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public Page<Commodity> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public List<Commodity> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Commodity> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public Optional<Commodity> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(Commodity entity) {
		repository.delete(entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Commodity getById(Long id) {
		return repository.getById(id);
	}

   

}
