package com.poly.controller.admin;

import com.poly.domain.Irvourcher;
import com.poly.domain.Irvourcherdetail;
import com.poly.dto.IrvouchersDTO;
import com.poly.service.IrvourcherDetailService;
import com.poly.service.IrvourcherService;
import com.poly.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/irvourcherDetails")
public class IrvourcherDetailController {
    @Autowired
    IrvourcherDetailService service;

    @Autowired
    IrvourcherService irvourcherService;
    @GetMapping("")
    public String list(Model model,
            @RequestParam("irvourcherID") Optional<Long> irvourcherID,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Irvourcherdetail> resultPage = null;
        if(irvourcherID.isEmpty()){
            resultPage = service.findAll(pageable);
        }else if(irvourcherID.isPresent()){
            Irvourcher irvourcher = irvourcherService.getById(irvourcherID.get());
            model.addAttribute("irvourcherID", irvourcherID.get());
            resultPage = service.findAllByIrvoucher(irvourcher, pageable);
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
        return "/admin/irvourcherdetail/list";
    }
    @GetMapping("paginated")
    public String search(Model model,
                         @RequestParam("irvourcherID") Optional<Long> irvourcherID,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
        Page<Irvourcherdetail> resultPage = null;
        if(irvourcherID.isEmpty()){
            resultPage = service.findAll(pageable);
        }else if(irvourcherID.isPresent()){
            Irvourcher irvourcher = irvourcherService.getById(irvourcherID.get());
            model.addAttribute("irvourcherID", irvourcherID.get());
            resultPage = service.findAllByIrvoucher(irvourcher, pageable);
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
        return "/admin/irvourcherdetail/list";
    }
}
