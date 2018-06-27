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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ifsp.helpdesk.Util.Util;
import com.ifsp.helpdesk.dao.TecnicoDAO;
import com.ifsp.helpdesk.dao.UsuarioDAO;
import com.ifsp.helpdesk.model.UsuarioViewModel;
import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.model.entities.Tecnico;
import com.ifsp.helpdesk.model.entities.Usuario;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@GetMapping("")
	public ModelAndView Index(HttpSession session) throws SQLException, ClassNotFoundException {
		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		TecnicoDAO daoTec = new TecnicoDAO();
		UsuarioDAO dao = new UsuarioDAO();

		ModelAndView mav = new ModelAndView("listaTec");
		mav.addObject("model", dao.List());
		mav.addObject("usuario", user.getUsuario());
		mav.addObject("tecnicos", daoTec.List());
		return mav;
	}

	@GetMapping("/criar")
	public ModelAndView Criar(HttpSession session) throws SQLException, ClassNotFoundException {
		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		TecnicoDAO daoTec = new TecnicoDAO();

		ModelAndView mav = new ModelAndView("usuario");
		mav.addObject("model", new UsuarioViewModel());
		mav.addObject("usuario", user.getUsuario());
		mav.addObject("action", "criar");
		mav.addObject("tecnicos", daoTec.List());
		return mav;
	}

	@GetMapping("/editar")
	public ModelAndView Editar(@RequestParam(value = "id", required = true) int id, HttpSession session)
			throws ClassNotFoundException, SQLException {
		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		TecnicoDAO daoTec = new TecnicoDAO();

		ModelAndView mav = new ModelAndView("usuario");
		UsuarioDAO dao = new UsuarioDAO();
		mav.addObject("model", new UsuarioViewModel(dao.Find(id)));
		mav.addObject("action", "editar");
		mav.addObject("usuario", user.getUsuario());
		mav.addObject("tecnicos", daoTec.List());
		return mav;
	}

	@PostMapping("/criar")
	public String Criar(@Valid UsuarioViewModel user, BindingResult bindingResult, Model model, HttpSession session)
			throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {

		UsuarioDAO dao = new UsuarioDAO();
		TecnicoDAO daoTec = new TecnicoDAO();

		Tecnico tecnico = null;
		if (user.getTecnico_id() != null)
			tecnico = daoTec.Find(user.getTecnico_id());
		else {
			tecnico = user.getTecnico();
			if (!tecnico.getNome().isEmpty())
				daoTec.Insert(tecnico);
		}

		Usuario usuario = new Usuario(0, user.getTipoUsuario().toString(), user.getUsuario(), user.getSenha(), tecnico);
		dao.Insert(usuario);

		return "redirect:/usuario";

	}

	@PostMapping("/editar")
	public String Editar(@Valid UsuarioViewModel user, BindingResult bindingResult, Model model, HttpSession session)
			throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {

		UsuarioDAO dao = new UsuarioDAO();
		TecnicoDAO daoTec = new TecnicoDAO();

		Usuario usuario = dao.Find(user.getId());
		if (usuario == null) {

			model.addAttribute("model", user);
			return "/editar";

		} else {
			Tecnico tecnico = user.getTecnico();
			if (!tecnico.getNome().isEmpty()) {
				System.out.println(tecnico.getId());
				if (tecnico.getId() > 0)
					daoTec.Update(tecnico);
				else
					daoTec.Insert(tecnico);
				usuario.setTecnico(tecnico);
			}
			usuario.setSenha(user.getSenha());
			usuario.setUsuario(user.getUsuario());
			usuario.setTipoUsuario(user.getTipoUsuario().toString());

			dao.Update(usuario);
			return "redirect:/usuario";
		}
	}

	@GetMapping("/excluir")
	public ModelAndView Excluir(@RequestParam(value = "id", required = true) int id, HttpSession session)
			throws ClassNotFoundException, SQLException {
		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		TecnicoDAO daoTec = new TecnicoDAO();

		UsuarioDAO dao = new UsuarioDAO();
		
		Usuario usuario=dao.Find(id);
		Tecnico tecnico=daoTec.Find(usuario.getTecnico().getId());
		
		dao.Remove(usuario);
		daoTec.Remove(tecnico);
		
		
		return new ModelAndView("redirect:/usuario");
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.removeAttribute("usuario");
		return "redirect:/";
	}
}
