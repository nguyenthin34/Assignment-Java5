package com.poly.repository;

import com.poly.domain.Commodity;
import com.poly.domain.Irvourcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IrvourchersRepositoty extends JpaRepository<Irvourcher, Long> {
    List<Irvourcher> findByCreateDateBetween(Date date1, Date date2);
    Page<Irvourcher> findByCreateDateBetween(Date date1, Date date2, Pageable pageable);
}
