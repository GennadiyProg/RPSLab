package ru.snapgot.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("registration")
    public String createUser(@ModelAttribute("user") User user, Model model){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        model.addAttribute("user", user);
        return "redirect:/registration";
    }

    @GetMapping("account")
    public String personalAccount(Principal principal, Model model) {
        model.addAttribute("currentUser", userRepo.findByUsername(principal.getName()));
        return "account";
    }
}
