package com.poly.controller.site;

import com.poly.domain.Category;
import com.poly.domain.Commodity;
import com.poly.dto.CategoriesDTO;
import com.poly.service.CategoryService;
import com.poly.service.CommodityService;
import com.poly.service.ProductService;
import com.poly.service.StorageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CommoditySiteController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    ProductService productService;

    @Autowired
    StorageService sService;
    @Autowired
    CategoryService categoryService;

    @ModelAttribute("categories")
    public List<CategoriesDTO> getCommodities(){
        return categoryService.findAll().stream().map(
                item ->{
                    CategoriesDTO dto = new CategoriesDTO();
                    BeanUtils.copyProperties(item, dto);
                    return  dto;
                }
        ).toList();
    }

    @GetMapping("/collections")
    public String collections(){

       return "/site/product/filter-commodity";
    }

    @GetMapping("/collections/{categoryId}")
    public String collections(@PathVariable("categoryId")Optional<Long> categoryId,
                              Model model, RedirectAttributes params){

        Optional<Category> category = categoryService.findById(categoryId.get());
        List<Commodity> list = new ArrayList<>();
        if(category.isEmpty()){
            params.addAttribute("message", "The Commodity is currently out of stock, please come back later");
        }else{
           list = commodityService.findByCategory(Optional.of(category.get()));
        }

        model.addAttribute("list", list);
        return "/site/product/filter-commodity";
    }

    @GetMapping("/collections/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }
}
