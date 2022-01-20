package com.poly.controller.admin;

import com.poly.domain.Category;
import com.poly.domain.Provider;
import com.poly.dto.CategoriesDTO;
import com.poly.dto.ProvidersDTO;
import com.poly.service.CategoryService;
import com.poly.service.ProviderService;
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
@RequestMapping("admin/providers")
public class ProviderController {
    @Autowired
    ProviderService service;

    @GetMapping("add")
    public String add(Model model) {
        ProvidersDTO dto = new ProvidersDTO();
        dto.setIsEdit(false);
        model.addAttribute("provider", dto);
        return "/admin/provider/addOredit";
    }

    @PostMapping("saveOrupdate")
    public String saveOrupdate(RedirectAttributes params,
            @Valid @ModelAttribute("provider") ProvidersDTO dto,
            BindingResult bResult) {
        if (bResult.hasErrors()) {
            return "/admin/provider/addOredit";
        }
        Provider entity = new Provider();
        BeanUtils.copyProperties(dto, entity);
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/admin/providers/add";
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
        Page<Provider> resultPage = null;
        Page<Provider> resultPageDefault = null;
        if (StringUtils.hasText(name)) {
            resultPage = service.findByNameContaining(name, pageable);
            resultPageDefault = service.findByNameContaining(name, pageableDefault);
        } else {
            resultPage = service.findAll(pageable);
            resultPageDefault = service.findAll(pageableDefault);
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
        model.addAttribute("providerPage", resultPage);
        model.addAttribute("providers", resultPageDefault.getContent());
        return "/admin/provider/list";
    }

    @GetMapping("edit/{providerId}")
    public ModelAndView edit(@PathVariable("providerId") Long providerId,
            ModelMap model, RedirectAttributes params) {
        ProvidersDTO dto = new ProvidersDTO();
        Optional<Provider> optional = service.findById(providerId);
        if (optional.isPresent()) {
            Provider entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("provider", dto);
            return new ModelAndView("/admin/provider/addOredit", model);
        }
        params.addAttribute("message", "provider is not exists");
        return new ModelAndView("redirect:/admin/providers", model);
    }

    @GetMapping("setStatus/{providerId}")
    public String setStatus(@PathVariable("providerId") Long providerId,
            ModelMap model, RedirectAttributes params) {
        ProvidersDTO dto = new ProvidersDTO();
        Optional<Provider> optional = service.findById(providerId);
        if (optional.isPresent()) {
            Provider entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/providers";
    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Provider> resultPage = null;
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
        model.addAttribute("providerPage", resultPage);
        return "/admin/provider/list";
    }
}
