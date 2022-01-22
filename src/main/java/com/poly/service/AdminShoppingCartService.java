package com.poly.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.poly.dto.AdminCartItem;

public interface AdminShoppingCartService {

	double getAmount();

	int getCount();

	Set<Integer> keySet();

    Map<Integer, AdminCartItem> getMap();

    Collection<AdminCartItem> getItems();

	void clear();


	void remove(Integer id);

//	void update(Long id, int quantity, double unitPrice, Long colorId, Long sizeId);

	void update(Integer id, Integer quantity, Double unitPrice, Long colorId, Long sizeId);

	void update(AdminCartItem cartItem);

	void add(AdminCartItem cartItem);
}
