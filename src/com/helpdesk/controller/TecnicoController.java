package com.helpdesk.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

import com.helpdesk.Util.TecnicoType;
import com.helpdesk.dao.TecnicoDAO;
import com.helpdesk.model.Tecnico;

@WebServlet(name="/tecnico",urlPatterns = "/")
public class TecnicoController extends HttpServlet {

	private TecnicoDAO dao;

	public TecnicoController() {
		try {
			dao = new TecnicoDAO();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		RequestDispatcher dispatcher = request.getRequestDispatcher("tecnico/home.jsp");
		dispatcher.forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String action = request.getParameter("action");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String telefone = request.getParameter("telefone");
		int id = Integer.parseInt(request.getParameter("id"));

		TecnicoType category = TecnicoType.HARDWARE;

		if (type.equals("software"))
			category = TecnicoType.SOFTWARE;
		else if (type.equals("redes"))
			category = TecnicoType.REDES;
		try {
			Tecnico model;
			if (action.equals("create")) {

				model = dao.Insert(new Tecnico(0, nome, category.toString(), email, telefone, 0));

			} else {
				model = dao.Find(id);
				model.setNome(nome);
				model.setEmail(email);
				model.setTelefone(telefone);
				model.setCategoria(category.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
