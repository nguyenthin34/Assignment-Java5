package com.poly.service.impl;

import com.poly.dto.AdminCartItem;
import com.poly.dto.SiteCart;
import com.poly.service.SiteShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@SessionScope
@Service
public class SiteShoppingCartServiceImpl implements SiteShoppingCartService {
    private Map<Long, SiteCart> map = new HashMap<>();

    @Override
    public void add(SiteCart siteCart){
        SiteCart exSiteCart = map.get(siteCart.getProductId());
        if(exSiteCart != null){
            exSiteCart.setQuantity(siteCart.getQuantity() + exSiteCart.getQuantity());
            exSiteCart.setTotal(exSiteCart.getPrice() * exSiteCart.getQuantity());
        }else{
            siteCart.setTotal(siteCart.getPrice() * siteCart.getQuantity());
            map.put(siteCart.getProductId(), siteCart);
        }
    }

    @Override
    public void remove(Long id){
        map.remove(id);
    }

    @Override
    public void update(Long id, int quantity){
        SiteCart item = map.get(id);
        item.setQuantity(quantity);
        if(item.getQuantity() <= 0){
            map.remove(id);
        }else if(item.getPrice() < 0){
            map.get(id).setPrice(0.0);
        }
        item.setTotal(item.getPrice() * item.getQuantity());
    }

    public void clear() {
        map.clear();
    }

    @Override
    public Collection<SiteCart> getItems(){
        return  map.values();
    }
    @Override
    public int getCount(){
        if(map.isEmpty()){
            return 0;
        }
        return map.values().size();
    }

    @Override
    public double getAmount(){
        return map.values().stream().mapToDouble(
                item -> item.getQuantity() * item.getPrice()
        ).sum();
    }
}
