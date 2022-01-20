package com.poly.service.impl;

import com.poly.domain.Provider;
import com.poly.repository.ProviderRepository;
import com.poly.service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService{
    @Autowired
    ProviderRepository repository;
    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
	public List<Provider> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
	public Page<Provider> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
	public List<Provider> findAll() {
        return repository.findAll();
    }

    @Override
	public List<Provider> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
	public Provider getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
	public Page<Provider> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
	public <S extends Provider> S save(S entity) {
        return repository.save(entity);
    }

    @Override
	public Optional<Provider> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
	public long count() {
        return repository.count();
    }

    @Override
	public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
	public void delete(Provider entity) {
        repository.delete(entity);
    }

    @Override
	public void deleteAll() {
        repository.deleteAll();
    }
}
