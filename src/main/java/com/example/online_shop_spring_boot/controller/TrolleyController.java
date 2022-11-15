package com.example.online_shop_spring_boot.controller;


import com.example.online_shop_spring_boot.configs.security.UserDetails;
import com.example.online_shop_spring_boot.domains.TrolleyProduct;
import com.example.online_shop_spring_boot.dto.TrolleyProductCreateDTO;
import com.example.online_shop_spring_boot.services.ProductService;
import com.example.online_shop_spring_boot.services.TrolleyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class TrolleyController {
    private final ProductService service;
    private final TrolleyService trolleyService;


    @GetMapping("/trolley")
    public String trolleyPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<TrolleyProduct> productList = trolleyService.findById ( userDetails.getUser ().getId () );
        Integer summa = 0;
        for (TrolleyProduct trolleyProduct : productList) {
            summa += trolleyProduct.getTheNumber () * Integer.parseInt ( trolleyProduct.getProduct ().getPrice () );
        }
        model.addAttribute ( "summa", summa );
        model.addAttribute ( "products", productList );
        return "trolley/trolley";
    }

    @PostMapping("/trolley")
    public String trolleyAdd(@AuthenticationPrincipal UserDetails userDetails) {
        trolleyService.update ( userDetails );
        return "redirect:/trolley";
    }


    @RequestMapping(value = "trolley/delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {
        model.addAttribute ( "trolley", trolleyService.get ( id ) );
        return "trolley/trolley_delete";
    }

    @RequestMapping(value = "trolley/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, @ModelAttribute TrolleyProductCreateDTO dto) throws InterruptedException {
        trolleyService.delete ( id, dto.getTheNumber () );

        return "redirect:/trolley";
    }




}
