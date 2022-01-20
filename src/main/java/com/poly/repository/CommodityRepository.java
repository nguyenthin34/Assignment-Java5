package com.poly.repository;

import java.util.List;
import java.util.Optional;

import com.poly.domain.Category;
import com.poly.domain.Commodity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Long> {
    List<Commodity> findByNameContaining(String name);

    Page<Commodity> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT c FROM Commodity c " +
            " WHERE c.status = true and " +
            " exists (select p from Product p " +
            "               where c.commodityId = p.commodity.commodityId)")
    Page<Commodity> findByCommodity(Pageable pageable);

    List<Commodity> findByCategory(Optional<Category> category);

}
