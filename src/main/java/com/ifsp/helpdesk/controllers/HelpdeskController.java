package com.ifsp.helpdesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelpdeskController {
	@GetMapping("/")
	public String Index() {
		return "../index";
	}
}
