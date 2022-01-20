package com.poly.service.impl;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
//import com.poly.repository.IdvourcherDetailRepository;
import com.poly.repository.IdvourcherDetailRepository;
import com.poly.service.IdvourcherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdvourcherDetailServiceImpl  implements  IdvourcherDetailService{
    @Autowired
    IdvourcherDetailRepository repository;

    @Override
    @Query("SELECT i FROM Idvourcherdetail i " +
            "WHERE  i.iDVourcher.IDVourcher_id = ?1")
    public List<Idvourcherdetail> findByIDVourcher_id(Long id) {
        return repository.findByIDVourcher_id(id);
    }

    @Override
    @Query("SELECT i FROM Idvourcherdetail i " +
            "WHERE  i.iDVourcher.IDVourcher_id = ?1")
    public Page<Idvourcherdetail> findByIDVourcher_id(Long id, Pageable pageable) {
        return repository.findByIDVourcher_id(id, pageable);
    }

    @Override
    public List<Idvourcherdetail> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Idvourcherdetail> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public Idvourcherdetail getById(Long aLong) {
        return repository.getById(aLong);
    }

    @Override
    public Page<Idvourcherdetail> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends Idvourcherdetail> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Idvourcherdetail> findById(Long aLong) {
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
    public void delete(Idvourcherdetail entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
