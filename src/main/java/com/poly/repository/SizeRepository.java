package com.poly.repository;

import java.util.List;
import com.poly.domain.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findByNameContaining(String name);

    Page<Size> findByNameContaining(String name, Pageable pageable);
}
