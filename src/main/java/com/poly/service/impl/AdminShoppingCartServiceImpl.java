package com.poly.service.impl;

import com.poly.dto.AdminCartItem;
import com.poly.service.AdminShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.websocket.OnError;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SessionScope
@Service
public class AdminShoppingCartServiceImpl implements AdminShoppingCartService {
    private Map<Integer, AdminCartItem> map = new HashMap<>();

    @Override
	public void add(AdminCartItem cartItem){

        AdminCartItem item = map.get(cartItem.getId());
        List<AdminCartItem> list =  map.values().stream().toList();
        AdminCartItem exAdminCartItem = null;
       if(map.size() <= 0){
           map.put(cartItem.getId(), cartItem);
       }else{
           if(item == null){
               for(int i = 0; i < map.size(); i++){
                   if(cartItem.getCommodityId().longValue() == list.get(i).getCommodityId().longValue()
                           && cartItem.getColorId().longValue() == list.get(i).getColorId().longValue()
                           && cartItem.getSizeId().longValue() == list.get(i).getSizeId().longValue()
                           && cartItem.getUnitPrice().longValue() == list.get(i).getUnitPrice().longValue()){
                       exAdminCartItem = list.get(i);
                       break;
                   }
               }
               if(exAdminCartItem != null){
                   map.get(exAdminCartItem.getId()).setQuantity(cartItem.getQuantity() + exAdminCartItem.getQuantity());
               }else{
                   map.put(cartItem.getId(), cartItem);
               }

           }else {
               int ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
               Set<Integer> set = map.keySet();
               if (map.size() > 0) {
                   for (int i = 0; i < set.size(); i++) {
                       if (ranNum == map.get(set).getId()) {
                           ranNum = ThreadLocalRandom.current().nextInt(1, 10000);
                       }
                   }
               }
               map.put(ranNum, cartItem);
           }
       }
    }
    @Override
	public void remove(Integer id){
        map.remove(id);
    }

    @Override
	public void update(Integer id, Integer quantity, Double unitPrice, Long colorId, Long sizeId){
        AdminCartItem item = map.get(id);
            map.get(item.getId()).setQuantity(quantity);
            map.get(item.getId()).setUnitPrice(unitPrice);
            map.get(item.getId()).setColorId(colorId);
            map.get(item.getId()).setSizeId(sizeId);
            if(item.getQuantity() <= 0){
                map.remove(id);
            }else if(item.getUnitPrice() < 0){
                map.get(id).setUnitPrice(0.0);
            }
        AdminCartItem item1 = map.get(id);
            if(item1 != null){
                List<AdminCartItem> list =  map.values().stream().toList();
                AdminCartItem item_check = null;
                for(int i = 0; i < map.size(); i++){
                    if(     id.intValue() != list.get(i).getId().intValue() &&
                            item.getCommodityId().longValue() ==list.get(i).getCommodityId().longValue()
                            && item.getColorId().longValue() == list.get(i).getColorId().longValue()
                            && item.getSizeId().longValue() == list.get(i).getSizeId().longValue()
                            && item.getUnitPrice().doubleValue() == list.get(i).getUnitPrice().doubleValue()
                    ){
                        item_check = list.get(i);
                        break;
                    }
                }
                if(item_check != null){
                    map.get(id).setQuantity(item1.getQuantity() + item_check.getQuantity());
                    map.remove(item_check.getId());
                }
            }

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
    public Set<Integer> keySet(){
        return  map.keySet();
    }

    @Override
    public Map<Integer, AdminCartItem> getMap(){
        return map;
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
