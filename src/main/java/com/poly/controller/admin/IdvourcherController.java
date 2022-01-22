package com.poly.controller.admin;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import com.poly.domain.Product;
import com.poly.service.IdvourcherDetailService;
import com.poly.service.IdvourcherService;
import com.poly.service.ParamService;
import com.poly.service.ProductService;
import groovy.transform.AutoImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/idvourchers")
public class IdvourcherController {
    @Autowired
    IdvourcherService service;
    @Autowired
    ParamService paramService;
    @Autowired
    IdvourcherDetailService idvourcherDetailService;
    @Autowired
    ProductService productService;
    @ModelAttribute("status")
    public Map<Integer, String> getStatus(){
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Unconfimred");
        map.put(1, "confimred");
        map.put(2, "Delivered");
        map.put(3, "Cancelled");
        return map;
    }
    @GetMapping("")
    public String list(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Idvourcher> resultPage = null;
        resultPage = service.findAll(pageable);
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);

            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("idvourcherPage", resultPage);
        return "/admin/Idvourcher/list";
    }

    @GetMapping("setStatus")
    public String setStatus(@RequestParam("id") Long id,
                            @RequestParam("status") Integer status,
                            ModelMap model, RedirectAttributes params) {
        Optional<Idvourcher> optional = service.findById(id);
        Idvourcher idvourcher = service.getById(id);
        if(status == null || id == null){
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "redirect:/admin/idvourchers";
        }
        if (idvourcher != null) {
            Idvourcher entity = idvourcher;
            if(getStatus().get(status).isEmpty()){
                params.addAttribute("message", "Status is not found");
                return "redirect:/admin/idvourchers";
            }
            entity.setStatus(status);
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/idvourchers";
    }

    @GetMapping("setExport/{id}")
    public String setExport(@PathVariable("id") Long id,
                            ModelMap model, RedirectAttributes params) {
        Optional<Idvourcher> optional = service.findById(id);
        Idvourcher idvourcher = service.getById(id);
        if (idvourcher != null) {
            Idvourcher entity = idvourcher;

            if(entity.getExport()){
                List<Idvourcherdetail> list = idvourcherDetailService.findByIDVourcher_id(entity.getIDVourcher_id());
                for(int i = 0;i < list.size(); i++ ){
                    Product product = productService.findById(list.get(i).getProduct().getProductId()).get();
                    product.setQuantity(product.getQuantity() + list.get(i).getQuantity());
                    productService.save(product);
                }
                entity.setExport(false);
            }else{
                List<Idvourcherdetail> list = idvourcherDetailService.findByIDVourcher_id(entity.getIDVourcher_id());
                for(int i = 0;i < list.size(); i++ ){
                    Product product = productService.findById(list.get(i).getProduct().getProductId()).get();
                    product.setQuantity(product.getQuantity() - list.get(i).getQuantity());
                    productService.save(product);
                }
                entity.setExport(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/idvourchers";
    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "createDate1", required = false) Optional<String> date1,
            @RequestParam(name = "createDate2", required = false) Optional<String> date2,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Idvourcher> resultPage = null;
        if(date1.isEmpty() && date2.isEmpty()){
            resultPage = service.findAll(pageable);
        }else if(date1.get().equals("") && date2.get().equals("")){
            resultPage = service.findAll(pageable);
        }else if(date1.isPresent() && date2.isEmpty() || date2.get().equals("")){
            resultPage = service.findByCreateDateBetween(paramService.getDate(date1.get(), "yyyy-MM-dd"),
                    new Date(),
                    pageable);
        }else if(date2.isPresent() && date1.isEmpty() || date1.get().equals("")){
            resultPage = service.findByCreateDateBetween(new Date(),
                    paramService.getDate(date2.get(), "yyyy-MM-dd"),
                    pageable);
        }
        else if (date1.isPresent() && date2.isPresent()) {
            resultPage = service.findByCreateDateBetween(paramService.getDate(date1.get(), "yyyy-MM-dd"),
                                                         paramService.getDate(date2.get(), "yyyy-MM-dd"),
                                                         pageable);
        } else {
            resultPage = service.findAll(pageable);
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
        model.addAttribute("idvourcherPage", resultPage);
        return "/admin/Idvourcher/list";
    }
}
