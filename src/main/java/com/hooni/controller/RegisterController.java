package com.hooni.controller;

import com.hooni.db.User;
import com.hooni.repository.UserRepository;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Replaces the original RegisterController.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo        = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(HttpSession session, Model model) {
        SessionUtils.setSessionAttribute(session, "currentPage", "register");
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String userName,
                           @RequestParam String email,
                           @RequestParam String passwd,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName,
                           HttpSession session,
                           Model model) {
        Map<String, String> errors = new HashMap<>();

        if (userName == null || userName.isBlank()) errors.put("userName", "Username is required");
        if (email    == null || email.isBlank())    errors.put("email",    "Email is required");
        if (passwd   == null || passwd.isBlank())   errors.put("passwd",   "Password is required");

        if (userRepo.existsById(userName)) {
            errors.put("userName", "Username already taken");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "register";
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setFirstName(firstName != null ? firstName : "");
        user.setLastName(lastName   != null ? lastName  : "");
        user.setPassword(passwordEncoder.encode(passwd));
        user.setStatus("pending");  // original had an activation flow
        userRepo.save(user);
        
        SessionUtils.setSessionAttribute(session, "registeredUser", userName);
        SessionUtils.setSessionAttribute(session, "registrationTime", System.currentTimeMillis());

        return "redirect:/login?registered=true";
    }

    /** AJAX: check if username is already taken (CheckUserNameController) */
    @GetMapping("/checkname")
    @ResponseBody
    public String checkName(@RequestParam String userName) {
        return userRepo.existsById(userName) ? "taken" : "available";
    }
}
