package com.poly.repository;

import com.poly.domain.Size;
import com.poly.domain.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findByNameContaining(String name);

    Page<Status> findByNameContaining(String name, Pageable pageable);
}
