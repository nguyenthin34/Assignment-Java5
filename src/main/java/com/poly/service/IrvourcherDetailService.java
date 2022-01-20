package com.poly.service;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Irvourcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Irvourcherdetail;

public interface IrvourcherDetailService {

	List<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher);

	Page<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher, Pageable pageable);

	Irvourcherdetail getById(Long id);

	List<Irvourcherdetail> findAllById(Iterable<Long> longs);

	void deleteAll();

	void delete(Irvourcherdetail entity);

	void deleteById(Long id);

	long count();

	Optional<Irvourcherdetail> findById(Long id);

	List<Irvourcherdetail> findAll(Sort sort);

	List<Irvourcherdetail> findAll();

	Page<Irvourcherdetail> findAll(Pageable pageable);

	<S extends Irvourcherdetail> S save(S entity);

}
