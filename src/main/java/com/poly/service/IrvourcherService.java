package com.poly.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Irvourcher;

public interface IrvourcherService {

	void deleteAll();

	void delete(Irvourcher entity);

	void deleteById(Long aLong);

	long count();

	Optional<Irvourcher> findById(Long aLong);

	<S extends Irvourcher> S save(S entity);

	Page<Irvourcher> findAll(Pageable pageable);

	Irvourcher getById(Long aLong);

	List<Irvourcher> findByCreateDateBetween(Date date1, Date date2) ;

	public Page<Irvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable);

	List<Irvourcher> findAll(Sort sort);

	List<Irvourcher> findAll();
}
