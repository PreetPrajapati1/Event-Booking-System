package com.example.demo.controller;

import com.example.demo.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @GetMapping("/events")
    public String events(@RequestParam(required = false) String keyword,
                         Model model,
                         HttpSession session) {

        System.out.println("EVENT CONTROLLER HIT");

        //  session check
        if (session.getAttribute("user_id") == null) {
            return "redirect:/login";
        }

        // Search logic
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("events",
                    eventRepo.findByEventNameContainingIgnoreCase(keyword));
        } else {
            model.addAttribute("events", eventRepo.findAll());
        }

        model.addAttribute("keyword", keyword);

        return "events"; // events.html
    }
}
