package com.poly.controller.site;

import com.poly.domain.*;
import com.poly.dto.*;
import com.poly.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("detail-product")
public class ProductSiteController {

    @Autowired
    ProductService productService;

    @Autowired
    StorageService sService;

    @Autowired
    CommodityService commodityService;


    @GetMapping("{commodityId}")
    public String getCommodity(@PathVariable("commodityId") Optional<Long> commodityId,
                               Model model, RedirectAttributes params){

        if(commodityId.isEmpty()){
            params.addAttribute("message", "Commodity ID is Empty");
            return "redirect:/home-shop-poly";
        }
        Optional<Commodity> commodity = commodityService.findById(commodityId.get());
        if(commodity.isEmpty()){
            params.addAttribute("message", "Commodity is Empty");
            return "redirect:/home-shop-poly";
        }
        List<Product> list = productService.findByCommodity_CommodityId(commodityId.get());
       if(list.size() <= 0 || list == null){
           params.addAttribute("message", "The product is temporarily out of stock, please come back later");
           return "redirect:/home-shop-poly";
       }else{
           model.addAttribute("products", list);
           model.addAttribute("product", list.get(0));
       }
        List<ColorsDTO> colors =  list.stream()
                        .map(item -> {
                            ColorsDTO dto = new ColorsDTO();
                            BeanUtils.copyProperties(item.getColor(), dto);
                            return dto;
                        }).distinct().toList();
        model.addAttribute("colors", colors);
        List<SizesDTO> sizes =  list.stream()
                .map(item -> {
                    SizesDTO dto = new SizesDTO();
                    BeanUtils.copyProperties(item.getSize(), dto);
                    return dto;
                }).distinct().toList();
        model.addAttribute("sizes", sizes);
        model.addAttribute("siteCart", new SiteCart());
        return "/site/productDetail";
    }
    @GetMapping("update")
    public String update(@RequestParam("commodityId") Optional<Long> commodityId,
            @RequestParam("colorId") Optional<Long> colorId,
                         @RequestParam("sizeId") Optional<Long> sizeId,
                               RedirectAttributes params,
                               Model model){
        if(colorId.isEmpty() || sizeId.isEmpty() || commodityId.isEmpty()){
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "redirect:/home-shop-poly";
        }else if((colorId.isEmpty() || colorId.isEmpty()) && commodityId.isPresent()){
            return  "redirect:/detail-product/" + commodityId.get();
        }
        List<Product> listcs =
        productService.findByColor_ColorIdAndSize_SizeIdAndCommodity_CommodityIdOrderByQuantity(colorId.get(), sizeId.get(), commodityId.get());
        List<Product> list = productService.findByCommodity_CommodityId(commodityId.get());
        if(listcs.size() <= 0){
            params.addAttribute("message", "The product you selected is out of stock");
            return "redirect:/detail-product/" + commodityId.get();
        }else{
            model.addAttribute("product", listcs.get(0));
        }
        List<ColorsDTO> colors =  list.stream()
                .map(item -> {
                    ColorsDTO dto = new ColorsDTO();
                    BeanUtils.copyProperties(item.getColor(), dto);
                    return dto;
                }).distinct().toList();
        model.addAttribute("colors", colors);
        List<SizesDTO> sizes =  list.stream()
                .map(item -> {
                    SizesDTO dto = new SizesDTO();
                    BeanUtils.copyProperties(item.getSize(), dto);
                    return dto;
                }).distinct().toList();
        model.addAttribute("sizes", sizes);
        model.addAttribute("siteCart", new SiteCart());
        return "/site/productDetail";
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }
}
