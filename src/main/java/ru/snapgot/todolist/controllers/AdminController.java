package ru.snapgot.todolist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.snapgot.todolist.model.dto.NewCustomerDto;
import ru.snapgot.todolist.model.Role;
import ru.snapgot.todolist.model.User;
import ru.snapgot.todolist.repos.UserRepo;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/")
    public String createCustomer(@ModelAttribute("newCustomer") NewCustomerDto newCustomerDto){
        User user = new User(
                newCustomerDto.getUsername(),
                passwordEncoder.encode(newCustomerDto.getPassword()),
                Role.CUSTOMER
        );
        userRepo.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/")
    public String getAllCustomers(Model model){
        model.addAttribute("allUsers", userRepo.findAll());
        return "admin/allUsers";
    }

    @GetMapping("/create")
    public String createCustomerPage(Model model) {
        model.addAttribute("newCustomer", new NewCustomerDto());
        return "admin/createCustomer";
    }

    @PostMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username){
        userRepo.deleteByUsername(username);
        return "redirect:/admin/";
    }
}
