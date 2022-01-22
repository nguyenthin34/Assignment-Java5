package com.poly.service.impl;

import com.poly.dto.AdminCartItem;
import com.poly.dto.SiteCart;
import com.poly.service.SiteShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SessionScope
@Service
public class SiteShoppingCartServiceImpl implements SiteShoppingCartService {
    private Map<Integer, SiteCart> map = new HashMap<>();

    @Override
    public void add(SiteCart siteCart){
        SiteCart exSiteCart = map.get(siteCart.getId());
        List<SiteCart> list =  map.values().stream().toList();
        SiteCart exAdminexSiteCart = null;
        if(map.size() <= 0){
            map.put(siteCart.getId(), siteCart);
        }else{
            if(exSiteCart == null){
                for(int i = 0; i < map.size(); i++){
                    if(siteCart.getProductId().longValue() == list.get(i).getProductId().longValue()
                            && siteCart.getColor().getColorId().longValue() == list.get(i).getColor().getColorId().longValue()
                            && siteCart.getSize().getSizeId().longValue() == list.get(i).getSize().getSizeId().longValue()
                            && siteCart.getPrice().longValue() == list.get(i).getPrice().longValue()){
                        exAdminexSiteCart = list.get(i);
                        break;
                    }
                }
                if(exAdminexSiteCart != null){
                    map.get(exAdminexSiteCart.getId()).setQuantity(siteCart.getQuantity() + exAdminexSiteCart.getQuantity());
                }else{
                    map.put(siteCart.getId(), siteCart);
                }
            }else {
                int ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
                Set<Integer> set = map.keySet();
                if (map.size() > 0) {
                    for (int i = 0; i < set.size(); i++) {
                        if (ranNum == map.values().stream().toList().get(i).getId()) {
                            ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
                        }
                    }
                }
                map.put(ranNum, siteCart);
            }
        }
    }

    @Override
    public void remove(Integer id){
        map.remove(id);
    }

    @Override
    public void update(Integer id, int quantity){
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
