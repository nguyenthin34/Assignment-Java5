package com.poly.controller.admin;

import com.poly.domain.*;
import com.poly.dto.*;
import com.poly.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("admin/shoppingcart")
public class ShoppingCartController {
    @Autowired
    AdminShoppingCartService service;
    @Autowired
    CommodityService commodityService;
    @Autowired
    StatusService statusService;
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;
    @Autowired
    StorageService sService;
    @Autowired
    ProviderService providerService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }
    @ModelAttribute("users")
    public List<UsersDTO> getUsers(){
        return userService.findByRoleTrueAndStatusTrue().stream().map(item -> {
            UsersDTO dto = new UsersDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }
    @ModelAttribute("providers")
    public List<ProvidersDTO> getProviders(){
        return providerService.findAll().stream().map(item -> {
            ProvidersDTO dto = new ProvidersDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }
    @ModelAttribute("colors")
    public List<ColorsDTO> getColor(){
        return colorService.findAll().stream()
                .map(item -> {
                    ColorsDTO dto = new ColorsDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                }).toList();
    }

    @ModelAttribute("sizes")
    public List<SizesDTO> getSizes(){
        return sizeService.findAll().stream().map(item ->{
            SizesDTO dto = new SizesDTO();
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).toList();
    }
    @RequestMapping("list")
    public String view(Model model) {
        Collection<AdminCartItem> cartItems = service.getItems();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", service.getAmount());
        model.addAttribute("NoOfItems", service.getCount());
        model.addAttribute("cartItem" , new AdminCartItem());
        return "/admin/shoppingcart/listcart";
    }

    @GetMapping("add/{commodityId}")
    public String add(@PathVariable("commodityId")Long commodityId,
                      RedirectAttributes params){
        Optional<Commodity> commodity = commodityService.findById(commodityId);
        if(commodity.isPresent()){
            AdminCartItem cartItem = new AdminCartItem();
            int ranNum = ThreadLocalRandom.current().nextInt(1,10000);
//            BeanUtils.copyProperties(commodity.get(), cartItem);
            cartItem.setId(ranNum);
            cartItem.setName(commodity.get().getName());
            cartItem.setCommodityId(commodity.get().getCommodityId());
            cartItem.setQuantity(1);
            cartItem.setUnitPrice(0.0);
            cartItem.setImage(commodity.get().getImage());
            if(getColor().size() > 0){
                cartItem.setColorId(getColor().get(0).getColorId());
            }
            if(getSizes().size() > 0){
                cartItem.setSizeId(getSizes().get(0).getSizeId());
            }
            service.add(cartItem);

        }else{
            params.addAttribute("message","Commodity is not exists");
            return "redirect:/admin/shoppingcart/list";
        }
        return "redirect:/admin/shoppingcart/list";
    }
    @GetMapping("remove/{id}")
    public String remove(@PathVariable("id") Optional<Integer> id,
                         RedirectAttributes params){
        if(id.isPresent()) {
            service.remove(id.get());
        }else{
            params.addAttribute("message","CartItem is not exists");
        }
        return  "redirect:/admin/shoppingcart/list";
    }

    @PostMapping("update")
    public String update(@RequestParam("commodityId") Optional<Long> commodityId,
                        @RequestParam("id") Optional<Integer> Id,
                         @RequestParam("unitPrice") Optional<Double> unitPrice,
                         @RequestParam("quantity") Optional<Integer> quantity,
                         @RequestParam("colorId") Optional<Long> colorId,
                         @RequestParam("sizeId") Optional<Long> sizeId,
                         RedirectAttributes params){
        if(Id.isEmpty() ||commodityId.isEmpty() || unitPrice.isEmpty() || quantity.isEmpty() || colorId.isEmpty() || sizeId.isEmpty()){
            params.addAttribute("message", "Error! An error occurred. Please try again later!");
            return "redirect:/admin/shoppingcart/list";
        }else if(!colorService.existsById(colorId.get())){
            params.addAttribute("message", "Error! An error occurred. Color ID is Not Exists");
            return "redirect:/admin/shoppingcart/list";
        }else if(!sizeService.existsById(sizeId.get())){
            params.addAttribute("message", "Error! An error occurred. Size ID is Not Exists");
            return "redirect:/admin/shoppingcart/list";
        }
        else if(!commodityService.existsById(commodityId.get())){
            params.addAttribute("message", "Error! An error occurred. Commodity ID is Not Exists");
            return "redirect:/admin/shoppingcart/list";
        }else{
            service.update(Id.get(), quantity.get(), unitPrice.get(), colorId.get(), sizeId.get());
            return "redirect:/admin/shoppingcart/list";
        }

    }
    @GetMapping("clear")
    public String clear() {
        return "redirect:/admin/shoppingCart/list";
    }

    @Autowired
    AdminShoppingCartService shoppingCartService;
    @Autowired
    IrvourcherDetailService irvourcherDetailService;
    @Autowired
    IrvourcherService iservice;
    @PostMapping("savecart")
    public String save(RedirectAttributes params,
                       Model model,
                       @RequestParam("providerId") Optional<Long> providerId){
        Irvourcher entity = new Irvourcher();
//        BeanUtils.copyProperties(dto, entity);
        // User lấy từ session

        User user = userService.findById(session.getAttribute("username").toString()).get();
        entity.setTotal(service.getAmount());
        entity.setCreator(user);
        entity.setCreateDate(new Date());
        entity.setStatus(false);
        if(providerId.isPresent()){
            Provider provider = providerService.getById(providerId.get());
            entity.setProvider(provider);
        }else if(providerService.existsById(providerId.get())){
            params.addAttribute("message", "Provider is Not exists");
            return "redirect:/admin/shoppingcart/list";
        }else {
            params.addAttribute("message", "Provider is Not Null");
            return "redirect:/admin/shoppingcart/list";
        }
        entity.setStatus(false);
        iservice.save(entity);
        for(int i = 0;i < service.getCount(); i++){
            Irvourcherdetail irvourcherdetail = new Irvourcherdetail();
            irvourcherdetail.setIrvoucher(entity);
            List<AdminCartItem> list = service.getItems().stream().toList();
            Size size = sizeService.findById(list.get(i).getSizeId()).get();
            Color color = colorService.findById(list.get(i).getColorId()).get();
            Commodity commodity = commodityService.findById(list.get(i).getCommodityId()).get();
            irvourcherdetail.setSize(size);
            irvourcherdetail.setColor(color);
            irvourcherdetail.setCommodity(commodity);
            irvourcherdetail.setQuantity(list.get(i).getQuantity());
            irvourcherdetail.setPrice(list.get(i).getUnitPrice());
            irvourcherDetailService.save(irvourcherdetail);
        }
        shoppingCartService.clear();
        params.addAttribute("message", "Add success");
        return "redirect:/admin/commodities/commodityAddCart";
    }
}
