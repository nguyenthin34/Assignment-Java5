package com.poly.repository;

import java.util.List;

import com.poly.domain.Color;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    List<Color> findByNameContaining(String name);

    Page<Color> findByNameContaining(String name, Pageable pageable);
}
