package com.hooni.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Replaces the original LoginController + LogoutController.
 *
 * Spring Security handles the actual POST /login authentication
 * (see SecurityConfig). This controller only renders the login page on GET.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error,
                            @RequestParam(name = "redirect", required = false) String redirect,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (redirect != null && !redirect.isBlank()) {
            model.addAttribute("redirect", redirect);
        }
        return "login";   // maps to classpath:/templates/login.ftl
    }
}
