package com.poly.service.impl;

import com.poly.domain.Status;
import com.poly.repository.StatusRepository;
import com.poly.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    StatusRepository repository;

    @Override
	public List<Status> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
	public Page<Status> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
	public List<Status> findAll() {
        return repository.findAll();
    }

    @Override
	public List<Status> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
	public Status getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
	public Page<Status> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
	public <S extends Status> S save(S entity) {
        return repository.save(entity);
    }

    @Override
	public Optional<Status> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
	public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
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
	public void delete(Status entity) {
        repository.delete(entity);
    }
}
