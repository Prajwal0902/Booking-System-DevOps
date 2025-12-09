package com.swe7303.devops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.swe7303.devops.model.User;
import com.swe7303.devops.service.PackageService;
import com.swe7303.devops.service.UserService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PackageService packageService;

	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("packages", packageService.getAllPackages());
		return "home";
	}

	// Customer Login
	@GetMapping("/login")
	public String getCustomerLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String postCustomerLogin(@ModelAttribute User user, Model model, HttpSession session) {
		User usr = userService.userLogin(user.getEmail(), user.getPassword());
		if (usr != null && "CUSTOMER".equalsIgnoreCase(usr.getRole())) {
			session.setAttribute("loggedInUser", usr);
			session.setAttribute("username", usr.getUsername());
			session.setAttribute("userId", usr.getId());
			session.setAttribute("userRole", usr.getRole());
			session.setAttribute("userEmail", usr.getEmail());
			return "redirect:/customer-dashboard";
		}
		model.addAttribute("error", "Invalid customer credentials!");
		return "login";
	}

	// Admin Login
	@GetMapping("/admin/login")
	public String getAdminLogin() {
		return "admin_login";
	}

	@PostMapping("/admin/login")
	public String postAdminLogin(@ModelAttribute User user, Model model, HttpSession session) {
		User usr = userService.userLogin(user.getEmail(), user.getPassword());
		if (usr != null && "ADMIN".equalsIgnoreCase(usr.getRole())) {
			session.setAttribute("loggedInUser", usr);
			session.setAttribute("username", usr.getUsername());
			session.setAttribute("userId", usr.getId());
			session.setAttribute("userRole", usr.getRole());
			session.setAttribute("userEmail", usr.getEmail());
			return "redirect:/admin-dashboard";
		}
		model.addAttribute("error", "Invalid admin credentials!");
		return "admin_login";
	}

	@GetMapping("/admin-dashboard")
	public String getAdminDashboard(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		}
		model.addAttribute("un", username);
		return "admin_dashboard";
	}

	@GetMapping("/customer-dashboard")
	public String getCustomerDashboard(HttpSession session, Model model) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			return "redirect:/login";
		}
		model.addAttribute("un", username);
		return "customer_dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// READ - List all users
	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	// UPDATE - Save edited user
	@PostMapping("/update-user/{id}")
	public String updateUser(@PathVariable("id") Integer id,
			@ModelAttribute("user") User user) {
		user.setId(id);
		userService.userSignup(user); // Reuse signup method to save
		return "redirect:/users";
	}

	// DELETE - Remove user
	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}

	@GetMapping("/signup")
	public String getSignup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(@ModelAttribute User user) {
		user.setRole("CUSTOMER"); // All signups are customers
		userService.userSignup(user);
		return "redirect:/login";
	}

	// Admin adds new staff
	@PostMapping("/admin/add-staff")
	public String addStaff(@ModelAttribute User user) {
		user.setRole("ADMIN"); // All staff are admins
		userService.userSignup(user);
		return "redirect:/users";
	}

}
