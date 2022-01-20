package com.poly.repository;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdvourcherDetailRepository extends JpaRepository<Idvourcherdetail, Long> {

    @Query("SELECT i FROM Idvourcherdetail i " +
            "WHERE  i.iDVourcher.IDVourcher_id = ?1")
    List<Idvourcherdetail> findByIDVourcher_id(Long id);

    @Query("SELECT i FROM Idvourcherdetail i " +
            "WHERE  i.iDVourcher.IDVourcher_id = ?1")
    Page<Idvourcherdetail> findByIDVourcher_id(Long id, Pageable pageable);
}
