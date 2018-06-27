package com.ifsp.helpdesk.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ifsp.helpdesk.Util.Configuracao;
import com.ifsp.helpdesk.Util.MessageBox;
import com.ifsp.helpdesk.Util.TecnicoCategoria;
import com.ifsp.helpdesk.Util.Util;
import com.ifsp.helpdesk.dao.UsuarioDAO;
import com.ifsp.helpdesk.model.LoginViewModel;
import com.ifsp.helpdesk.model.entities.Usuario;


@Controller
@RequestMapping("login")
public class LoginController {
	@PostMapping("/")
    public String login(@Valid LoginViewModel user, BindingResult bindingResult, Model model,HttpSession session) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
		
		UsuarioDAO dao=new UsuarioDAO();
		Usuario usuarioLogado=dao.Login(user.getUsername(),user.getSenha());
		if(usuarioLogado==null)
		{
			model.addAttribute("erro","Usuário ou senha errado");
			return "../index";
		}
		session.setAttribute("usuario",usuarioLogado);
		
		Configuracao.getCurrent().setTecnico(usuarioLogado.getTecnico());
		
		if (usuarioLogado.getTipoUsuario().equals( TecnicoCategoria.ADMINISTRADOR.toString()))
			return "redirect:/admin";
		else if (usuarioLogado.getTipoUsuario().equals( TecnicoCategoria.TECNICO.toString()))
			return "redirect:/tecnico";
		else {
			return "index";
		}
		
		
    }
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.removeAttribute("usuario");
				return "redirect:/";
	}
}
