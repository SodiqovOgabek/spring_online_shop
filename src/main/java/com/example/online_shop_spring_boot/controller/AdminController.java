package com.example.online_shop_spring_boot.controller;

import com.example.online_shop_spring_boot.configs.security.UserDetails;
import com.example.online_shop_spring_boot.domains.Product;
import com.example.online_shop_spring_boot.domains.TrolleyProduct;
import com.example.online_shop_spring_boot.dto.ProductCreateDTO;
import com.example.online_shop_spring_boot.dto.TrolleyProductCreateDTO;
import com.example.online_shop_spring_boot.enums.Currency;
import com.example.online_shop_spring_boot.enums.Gender;
import com.example.online_shop_spring_boot.enums.ProductSize;
import com.example.online_shop_spring_boot.enums.ProductType;
import com.example.online_shop_spring_boot.services.ProductService;
import com.example.online_shop_spring_boot.services.TrolleyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author "Sodiqov Ogabek"
 * @since 11/9/2022 5:11 AM (Wednesday)
 * online_shop_spring_boot/IntelliJ IDEA
 */
@Controller
@RequiredArgsConstructor
public class AdminController {
    private final ProductService service;
    private final TrolleyService trolleyService;





    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute ( "genders", Gender.values () );
        model.addAttribute ( "currencys", Currency.values () );
        model.addAttribute ( "productTypes", ProductType.values () );
        return "admin/add";
    }

    @PostMapping("/add")
    String productSave(@ModelAttribute ProductCreateDTO dto) {
        service.save ( dto );
        return "admin/admin";
    }
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String detailsPage(@PathVariable Long id, Model model) {
        model.addAttribute ( "product", service.get ( id ).get () );
        model.addAttribute ( "size", ProductSize.values () );
        return "product";

    }





    @RequestMapping(value = "/product/{id}", method = RequestMethod.POST)
    public String AddTrolley(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, @ModelAttribute TrolleyProductCreateDTO dto) {

        TrolleyProduct trolleyProduct = trolleyService.findByProductId ( id, userDetails.getUser ().getId (), ProductSize.findByName ( dto.getSize () ) );
        Optional<Product> product = service.finById ( id );
        if (Objects.nonNull ( trolleyProduct )) {
            trolleyService.updateTrolleyProduct ( trolleyProduct, dto );
        } else {
            trolleyService.save ( dto, userDetails, product );
        }
        return "redirect:/shop";
    }


    @RequestMapping(value = "product/delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {

        model.addAttribute ( "product", service.get ( id ) );
        return "admin/product_delete";
    }

    @RequestMapping(value = "product/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        service.delete ( id );
        return "redirect:/admin";
    }

    @RequestMapping(value = "product/update/{id}", method = RequestMethod.GET)
    public String updatePage(@PathVariable Long id, Model model) {
        model.addAttribute ( "genders", Gender.values () );
        model.addAttribute ( "currency", Currency.values () );
        model.addAttribute ( "productTypes", ProductType.values () );
        model.addAttribute ( "product", service.get ( id ) );
        return "admin/product_update";
    }

    @RequestMapping(value = "product/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, ProductCreateDTO dto) {
        service.update ( id, dto );
        return "redirect:/admin";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/history")
    public String historyPage(@RequestParam Optional<String> search,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "limit") Optional<Integer> limit,
                              Model model) {
        String searchQuery = search.orElse ( "" );
        Page<TrolleyProduct> products = trolleyService.productAll ( searchQuery, page, limit );

        model.addAttribute ( "search", searchQuery );
        model.addAttribute ( "page", products );
        model.addAttribute ( "pageNumbers", IntStream.range ( 0, products.getTotalPages () ).toArray () );

        return "admin/history";
    }

}
