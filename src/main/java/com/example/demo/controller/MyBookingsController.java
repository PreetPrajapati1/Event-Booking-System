package com.example.demo.controller;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Seat;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.SeatRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyBookingsController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private SeatRepository seatRepo;

    @GetMapping("/myBookings")
    public String myBookings(HttpSession session, Model model) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Booking> bookings = bookingRepo.findByUser_Id(userId);
        model.addAttribute("bookings", bookings);

        return "myBookings";
    }

    @PostMapping("/cancelBooking")
    public String cancelBooking(@RequestParam int bookingId,
                                HttpSession session) {

        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking == null) {
            return "redirect:/myBookings";
        }

        // ✅ Only owner can cancel
       if (booking.getUser() == null || booking.getUser().getId() != userId) {
    return "redirect:/myBookings";
}

        // ✅ Free the seat
        Seat seat = booking.getSeat();
        if (seat != null) {
            seat.setBooked(false);
            seat.setBookedBy(null);
            seatRepo.save(seat);
        }

        // ✅ Delete booking
        bookingRepo.deleteById(bookingId);

        return "redirect:/myBookings";
    }
}
