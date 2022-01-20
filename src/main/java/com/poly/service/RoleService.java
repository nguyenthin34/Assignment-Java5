package com.poly.service;

import com.poly.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    List<Role> findByNameContaining(String name);

    Page<Role> findByNameContaining(String name, Pageable pageable);

    List<Role> findAll(Sort sort);

    Role getById(Long aLong);

    Page<Role> findAll(Pageable pageable);

    <S extends Role> S save(S entity);

    Optional<Role> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Role entity);
}
