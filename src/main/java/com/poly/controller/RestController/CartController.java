//package com.poly.controller.RestController;
//
//import com.poly.domain.Category;
//import com.poly.dto.SiteCart;
//import com.poly.dto.SiteCart2;
//import com.poly.service.*;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpSession;
//import java.util.Collection;
//import java.util.List;
//@CrossOrigin("*")
//@RestController
//public class CartController {
//    @Autowired
//    SiteShoppingCartService service;
//
//    @Autowired
//    CategoryService categoryService;
//    @GetMapping("/api/cart")
//    public List<SiteCart2> get() {
//
//       return service.getItems().stream().map(
//               item ->{
//                   SiteCart2 dto = new SiteCart2();
//                   BeanUtils.copyProperties(item, dto);
//                   dto.setColor(item.getColor().getColorId());
//                   dto.setSize(item.getSize().getSizeId());
//                   return dto;
//               }
//       ).toList();
//    }
//
//    @GetMapping("/api/category")
//    public List<Category> getCategory(){
//        List<Category> list = categoryService.findAll();
//        return list;
//    }
//}
