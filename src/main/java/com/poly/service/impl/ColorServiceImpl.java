package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Color;
import com.poly.repository.ColorRepository;
import com.poly.service.ColorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    ColorRepository repository;
    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
	public List<Color> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
	public Page<Color> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
	public <S extends Color> S save(S entity) {
        return repository.save(entity);
    }

    @Override
	public Page<Color> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
	public List<Color> findAll() {
        return repository.findAll();
    }

    @Override
	public List<Color> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
	public Optional<Color> findById(Long id) {
        return repository.findById(id);
    }

    @Override
	public <S extends Color> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
	public <S extends Color> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
	public long count() {
        return repository.count();
    }

    @Override
	public void delete(Color entity) {
        repository.delete(entity);
    }

    @Override
	public void deleteAll() {
        repository.deleteAll();
    }

}
