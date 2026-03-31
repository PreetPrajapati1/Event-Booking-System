package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class AuthController {

    @Autowired
    private UserRepository repo;

    // Register page
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Save registered user
    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        repo.save(user);
        return "redirect:/login";
    }

    // Login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Login validation
    @PostMapping("/login")
public String login(@RequestParam String email,
                    @RequestParam String password,
                    Model model,
                    jakarta.servlet.http.HttpSession session) {
    if (email.equals("admin@gmail.com") && password.equals("admin")) {
        session.setAttribute("user_id", 0); // 0 for admin
        session.setAttribute("isAdmin", true);
        return "redirect:/admin/dashboard"; // admin page
    }

    User user = repo.findByEmailAndPassword(email, password);

    if (user != null) {
        session.setAttribute("user_id", user.getId());   
        session.setAttribute("user_name", user.getName());
        return "redirect:/dashboard";
    } else {
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}


    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
