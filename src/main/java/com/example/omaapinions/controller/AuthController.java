package com.example.omaapinions.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.omaapinions.dto.UserDto;
import com.example.omaapinions.models.UserSurvey;
import com.example.omaapinions.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @SuppressWarnings("FieldMayBeFinal")
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        UserDto user = new UserDto();

        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult bindingResult,
            Model model) {
        UserSurvey existingUserEmail = this.userService.findByEmail(user.getEmail());

        if (existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?failEmail";
        }

        UserSurvey existingUsername = this.userService.findByUsername(user.getUsername());

        if (existingUsername != null && existingUsername.getUsername() != null
                && !existingUsername.getUsername().isEmpty()) {
            return "redirect:/register?failUsername";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);

            return "register";
        }

        this.userService.saveUser(user);

        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "access-denied";
    }
}
