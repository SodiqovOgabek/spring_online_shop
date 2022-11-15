package com.example.online_shop_spring_boot.controller;


import com.example.online_shop_spring_boot.Bot;
import com.example.online_shop_spring_boot.configs.security.UserDetails;
import com.example.online_shop_spring_boot.domains.TrolleyProduct;
import com.example.online_shop_spring_boot.dto.UserCreateDTO;
import com.example.online_shop_spring_boot.services.AuthService;
import com.example.online_shop_spring_boot.services.TrolleyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TrolleyService trolleyService;
    private final AuthService service;
    private final Bot bot;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {

        return "auth/login";
    }


    @GetMapping("/register")
    public String registerPage(HttpServletRequest request,Model model) {
        model.addAttribute ( "dto", new UserCreateDTO () );

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSave(HttpServletRequest request,@Valid @ModelAttribute("dto") UserCreateDTO dto, BindingResult result) {
        if (result.hasErrors ()) {
            return "auth/register";
        }

        service.generate ( dto );
        bot.sendMessage(request.getRemoteAddr(), "register",dto.getUsername ()+" registratsiyadan o'tdi");

        return "auth/login";
    }


    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/logout";
    }


    @GetMapping("/history")
    public String historyPage(@RequestParam Optional<String> search,
                              @RequestParam(name = "page") Optional<Integer> page,
                              @RequestParam(name = "limit") Optional<Integer> limit,
                              Model model
            , @AuthenticationPrincipal UserDetails userDetails) {
        String searchQuery = search.orElse ( "" );
        Page<TrolleyProduct> products = trolleyService.userProductAll ( searchQuery, page, limit, userDetails.getUser ().getId () );

        model.addAttribute ( "search", searchQuery );
        model.addAttribute ( "page", products );
        model.addAttribute ( "pageNumbers", IntStream.range ( 0, products.getTotalPages () ).toArray () );

        return "auth/auth_history";
    }

}