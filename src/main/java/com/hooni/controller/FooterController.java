package com.hooni.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FooterController
{
	public FooterController() {
	}

	@GetMapping("/footer")
	public String home(@RequestParam(name = "op", defaultValue = "products") String op,
					   @AuthenticationPrincipal UserDetails principal,
					   Model model) {
		if (op.equals("privacy")) {
			return "privacy";
		} else if (op.equals("about")) {
			return "aboutus";
		} else if (op.equals("policy")) {
			return "policy";
		} else if (op.equals("return")) {
			return "return";
		} else {
			return "error";
		}

	}


}
