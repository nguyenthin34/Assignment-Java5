package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Category;
import com.poly.repository.CategoryRepository;
import com.poly.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repository;



    @Override
    public Page<Category> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
    public <S extends Category> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void delete(Category entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Category> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

}
