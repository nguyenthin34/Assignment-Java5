package com.poly.service;

import java.util.Collection;

import com.poly.dto.AdminCartItem;

public interface AdminShoppingCartService {

	double getAmount();

	int getCount();

	Collection<AdminCartItem> getItems();

	void clear();

	void update(Long id, int quantity, double unitPrice, Long colorId, Long sizeId);

	void update(AdminCartItem cartItem);

	void remove(Long id);

	void add(AdminCartItem cartItem);
}
