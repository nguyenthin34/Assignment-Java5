package com.poly.service;

import com.poly.domain.Idvourcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IdvourcherService {
    Page<Idvourcher> findByCreator_Username(String username, Pageable pageable);

    List<Idvourcher> findByCreator_Username(String username);

    List<Idvourcher> findByPhoneLike(String phone);

    Page<Idvourcher> findByPhoneLike(String phone, Pageable pageable);

    List<Idvourcher> findAll();

    List<Idvourcher> findAll(Sort sort);

    void flush();

    Idvourcher getById(Long aLong);

    Page<Idvourcher> findAll(Pageable pageable);

    <S extends Idvourcher> S save(S entity);

    Optional<Idvourcher> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Idvourcher entity);

    void deleteAll();

    List<Idvourcher> findByCreateDateBetween(Date date1, Date date2);

    Page<Idvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable);
}
