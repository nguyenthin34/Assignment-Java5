package com.poly.repository;

import com.poly.domain.Category;
import com.poly.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByNameContaining(String name);

    Page<Role> findByNameContaining(String name, Pageable pageable);
}
