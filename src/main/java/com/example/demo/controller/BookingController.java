package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@Controller
public class BookingController {

    @Autowired
    private SeatRepository seatRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    @PostMapping("/bookSeat")
    public String bookSeat(@RequestParam("seat_id") int seatId,
                           HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/login";
        }

        // fetch seat
        Seat seat = seatRepo.findById(seatId).orElse(null);
        if (seat == null || seat.isBooked()) {
            return "redirect:/dashboard";
        }

        // fetch user safely
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // lock seat
        seat.setBooked(true);
        seat.setBookedBy(userId);
        seatRepo.save(seat);

        // create booking
        Booking booking = new Booking();
        booking.setSeat(seat);
        booking.setEvent(seat.getEvent());
        booking.setUser(user);
        booking.setBookingDate(new Timestamp(System.currentTimeMillis()));

        bookingRepo.save(booking);

        return "redirect:/myBookings";
        
    }
    
}
