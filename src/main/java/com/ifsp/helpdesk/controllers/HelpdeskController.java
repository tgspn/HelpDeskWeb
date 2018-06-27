package com.ifsp.helpdesk.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifsp.helpdesk.Util.TecnicoCategoria;
import com.ifsp.helpdesk.Util.Util;
import com.ifsp.helpdesk.model.entities.Usuario;

@Controller
@RequestMapping("/")
public class HelpdeskController {
	@GetMapping("/")
	public String Index(HttpSession session) {
		 Usuario user = Util.getCurrentUser(session); 
	        if (user == null) 
	            return "../index"; 
	        if (user.getTipoUsuario().equals( TecnicoCategoria.ADMINISTRADOR.toString())) 
	            return "redirect:/admin"; 
	        else if (user.getTipoUsuario().equals( TecnicoCategoria.TECNICO.toString())) 
	            return "redirect:/tecnico"; 
	        else { 
	            return "../index"; 
	        } 
	}
}
