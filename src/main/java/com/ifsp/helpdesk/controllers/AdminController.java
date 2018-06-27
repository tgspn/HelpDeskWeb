package com.ifsp.helpdesk.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ifsp.helpdesk.Util.Util;
import com.ifsp.helpdesk.dao.ChamadoDAO;
import com.ifsp.helpdesk.model.ChamadoViewModel;
import com.ifsp.helpdesk.model.TecnicoListViewModel;
import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.model.entities.Usuario;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("")
	public ModelAndView Index(Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		
		ModelAndView mav = new ModelAndView("homeAdmin");
		ChamadoDAO chamado = new ChamadoDAO();

		TecnicoListViewModel modelo = new TecnicoListViewModel();
		modelo.ParseFila(chamado.List());
		modelo.ParseMeusChamados(chamado.ListForTecnico(user.getTecnico().getId()));
		mav.addObject("model", modelo);
		mav.addObject("usuario",user.getUsuario());
		return mav;
	}
	
	@GetMapping("/criar")	
	public ModelAndView Novo( Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		
		ModelAndView mav = new ModelAndView("requisicoes");
		ChamadoDAO chamado = new ChamadoDAO();
		mav.addObject("model",new Chamado());
		mav.addObject("usuario",user.getUsuario());
		return mav;
	}
	
	@PostMapping("/criar")	
	public ModelAndView Novo(ChamadoViewModel chamado, Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		
		ModelAndView mav = new ModelAndView("redirect:/");
		ChamadoDAO dao = new ChamadoDAO();

		chamado.setStatus("Aberto");
		chamado.setTecnico(user.getTecnico());
		dao.Insert(chamado.ToChamado());
		//mav.addObject("usuario",user.getUsuario());
		return mav;
	}
	
	@GetMapping("/editar")	
	public ModelAndView editar(@RequestParam(value = "id", required = true)int id, Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		
		ModelAndView mav = new ModelAndView("requisicoes");
		ChamadoDAO dao = new ChamadoDAO();
		mav.addObject("model",dao.Find(id));
		mav.addObject("action","editar");
		mav.addObject("usuario",user.getUsuario());
		return mav;
	}
	
	@PostMapping("/editar")	
	public ModelAndView editar( @RequestParam(name = "id", required = true) int id, ChamadoViewModel chamado, Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("redirect:/");
		
		ModelAndView mav = new ModelAndView("redirect:/");
		ChamadoDAO dao = new ChamadoDAO();

		chamado.setStatus("Aberto");
		chamado.setTecnico(user.getTecnico());
		dao.Update(chamado.ToChamado());
		//mav.addObject("usuario",user.getUsuario());
		return mav;
	}
}
