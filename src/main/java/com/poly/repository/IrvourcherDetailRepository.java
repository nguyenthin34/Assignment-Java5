package com.poly.repository;

import com.poly.domain.Irvourcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.domain.Irvourcherdetail;

import java.util.List;

public interface IrvourcherDetailRepository extends JpaRepository<Irvourcherdetail, Long> {
    List<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher);

    Page<Irvourcherdetail> findAllByIrvoucher(Irvourcher irvourcher, Pageable pageable);
}
