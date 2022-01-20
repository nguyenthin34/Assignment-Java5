package com.poly.service.impl;

import com.poly.domain.Role;
import com.poly.repository.RoleRepository;
import com.poly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Role> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public Page<Role> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Override
    public List<Role> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Role getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Role> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Role> findById(Long aLong) {
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
    public void delete(Role entity) {
        repository.delete(entity);
    }

}
