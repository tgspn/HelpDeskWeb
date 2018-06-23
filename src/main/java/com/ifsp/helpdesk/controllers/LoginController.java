package com.ifsp.helpdesk.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ifsp.helpdesk.model.LoginViewModel;

@Controller
public class LoginController {
	@PostMapping("/login")
    public String login(@Valid LoginViewModel user, BindingResult bindingResult, Model model) {
       
		return "home";
    }
}
