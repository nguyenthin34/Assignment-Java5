package com.poly.repository;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdvourcherDetailRepository extends JpaRepository<Idvourcherdetail, Long> {
    List<Idvourcherdetail> findByiDVourcher(Idvourcher irvourcher);

    Page<Idvourcherdetail> findByiDVourcher(Idvourcher irvourcher, Pageable pageable);
}
