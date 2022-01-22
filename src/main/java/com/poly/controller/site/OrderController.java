package com.poly.controller.site;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import com.poly.domain.User;
import com.poly.service.IdvourcherDetailService;
import com.poly.service.IdvourcherService;
import com.poly.service.StorageService;
import com.poly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("orders")
public class OrderController {

    @Autowired
    StorageService sService;

    @ModelAttribute("status")
    public Map<Integer, String> getStatus(){
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Unconfimred");
        map.put(1, "confimred");
        map.put(2, "Delivered");
        map.put(3, "Cancelled");
        return map;
    }

    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    IdvourcherDetailService idvourcherDetailService;

    @Autowired
    IdvourcherService idvourcherService;
    @GetMapping("")
     public String getOrders(Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Idvourcher> resultPage = null;
        if(session.getAttribute("username") != null){
            Optional<User> user = userService.findById(session.getAttribute("username").toString());
            if(user.isPresent()){
                resultPage = idvourcherService.findByCreator_Username(user.get().getUsername(), pageable);
                model.addAttribute("orders", resultPage);
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
        }
        return "/site/order/list";
    }
    @GetMapping("/search")
    public String getByPhone(@RequestParam("phone") Optional<String> phone,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model){
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("createDate"));
        Page<Idvourcher> resultPage = null;
        resultPage = idvourcherService.findByPhoneLike(phone.get(), pageable);
        model.addAttribute("orders", resultPage);
        int totalPages = resultPage.getTotalPages();
        if (totalPages > 0) {
            int start = Math.max(1, currentPage - 2);
            int end = Math.min(currentPage + 2, totalPages);
                List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
        return "/site/order/list";
        }

        @GetMapping("/{Id}")
        public String getOrderDetail(@PathVariable("Id") Optional<Long> id,
                                    Model model, RedirectAttributes params,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size){
            int currentPage = page.orElse(1);
            int sizePage = size.orElse(5);
            Pageable pageable = PageRequest.of(currentPage - 1, sizePage);
            Page<Idvourcherdetail> resultPage = null;
            if(id.isEmpty()){
                params.addAttribute("message", "Idvourcher ID is Empty");
                return "redirect:/orders";
            }
            Idvourcher idvourcher = idvourcherService.getById(id.get());
            model.addAttribute("idvourcherID", id.get());
            resultPage = idvourcherDetailService.findByIDVourcher_id(id.get(), pageable);
            int totalPages = resultPage.getTotalPages();
            if (totalPages > 0) {
                int start = Math.max(1, currentPage - 2);
                int end = Math.min(currentPage + 2, totalPages);

                List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("orderDetails", resultPage);
            return "/site/order/order-detail";
        }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }
}
