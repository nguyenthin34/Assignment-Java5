package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.poly.domain.Product;

public interface ProductService {

	List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Long colorId, Long sizeId, Long commodityId);

    List<Product> findByCommodity_CommodityId(Long commodityId) ;

	Page<Product> findByCommodity_CommodityId(Long commodityId, Pageable pageable);

	void deleteAll();

	void delete(Product entity);

	void deleteById(Long aLong);

	long count();

	boolean existsById(Long aLong);

	Optional<Product> findById(Long aLong);

	<S extends Product> S save(S entity);

	Page<Product> findAll(Pageable pageable);

	Product getById(Long aLong);

	List<Product> findAll(Sort sort);

	List<Product> findAll();
}
