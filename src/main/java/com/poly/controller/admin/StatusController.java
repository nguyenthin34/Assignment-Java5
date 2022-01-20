package com.poly.controller.admin;

import com.poly.domain.Status;
import com.poly.dto.StatusesDTO;
import com.poly.service.CategoryService;
import com.poly.service.StatusService;
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
@RequestMapping("admin/statuses")
public class StatusController {
    @Autowired
    StatusService service;

    @GetMapping("add")
    public String add(Model model) {
        StatusesDTO dto = new StatusesDTO();
        dto.setIsEdit(false);
        model.addAttribute("status", dto);
        return "/admin/status/addOredit";
    }

    @PostMapping("saveOrupdate")
    public String saveOrupdate(RedirectAttributes params,
            @Valid @ModelAttribute("status") StatusesDTO dto,
            BindingResult bResult) {
        if (bResult.hasErrors()) {
            return "/admin/status/addOredit";
        }
        Status entity = new Status();
        BeanUtils.copyProperties(dto, entity);
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/admin/statuses/add";
    }

    @GetMapping("")
    public String list(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("name"));

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Status> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
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
        model.addAttribute("statusPage", resultPage);
        return "/admin/status/list";
    }

    @GetMapping("edit/{statusId}")
    public ModelAndView edit(@PathVariable("statusId") Long statusId,
            ModelMap model, RedirectAttributes params) {
        StatusesDTO dto = new StatusesDTO();
        Optional<Status> optional = service.findById(statusId);
        if (optional.isPresent()) {
            Status entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("status", dto);
            return new ModelAndView("/admin/status/addOredit", model);
        }
        params.addAttribute("message", "status is not exists");
        return new ModelAndView("redirect:/admin/statuses", model);
    }

    @GetMapping("setStatus/{statusId}")
    public String setStatus(@PathVariable("statusId") Long statusId,
            ModelMap model, RedirectAttributes params) {
        StatusesDTO dto = new StatusesDTO();
        Optional<Status> optional = service.findById(statusId);
        if (optional.isPresent()) {
            Status entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/statuses";
    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Status> resultPage = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
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
        model.addAttribute("statusPage", resultPage);
        return "/admin/status/list";
    }
}
