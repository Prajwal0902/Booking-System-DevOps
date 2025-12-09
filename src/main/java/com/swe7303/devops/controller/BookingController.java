package com.swe7303.devops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.swe7303.devops.model.Booking;
import com.swe7303.devops.service.BookingService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // READ - List all bookings
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "bookings";
    }

    // CREATE - Save new booking (via modal)
    @PostMapping("/bookings")
    public String saveBooking(@ModelAttribute("booking") Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    // UPDATE - Save edited booking
    @PostMapping("/update-booking/{id}")
    public String updateBooking(@PathVariable("id") Integer id,
            @ModelAttribute("booking") Booking booking) {
        booking.setBookingId(id);
        bookingService.saveBooking(booking);
        return "redirect:/bookings";
    }

    // DELETE - Remove booking
    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable("id") Integer id) {
        bookingService.deleteBooking(id);
        return "redirect:/bookings";
    }
}
