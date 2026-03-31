package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class HomeController {

    @Autowired
    private UserRepository repo;

    // TEST: fetch all users
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // TEST: save new user
    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return repo.save(user);
    }
}
