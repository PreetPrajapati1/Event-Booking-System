package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.entity.Seat;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.SeatRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private SeatRepository seatRepo;

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }
        model.addAttribute("events", eventRepo.findAll());
        return "adminDashboard";
    }

    @GetMapping("/createEvent")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "createEvent";
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String eventName,
                           @RequestParam String eventDate,
                           HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/login";
        }

        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDate(java.sql.Date.valueOf(eventDate));
        eventRepo.save(event);

        // AUTO CREATE SEATS
        char[] rows = {'A', 'B', 'C', 'D'};
        for (char row : rows) {
            for (int i = 1; i <= 10; i++) {
                Seat seat = new Seat();
                seat.setSeatRow(String.valueOf(row));
                seat.setSeatNumber(i);
                seat.setBooked(false);
                seat.setEvent(event);
                seatRepo.save(seat);
            }
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable int id) {
        seatRepo.deleteAll(
            seatRepo.findByEventIdOrderBySeatRowAscSeatNumberAsc(id)
        );
        eventRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }
}
