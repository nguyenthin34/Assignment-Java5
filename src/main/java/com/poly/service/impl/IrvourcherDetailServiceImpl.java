package com.poly.service.impl;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Irvourcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poly.domain.Irvourcherdetail;
import com.poly.repository.IrvourcherDetailRepository;
import com.poly.service.IrvourcherDetailService;

@Service
public class IrvourcherDetailServiceImpl implements IrvourcherDetailService {
	@Autowired
	IrvourcherDetailRepository repository;

	@Override
	public List<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher) {
		return repository.findAllByIrvoucher(irvourcher);
	}

	@Override
	public Page<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher, Pageable pageable) {
		return repository.findAllByIrvoucher(irvourcher, pageable);
	}

	@Override
	public List<Irvourcherdetail> findAllById(Iterable<Long> longs) {
		return repository.findAllById(longs);
	}

	@Override
	public <S extends Irvourcherdetail> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public Page<Irvourcherdetail> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public List<Irvourcherdetail> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Irvourcherdetail> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	@Override
	public Optional<Irvourcherdetail> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void delete(Irvourcherdetail entity) {
		repository.delete(entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Irvourcherdetail getById(Long id) {
		return repository.getById(id);
	}
	
	
}
