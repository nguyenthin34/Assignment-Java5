package com.poly.service.impl;

import com.poly.dto.AdminCartItem;
import com.poly.service.AdminShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SessionScope
@Service
public class AdminShoppingCartServiceImpl implements AdminShoppingCartService {
    private Map<Long, AdminCartItem> map = new HashMap<>();

    @Override
	public void add(AdminCartItem cartItem){
        AdminCartItem exAdminCartItem = map.get(cartItem.getCommodityId());
        if(exAdminCartItem != null){
            exAdminCartItem.setQuantity(cartItem.getQuantity() + exAdminCartItem.getQuantity());
        }else{
            map.put(cartItem.getCommodityId(), cartItem);
        }
    }
    @Override
	public void remove(Long id){
        map.remove(id);
    }

    @Override
	public void update(Long id, int quantity, double unitPrice, Long colorId, Long sizeId){
        AdminCartItem item = map.get(id);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setColorId(colorId);
        item.setSizeId(sizeId);
        if(item.getQuantity() <= 0){
            map.remove(id);
        }else if(item.getUnitPrice() < 0){
            map.get(id).setUnitPrice(0.0);
        }
    }
    @Override
    public void update(AdminCartItem cartItem){
        AdminCartItem item = map.get(cartItem.getCommodityId());
        item.setQuantity(cartItem.getQuantity());
        item.setUnitPrice(cartItem.getUnitPrice());
        item.setColorId(cartItem.getColorId());
        item.setSizeId(cartItem.getSizeId());
        if(item.getQuantity() <= 0){
            map.remove(cartItem.getCommodityId());
        }else if(item.getUnitPrice() < 0){
            map.get(cartItem.getCommodityId()).setUnitPrice(0.0);
        }
    }
    @Override
	public void clear() {
        map.clear();
    }

    @Override
	public Collection<AdminCartItem> getItems(){
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
                item -> item.getQuantity() * item.getUnitPrice()
        ).sum();
    }
}
