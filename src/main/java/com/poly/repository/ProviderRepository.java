package com.poly.repository;

import com.poly.domain.Color;
import com.poly.domain.Provider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    List<Provider> findByNameContaining(String name);

    Page<Provider> findByNameContaining(String name, Pageable pageable);
}
