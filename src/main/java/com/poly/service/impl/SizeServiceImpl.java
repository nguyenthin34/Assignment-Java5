package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Size;
import com.poly.repository.SizeRepository;
import com.poly.service.SizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements SizeService{

    @Autowired
    SizeRepository repository;

    @Override
	public List<Size> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
	public Page<Size> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
	public <S extends Size> S save(S entity) {
        return repository.save(entity);
    }

    @Override
	public Page<Size> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
	public List<Size> findAll() {
        return repository.findAll();
    }

    @Override
	public List<Size> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
	public Optional<Size> findById(Long id) {
        return repository.findById(id);
    }

    @Override
	public void flush() {
        repository.flush();
    }

    @Override
	public <S extends Size> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
	public long count() {
        return repository.count();
    }

    @Override
	public void delete(Size entity) {
        repository.delete(entity);
    }

    @Override
	public void deleteAll() {
        repository.deleteAll();
    }
    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }
}
