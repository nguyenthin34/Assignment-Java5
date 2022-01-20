package com.poly.service;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface IdvourcherDetailService {


    List<Idvourcherdetail> findAllByIDVourcher(Idvourcher irvourcher);

    Page<Idvourcherdetail> findAllByIDVourcher(Idvourcher irvourcher, Pageable pageable);

    List<Idvourcherdetail> findAll();

    List<Idvourcherdetail> findAll(Sort sort);

    void flush();

    Idvourcherdetail getById(Long aLong);

    Page<Idvourcherdetail> findAll(Pageable pageable);

    <S extends Idvourcherdetail> S save(S entity);

    Optional<Idvourcherdetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Idvourcherdetail entity);

    void deleteAll();
}
