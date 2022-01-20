package com.poly.service;

import com.poly.dto.SiteCart;

import java.util.Collection;

public interface SiteShoppingCartService {
    void add(SiteCart siteCart);

    void remove(Long id);

    void update(Long id, int quantity);

    Collection<SiteCart> getItems();

    int getCount();

    double getAmount();

    void clear();
}
