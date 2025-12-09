package com.swe7303.devops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swe7303.devops.model.Booking;
import com.swe7303.devops.model.User;
import com.swe7303.devops.service.BookingService;
import com.swe7303.devops.service.PackageService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private BookingService bookingService;

    // Browse available packages
    @GetMapping("/browse-packages")
    public String browsePackages(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("packages", packageService.getAllPackages());
        return "customer_packages";
    }

    // View to book a package
    @GetMapping("/book-package")
    public String bookPackageForm(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        model.addAttribute("packages", packageService.getAllPackages());
        model.addAttribute("booking", new Booking());
        return "customer_book_package";
    }

    // Save booking
    @PostMapping("/book-package")
    public String saveBooking(@ModelAttribute("booking") Booking booking, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        booking.setStatus("Pending"); // Default status
        bookingService.saveBooking(booking);
        return "redirect:/customer/my-bookings";
    }

    // View own bookings
    @GetMapping("/my-bookings")
    public String myBookings(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Get user email from session or from loggedInUser object
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            User user = (User) session.getAttribute("loggedInUser");
            if (user != null) {
                userEmail = user.getEmail();
            } else {
                // If no user info available, redirect to login
                return "redirect:/login";
            }
        }

        // Filter bookings by customer email
        List<Booking> allBookings = bookingService.getAllBookings();
        final String email = userEmail;
        List<Booking> myBookings = allBookings.stream()
                .filter(b -> b.getCustomerEmail() != null && b.getCustomerEmail().equals(email))
                .collect(Collectors.toList());

        model.addAttribute("bookings", myBookings);
        return "customer_bookings";
    }

    // Cancel booking
    @GetMapping("/cancel-booking/{id}")
    public String cancelBooking(@PathVariable("id") Integer id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            booking.setStatus("Cancelled");
            bookingService.saveBooking(booking);
        }
        return "redirect:/customer/my-bookings";
    }
}
