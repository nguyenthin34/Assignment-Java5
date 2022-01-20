package com.poly.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.poly.domain.Size;
import com.poly.domain.Size;
import com.poly.dto.SizesDTO;
import com.poly.service.SizeService;
import com.poly.service.SizeService;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/sizes")
public class SizeController {
    @Autowired
    SizeService service;

    @GetMapping("add")
    public String add(Model model) {
        SizesDTO dto = new SizesDTO();
        dto.setIsEdit(false);
        model.addAttribute("size", dto);
        return "/admin/size/addOredit";
    }

    @PostMapping("saveOrupdate")
    public String saveOrupdate(RedirectAttributes params,
            @Valid @ModelAttribute("size") SizesDTO dto,
            BindingResult bResult) {
        if (bResult.hasErrors()) {
            return "/admin/size/addOredit";
        }
        Size entity = new Size();
        BeanUtils.copyProperties(dto, entity);
        service.save(entity);
        params.addAttribute("message", "Save is Success");
        return "redirect:/admin/sizes/add";
    }

    @GetMapping("")
    public String list(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("sizeP") Optional<Integer> size) {
        Pageable pageableDefault = PageRequest.of(0, 5, Sort.by("name"));

        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Size> resultPage = null;
        Page<Size> resultPageDefault = null;
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
            model.addAttribute("sizePage", resultPage);
        } else {
            model.addAttribute("sizePage", null);
        }
        model.addAttribute("sizes", resultPageDefault.getContent());
        return "/admin/size/list";
    }

    @GetMapping("edit/{SizeId}")
    public ModelAndView edit(@PathVariable("SizeId") Long SizeId,
            ModelMap model, RedirectAttributes params) {
        SizesDTO dto = new SizesDTO();
        Optional<Size> optional = service.findById(SizeId);
        if (optional.isPresent()) {
            Size entity = optional.get();
            BeanUtils.copyProperties(entity, dto);
            dto.setIsEdit(true);
            model.addAttribute("size", dto);
            return new ModelAndView("/admin/size/addOredit", model);
        }
        params.addAttribute("message", "Size is not exists");
        return new ModelAndView("redirect:/admin/sizes", model);
    }

    @GetMapping("setStatus/{SizeId}")
    public String setStatus(@PathVariable("SizeId") Long SizeId,
            ModelMap model, RedirectAttributes params) {
        Optional<Size> optional = service.findById(SizeId);
        if (optional.isPresent()) {
            Size entity = optional.get();
            if (entity.getStatus()) {
                entity.setStatus(false);
            } else {
                entity.setStatus(true);
            }
            service.save(entity);
        } else {
            params.addAttribute("message", "Entity is not found");
        }
        return "redirect:/admin/sizes";
    }

    @GetMapping("paginated")
    public String search(Model model,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("sizeP") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int sizePage = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage - 1, sizePage, Sort.by("name"));
        Page<Size> resultPage = null;
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
        model.addAttribute("sizePage", resultPage);
        return "/admin/size/list";
    }
}
