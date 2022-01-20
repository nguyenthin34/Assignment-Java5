package com.poly.controller.admin;

import com.poly.domain.*;
import com.poly.dto.CategoriesDTO;
import com.poly.dto.CommoditiesDTO;
import com.poly.dto.ProductsDTO;
import com.poly.dto.StatusesDTO;
import com.poly.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/products")
public class ProductController {
    @Autowired
    CommodityService commodityService;

    @Autowired
    IrvourcherService irvourcherService;

    @Autowired
    IrvourcherDetailService irvourcherDetailService;

    @Autowired
    ProductService productService;

    @Autowired
    StatusService statusService;
    @ModelAttribute("commodities")
    public List<CommoditiesDTO> getCommodities(){
        return commodityService.findAll().stream().map(
                item -> {
                    CommoditiesDTO dto = new CommoditiesDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }
        ).toList();
    }
    @ModelAttribute("statuses")
    public List<StatusesDTO> getStatuses(){
        return statusService.findAll().stream().map(
                item -> {
                    StatusesDTO dto = new StatusesDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }
        ).toList();
    }
    @GetMapping("addAll/{irvourcherId}")
    public String addAll(RedirectAttributes params,
                         @PathVariable("irvourcherId") Optional<Long> IrvourcherID) {
        List<Status> statusList = statusService.findAll();
        int sizeStatus = statusList.size();
       if(IrvourcherID.isEmpty()){
           params.addAttribute("message", "Irvourcher ID is Empty");
       }else{
           Irvourcher irvourcher = irvourcherService.getById(IrvourcherID.get());
           if(irvourcher == null){
               params.addAttribute("message", "Irvourcher is Empty");
           }else if(irvourcher.getStatus()){
               params.addAttribute("message", "Irvourcher is Saved");
           }else if(sizeStatus < 0){
               params.addAttribute("message", "Status is Empty");
           }else{

                List<Irvourcherdetail> list = irvourcherDetailService.findAllByIrvoucher(irvourcher);
                int size  = list.size();
                if(size <= 0){
                    params.addAttribute("message", "Irvourcher Detail is Not Product");
                }
                for(int i = 0; i < size; i++){
                    Product product = new Product();
                    BeanUtils.copyProperties(list.get(i), product);
                    product.setUnitPrice(list.get(i).getPrice());
                    product.setPrice(list.get(i).getPrice());
                    product.setStatus(statusList.get(0));
                    productService.save(product);
                }
                irvourcher.setStatus(true);
                irvourcherService.save(irvourcher);
                params.addAttribute("message", "Save Product is Success");
           }
       }
       return "redirect:/admin/irvourchers";
    }


    @GetMapping("")
    public String list(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Product> resultPage = null;
        resultPage = productService.findAll(pageable);
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productPage", resultPage);
        return "/admin/product/list";
    }

    @GetMapping("update")
    public String edit(
            @RequestParam("productId") Optional<Long> productId,
            @RequestParam("price") Optional<Double> price,
            @RequestParam("status") Optional<Long> statusId,
            ModelMap model, RedirectAttributes params) {
        ProductsDTO dto = new ProductsDTO();
        if(productId.isEmpty()){
            params.addAttribute("message", "Product ID is Empty");
        }else if(price.isEmpty()){
            params.addAttribute("message", "Price is Empty");
        }else if(statusId.isEmpty()){
            params.addAttribute("message", "Status ID is Empty");
        }else{
            Product entity = productService.getById(productId.get());
            Status status = statusService.getById(statusId.get());
            if(entity == null){
                params.addAttribute("message", "Product is Empty");
            }else if(status == null){
                params.addAttribute("message", "Status is Empty");
            }else{
                if(price.get() < 0){
                    entity.setPrice(0.0);
                }
                entity.setPrice(price.get());
                entity.setStatus(status);
                productService.save(entity);
            }
        }
        return "redirect:/admin/products";
    }

//    @GetMapping("setStatus/{categoryId}")
//    public String setStatus(@PathVariable("categoryId") Long categoryId,
//            ModelMap model, RedirectAttributes params) {
//        CategoriesDTO dto = new CategoriesDTO();
//        Optional<Category> optional = service.findById(categoryId);
//        if (optional.isPresent()) {
//            Category entity = optional.get();
//            if (entity.getStatus()) {
//                entity.setStatus(false);
//            } else {
//                entity.setStatus(true);
//            }
//            service.save(entity);
//        } else {
//            params.addAttribute("message", "Entity is not found");
//        }
//        return "redirect:/admin/categories";
//    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "commodityId", required = false) Optional<Long> commodityId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Product> resultPage = null;
        if(commodityId.isEmpty()){
            resultPage = productService.findAll(pageable);
        }else if(commodityId.isPresent()){
            resultPage = productService.findByCommodity_CommodityId(commodityId.get(), pageable);
        }else {
            resultPage = productService.findAll(pageable);
        }
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("productPage", resultPage);
        return "/admin/product/list";
    }
}
