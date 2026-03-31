package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserPageController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/users-page")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        repo.save(user);
        return "redirect:/userList";
    }

    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("users", repo.findAll());
        return "userList";
    }
}
