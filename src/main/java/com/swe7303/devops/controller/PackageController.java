package com.swe7303.devops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.swe7303.devops.model.Package;
import com.swe7303.devops.service.PackageService;

@Controller
public class PackageController {

	@Autowired
	private PackageService packageService;

	// READ - List all packages
	@GetMapping("/packages")
	public String listPackages(Model model) {
		model.addAttribute("packages", packageService.getAllPackages());
		return "packages";
	}

	// CREATE - Save new package (via modal)
	@PostMapping("/packages")
	public String postPackages(@ModelAttribute("package") Package pkg, Model model) {
		packageService.savePackage(pkg);
		return "redirect:/packages";
	}

	// UPDATE - Save edited package
	@PostMapping("/update-packages/{id}")
	public String updatePackage(@PathVariable("id") Integer id,
			@ModelAttribute("package") Package pkg,
			Model model) {
		pkg.setPid(id);
		packageService.savePackage(pkg);
		return "redirect:/packages";
	}

	// DELETE - Remove package
	@GetMapping("/delete-packages/{id}")
	public String deletePackage(@PathVariable("id") Integer id) {
		packageService.deletePackage(id);
		return "redirect:/packages";
	}
}
