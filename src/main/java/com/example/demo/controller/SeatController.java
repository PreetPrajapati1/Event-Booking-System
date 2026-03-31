package com.example.demo.controller;

import com.example.demo.entity.Seat;
import com.example.demo.repository.SeatRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SeatController {

    @Autowired
    private SeatRepository seatRepo;

    @GetMapping("/seats")
    public String showSeats(@RequestParam("event_id") int eventId,
                            HttpSession session,
                            Model model) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Seat> seats = seatRepo.findByEventIdOrderBySeatRowAscSeatNumberAsc(eventId);

        model.addAttribute("seats", seats);
        model.addAttribute("eventId", eventId);

        return "seats"; // maps to seat.html
    }
}
