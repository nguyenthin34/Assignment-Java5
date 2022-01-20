package com.poly.controller.site;

import com.poly.domain.Idvourcher;
import com.poly.domain.Idvourcherdetail;
import com.poly.domain.Product;
import com.poly.domain.User;
import com.poly.dto.IdvourchersDTO;
import com.poly.dto.SiteCart;
import com.poly.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("site-shopping-cart")
public class ShoppingCartSiteController {

    @Autowired
    SiteShoppingCartService service;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
    @Autowired
    StorageService sService;

    @Autowired
    CookiesService cookiesService;

    @Autowired
    HttpSession session;

    @Autowired
    IdvourcherService idvourcherService;

    @Autowired
    IdvourcherDetailService idvourcherDetailService;

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
        Resource file = sService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment: filename=\"" + file.getFilename() + "\"").body(file);
    }
    @GetMapping("list")
    public String getList(Model model){
        Collection<SiteCart> SiteCarts = service.getItems();
        model.addAttribute("cartItems", SiteCarts);
        model.addAttribute("total", service.getAmount());

        return "/site/shoppingcart/list-site-cart";
    }

    @PostMapping("/addCart")
    public String add(@RequestParam("commodityId") Optional<Long> commodityId,
                      @RequestParam("productId") Optional<Long> productId,
                      @RequestParam("name") Optional<String> name,
                      @RequestParam("image") Optional<String> image,
                      @RequestParam("quantity") Optional<Integer> quantity,
                 Model model, RedirectAttributes params){
        if(productId.isEmpty()  || commodityId.isEmpty()  ||
                name.isEmpty() || quantity.isEmpty() || image.isEmpty()){
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "redirect:/home-shop-poly";
        }else if(quantity.isPresent() && quantity.get() <= 0){
            params.addAttribute("message", "Please select quantity > 0");
            return "redirect:/detail-product/" + commodityId.get();
        }
        SiteCart siteCart = new SiteCart();
        siteCart.setQuantity(quantity.get());
        siteCart.setProductId(productId.get());
        siteCart.setName(name.get());
        siteCart.setImage(image.get());
        Optional<Product> product = productService.findById(productId.get());
        siteCart.setPrice(product.get().getPrice());
        siteCart.setColor(product.get().getColor());
        siteCart.setSize(product.get().getSize());
        service.add(siteCart);
        cookiesService.add("CartItem", siteCart.getProductId().toString(), 30);
        params.addAttribute("message", "Added successfully, please check your cart!");
        return "redirect:/home-shop-poly";
    }

    @GetMapping("remove/{productId}")
    public String remove(@PathVariable("productId") Optional<Long> productId,
                         RedirectAttributes params){
        if(productId.isPresent()) {
            service.remove(productId.get());
        }else{
            params.addAttribute("message","CartItem is not exists");
        }
        return  "redirect:/site-shopping-cart/list";
    }

    @PostMapping("update")
    public String update(@RequestParam("productId") Optional<Long> productId,
                         @RequestParam("quantity") Optional<Integer> quantity,
                         RedirectAttributes params){
        if(productId.isEmpty() || quantity.isEmpty()){
            params.addAttribute("message", "Error! An error occurred. Please try again later");
            return "redirect:/site-shopping-cart/list";
        }
        if(!productService.existsById(productId.get())){
            params.addAttribute("message", "Product is Empty");
            return "redirect:/site-shopping-cart/list";
        }
        service.update(productId.get(), quantity.get());
        return "redirect:/site-shopping-cart/list";
    }
    @GetMapping("clear")
    public String clear() {
        return "redirect:/site-shopping-cart/list";
    }

    @GetMapping("check-out-information")
    public String checkout(RedirectAttributes params, Model model){
        if(service.getItems().size() > 0){
            model.addAttribute("cartItems", service.getItems());
            model.addAttribute("cout", service.getCount());
            model.addAttribute("total", service.getAmount());
        }else{
            params.addAttribute("message", "You have not selected a product");
            return "redirect:/site-shopping-cart/list";
        }
        Optional<User> user = null;
        if(session.getAttribute("username") != null){
            user  = userService.findById(session.getAttribute("username").toString());
            model.addAttribute("user", user.get());
        }
        model.addAttribute("idvourchers", new IdvourchersDTO());
        return "/site/shoppingcart/check-out";
    }

    @PostMapping("check-out-save")
    public String checkoutSave(@Valid @ModelAttribute("idvourcher")IdvourchersDTO dto,
                               RedirectAttributes params){
        Idvourcher idvourcher = new Idvourcher();
        BeanUtils.copyProperties(dto, idvourcher);
        idvourcher.setCreateDate(new Date());
        idvourcher.setTotalAmount(service.getAmount());
        idvourcher.setStatus(0);
        if(session.getAttribute("username") != null){
            Optional<User> user = userService.findById(session.getAttribute("username").toString());
            idvourcher.setCreator(user.get());
        }
        idvourcherService.save(idvourcher);
        List<SiteCart> list = service.getItems().stream().toList();
        for(int i = 0; i < service.getCount(); i++){
            Idvourcherdetail idvourcherdetail = new Idvourcherdetail();
            idvourcherdetail.setIDVourcher(idvourcher);
            idvourcherdetail.setProduct(productService.getById(list.get(i).getProductId()));
            idvourcherdetail.setPrice(list.get(i).getPrice());
            idvourcherdetail.setQuantity(list.get(i).getQuantity());
            idvourcherDetailService.save(idvourcherdetail);
        }
        service.clear();
        params.addAttribute("message", "Your order will be reviewed soon, please wait, our staff will contact you soon!");
        return "redirect:/home-shop-poly";
    }
}
