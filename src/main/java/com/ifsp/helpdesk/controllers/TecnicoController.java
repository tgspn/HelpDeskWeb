package com.ifsp.helpdesk.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ifsp.helpdesk.Util.SituacaoType;
import com.ifsp.helpdesk.Util.Util;
import com.ifsp.helpdesk.dao.ChamadoDAO;
import com.ifsp.helpdesk.model.TecnicoListViewModel;
import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.model.entities.Usuario;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {

	@GetMapping("")
	public ModelAndView Index(Model model, HttpSession session) throws ClassNotFoundException, SQLException, IOException {

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return new ModelAndView("../index");
		
		ModelAndView mav = new ModelAndView("chamadosTecnico");
		ChamadoDAO chamado = new ChamadoDAO();

		TecnicoListViewModel modelo = new TecnicoListViewModel();
		modelo.ParseFila(chamado.ListBySituacao(SituacaoType.ABERTO));
		modelo.ParseMeusChamados(chamado.ListForTecnico(user.getTecnico().getId()));
		mav.addObject("model", modelo);
		mav.addObject("usuario",user.getUsuario());
		return mav;
	}

	@GetMapping("/bloquear")
	public String Bloquear(@RequestParam(name = "id", required = true) int id, HttpSession session)
			throws SQLException, ClassNotFoundException {
		ChamadoDAO dao = new ChamadoDAO();
		Chamado model = dao.Find(id);

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return "redirect:/";

		model.setSituacao(SituacaoType.BLOQUEADO.toString());
		model.setTecnico(user.getTecnico());

		dao.Update(model);

		return "redirect:/tecnico";
	}
	
	@GetMapping("/desbloquear")
	public String Desbloquear(@RequestParam(name = "id", required = true) int id, HttpSession session)
			throws SQLException, ClassNotFoundException {
		ChamadoDAO dao = new ChamadoDAO();
		Chamado model = dao.Find(id);

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return "redirect:/";

		model.setSituacao(SituacaoType.ABERTO.toString());
		model.setTecnico(null);

		dao.Update(model);

		return "redirect:/tecnico";
	}
	
	@GetMapping("/finalizar")
	public String Finalizar(@RequestParam(name = "id", required = true) int id, HttpSession session)
			throws SQLException, ClassNotFoundException {
		ChamadoDAO dao = new ChamadoDAO();
		Chamado model = dao.Find(id);

		Usuario user = Util.getCurrentUser(session);
		if (user == null)
			return "redirect:/";

		model.setSituacao(SituacaoType.FINALIZADO.toString());
		model.setTecnico(null);

		dao.Update(model);

		return "redirect:/tecnico";
	}

	

}
