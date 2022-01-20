package com.poly.controller.admin;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import com.poly.service.IdvourcherDetailService;
import com.poly.service.IdvourcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/idvourcherDetails")
public class IdvourcherDetailController {
    @Autowired
    IdvourcherDetailService service;

    @Autowired
    IdvourcherService idvourcherService;
    @GetMapping("")
    public String list(Model model,
            @RequestParam("idvourcherID") Optional<Long> idvourcherID,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Idvourcherdetail> resultPage = null;
        if(idvourcherID.isEmpty()){
            resultPage = service.findAll(pageable);
        }else if(idvourcherID.isPresent()){
            Idvourcher idvourcher = idvourcherService.findById(idvourcherID.get()).get();
            model.addAttribute("irvourcherID", idvourcherID.get());
            resultPage = service.findByIDVourcher_id(idvourcherID.get(), pageable);
        }else{
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
        model.addAttribute("irvourcherDetailPage", resultPage);
        return "/admin/idvourcherdetail/list";
    }
    @GetMapping("paginated")
    public String search(Model model,
                         @RequestParam("idvourcherID") Optional<Long> idvourcherID,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Idvourcherdetail> resultPage = null;
        if(idvourcherID.isEmpty()){
            resultPage = service.findAll(pageable);
        }else if(idvourcherID.isPresent()){
            Idvourcher idvourcher = idvourcherService.getById(idvourcherID.get());
            model.addAttribute("idvourcherID", idvourcherID.get());
            resultPage = service.findByIDVourcher_id(idvourcherID.get(), pageable);
        }else{
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
        model.addAttribute("idvourcherDetailPage", resultPage);
        return "/admin/idvourcherdetail/list";
    }
}
