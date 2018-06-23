package com.ifsp.helpdesk.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ifsp.helpdesk.Util.TecnicoType;
import com.ifsp.helpdesk.dao.ChamadoDAO;
import com.ifsp.helpdesk.model.ChamadoViewModel;
import com.ifsp.helpdesk.model.TecnicoListViewModel;

@Controller
@RequestMapping("/tecnico")
public class TecnicoController {

	@GetMapping("")
	public ModelAndView Index(Model model) throws ClassNotFoundException, SQLException, IOException {
		
		ModelAndView mav = new ModelAndView("chamadosTecnico");
		ChamadoDAO chamado=new ChamadoDAO();
		
		TecnicoListViewModel modelo=new TecnicoListViewModel();
		modelo.ParseFila(chamado.List());
		modelo.ParseMeusChamados(chamado.ListForTecnico(1));
        mav.addObject("model", modelo);
        return mav;
	}
}
