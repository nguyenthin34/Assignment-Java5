package com.poly.service;

import com.poly.dto.SiteCart;

import java.util.Collection;

public interface SiteShoppingCartService {
    void add(SiteCart siteCart);

    void remove(Integer id);

    Collection<SiteCart> getItems();

    int getCount();

    double getAmount();

    void update(Integer id, int quantity);

    void clear();
}
