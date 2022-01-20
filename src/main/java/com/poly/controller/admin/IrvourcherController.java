package com.poly.controller.admin;

import com.poly.domain.Category;
import com.poly.domain.Irvourcher;
import com.poly.dto.CategoriesDTO;
import com.poly.dto.IrvouchersDTO;
import com.poly.service.CategoryService;
import com.poly.service.IrvourcherService;
import com.poly.service.ParamService;
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
import org.thymeleaf.util.DateUtils;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/irvourchers")
public class IrvourcherController {
    @Autowired
    IrvourcherService service;
    @Autowired
    ParamService paramService;

    @GetMapping("")
    public String list(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Irvourcher> resultPage = null;
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
        model.addAttribute("irvourcherPage", resultPage);
        return "/admin/Irvourcher/list";
    }

//    @GetMapping("setStatus/{irVoucherId}")
//    public String setStatus(@PathVariable("irVoucherId") Long irVoucherId,
//            ModelMap model, RedirectAttributes params) {
//        Optional<Irvourcher> optional = service.findById(irVoucherId);
//        if (optional.isPresent()) {
//            Irvourcher entity = optional.get();
//            if (entity.getStatus()) {
//                entity.setStatus(false);
//            }
//            service.save(entity);
//        } else {
//            params.addAttribute("message", "Entity is not found");
//        }
//        return "redirect:/admin/irvourchers";
//    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "createDate1", required = false) Optional<String> date1,
            @RequestParam(name = "createDate2", required = false) Optional<String> date2,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Irvourcher> resultPage = null;
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
        model.addAttribute("irvourcherPage", resultPage);
        return "/admin/Irvourcher/list";
    }
}
