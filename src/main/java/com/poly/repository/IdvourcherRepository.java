package com.poly.repository;

import com.poly.domain.Idvourcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

public interface IdvourcherRepository extends JpaRepository<Idvourcher, Long> {
    List<Idvourcher> findByCreateDateBetween(Date date1, Date date2);
    Page<Idvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable);

    List<Idvourcher> findByCreator_Username(String username);
    Page<Idvourcher> findByCreator_Username(String username, Pageable pageable);


    List<Idvourcher> findByPhoneLike(String phone);
    Page<Idvourcher> findByPhoneLike(String phone, Pageable pageable);
}
