package com.poly.service.impl;

import com.poly.domain.Irvourcher;
import com.poly.repository.IrvourchersRepositoty;
import com.poly.service.IrvourcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IrvourcherServiceIpml implements IrvourcherService {

    @Autowired
    IrvourchersRepositoty repositoty;

    @Override
	public List<Irvourcher> findAll() {
        return repositoty.findAll();
    }

    @Override
	public List<Irvourcher> findAll(Sort sort) {
        return repositoty.findAll(sort);
    }

    @Override
	public Irvourcher getById(Long aLong) {
        return repositoty.getById(aLong);
    }

    @Override
	public Page<Irvourcher> findAll(Pageable pageable) {
        return repositoty.findAll(pageable);
    }

    @Override
	public <S extends Irvourcher> S save(S entity) {
        return repositoty.save(entity);
    }
    @Override
    public List<Irvourcher> findByCreateDateBetween(Date date1, Date date2) {
        return repositoty.findByCreateDateBetween(date1, date2);
    }
    @Override
    public Page<Irvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable) {
        return repositoty.findByCreateDateBetween(date1,date2, pageable);
    }

    @Override
	public Optional<Irvourcher> findById(Long aLong) {
        return repositoty.findById(aLong);
    }

    @Override
	public long count() {
        return repositoty.count();
    }

    @Override
	public void deleteById(Long aLong) {
        repositoty.deleteById(aLong);
    }

    @Override
	public void delete(Irvourcher entity) {
        repositoty.delete(entity);
    }

    @Override
	public void deleteAll() {
        repositoty.deleteAll();
    }
}
