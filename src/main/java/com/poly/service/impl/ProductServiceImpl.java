package com.poly.service.impl;

import com.poly.domain.Product;
import com.poly.repository.Productrepository;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    Productrepository repositoty;

    public List<Product> findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(Long colorId, Long sizeId, Long commodityId) {
        return repositoty.findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(colorId, sizeId, commodityId);
    }

    @Override
    public List<Product> findByCommodity_CommodityId(Long commodityId) {
        return repositoty.findByCommodity_CommodityId(commodityId);
    }
    @Override
    public Page<Product> findByCommodity_CommodityId(Long commodityId, Pageable pageable) {
        return repositoty.findByCommodity_CommodityId(commodityId, pageable);
    }

    @Override
	public List<Product> findAll() {
        return repositoty.findAll();
    }

    @Override
	public List<Product> findAll(Sort sort) {
        return repositoty.findAll(sort);
    }

    @Override
	public Product getById(Long aLong) {
        return repositoty.getById(aLong);
    }

    @Override
	public Page<Product> findAll(Pageable pageable) {
        return repositoty.findAll(pageable);
    }

    @Override
	public <S extends Product> S save(S entity) {
        return repositoty.save(entity);
    }

    @Override
	public Optional<Product> findById(Long aLong) {
        return repositoty.findById(aLong);
    }

    @Override
	public boolean existsById(Long aLong) {
        return repositoty.existsById(aLong);
    }

    @Override
	public long count() {
        return repositoty.count();
    }

    @Override
	public void deleteById(Long aLong) {
        repositoty.deleteById(aLong);
    }

    @Override
	public void delete(Product entity) {
        repositoty.delete(entity);
    }

    @Override
	public void deleteAll() {
        repositoty.deleteAll();
    }
}
