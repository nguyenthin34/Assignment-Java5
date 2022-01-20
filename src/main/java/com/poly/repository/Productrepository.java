package com.poly.repository;

import com.poly.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Productrepository extends JpaRepository<Product, Long> {
    List<Product> findByCommodity_CommodityId(Long commodityId);

    Page<Product> findByCommodity_CommodityId(Long commodityId, Pageable pageable);

    List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Long colorId, Long sizeId, Long commodityId);

}
