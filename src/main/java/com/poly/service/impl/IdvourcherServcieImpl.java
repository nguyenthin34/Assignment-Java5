package com.poly.service.impl;

import com.poly.domain.Idvourcher;
import com.poly.repository.IdvourcherRepository;
import com.poly.service.IdvourcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IdvourcherServcieImpl implements IdvourcherService {
    @Autowired
    IdvourcherRepository repository;

    @Override
    public Page<Idvourcher> findByCreator_Username(String username, Pageable pageable) {
        return repository.findByCreator_Username(username, pageable);
    }

    @Override
    public List<Idvourcher> findByCreator_Username(String username) {
        return repository.findByCreator_Username(username);
    }

    @Override
    public List<Idvourcher> findByPhoneLike(String phone) {
        return repository.findByPhoneLike(phone);
    }

    @Override
    public Page<Idvourcher> findByPhoneLike(String phone, Pageable pageable) {
        return repository.findByPhoneLike(phone, pageable);
    }

    @Override
    public List<Idvourcher> findAll() {
        return repository.findAll();
    }
    @Override
    public List<Idvourcher> findAll(Sort sort) {
        return repository.findAll(sort);
    }
    @Override
    public void flush() {
        repository.flush();
    }
    @Override
    public Idvourcher getById(Long aLong) {
        return repository.getById(aLong);
    }
    @Override
    public Page<Idvourcher> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    @Override
    public <S extends Idvourcher> S save(S entity) {
        return repository.save(entity);
    }
    @Override
    public Optional<Idvourcher> findById(Long aLong) {
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
    public void delete(Idvourcher entity) {
        repository.delete(entity);
    }
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Idvourcher> findByCreateDateBetween(Date date1, Date date2) {
        return repository.findByCreateDateBetween(date1, date2);
    }

    @Override
    public Page<Idvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable) {
        return repository.findByCreateDateBetween(date1, date2, pageable);
    }
}
