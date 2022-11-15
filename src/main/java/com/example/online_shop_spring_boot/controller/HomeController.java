package com.example.online_shop_spring_boot.controller;


import com.example.online_shop_spring_boot.domains.Product;
import com.example.online_shop_spring_boot.enums.ProductSize;
import com.example.online_shop_spring_boot.enums.ProductType;
import com.example.online_shop_spring_boot.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductService service;


    @RequestMapping
    @PreAuthorize("permitAll()")
    public String homPage() {
        return "fragments";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/admin")
    public String admin(@RequestParam Optional<String> search,
                        @RequestParam(name = "page") Optional<Integer> page,
                        @RequestParam(name = "limit") Optional<Integer> limit,
                        Model model) {
        String searchQuery = search.orElse ( "" );
        Page<Product> products = service.productAll ( searchQuery, page, limit );

        model.addAttribute ( "search", searchQuery );
        model.addAttribute ( "page", products );
        model.addAttribute ( "pageNumbers", IntStream.range ( 0, products.getTotalPages () ).toArray () );
        return "admin/admin";
    }


    @GetMapping("/home")
    public String homePage(Model model) {

        List<Product> shoes = service.shoesAll ( ProductType.findByName ( "SHOES" ) );
        List<Product> shirts = service.shoesAll ( ProductType.findByName ( "SHIRT" ) );


        model.addAttribute ( "shoes", shoes );
        model.addAttribute ( "shirts", shirts );
        model.addAttribute ( "size", ProductSize.values () );
        return "home";
    }

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String shopPage(@RequestParam Optional<String> search,
                           @RequestParam(name = "page") Optional<Integer> page,
                           @RequestParam(name = "limit") Optional<Integer> limit,
                           Model model) {
        String searchQuery = search.orElse ( "" );
        Page<Product> products = service.productAll ( searchQuery, page, limit );

        model.addAttribute ( "search", searchQuery );
        model.addAttribute ( "page", products );
        model.addAttribute ( "pageNumbers", IntStream.range ( 0, products.getTotalPages () ).toArray () );

        return "shop";
    }

}
