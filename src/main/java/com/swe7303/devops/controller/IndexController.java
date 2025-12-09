package com.swe7303.devops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.swe7303.devops.service.PackageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@Autowired
	private PackageService packageService;

	// Admin portal entry - redirects to admin login if not logged in
	@GetMapping("/admin")
	public String adminPortal(HttpSession session) {
		String role = (String) session.getAttribute("userRole");
		if ("ADMIN".equals(role)) {
			return "redirect:/admin-dashboard";
		}
		return "redirect:/admin/login";
	}

	// About Us page
	@GetMapping("/about")
	public String aboutPage() {
		return "about";
	}

	// Contact page
	@GetMapping("/contact")
	public String contactPage() {
		return "contact";
	}

	// Public home page with packages
	@GetMapping("/browse-packages")
	public String browsePackages(Model model) {
		model.addAttribute("packages", packageService.getAllPackages());
		return "home";
	}
}
