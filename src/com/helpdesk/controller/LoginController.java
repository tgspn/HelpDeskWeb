package com.helpdesk.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.helpdesk.Util.Configuracao;
import com.helpdesk.Util.TecnicoCategoria;
import com.helpdesk.Util.Util;
import com.helpdesk.dao.UsuarioDAO;
import com.helpdesk.model.Usuario;

@WebServlet(name="/login",urlPatterns = "/")
public class LoginController extends HttpServlet {
	UsuarioDAO dao;

	public LoginController() {
		
	}

//	private void processRequest(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/Login.jsp");
//		dispatcher.forward(request, response);
//	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	//	processRequest(request, response);
		getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario user;
		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String url = "/login";
			this.dao = new UsuarioDAO();
			user = this.dao.Login(login, senha);
			if (user != null) {
				Configuracao.getCurrent().setTecnico(user.getTecnico());

				if (user.getTipoUsuario().equals(TecnicoCategoria.ADMINISTRADOR.toString()))
					url = "admin/home";
				else if (user.getTipoUsuario().equals(TecnicoCategoria.TECNICO.toString()))
					url = "/tecnico/";
				else {
					request.setAttribute("mensagem", "Usuário ou senha incorreto");
				}

			} else {
				request.setAttribute("mensagem", "Usuário ou senha incorreto");
			}
			response.sendRedirect(url);
			getServletContext().getRequestDispatcher(url).forward(request, response);

		} catch (NoSuchAlgorithmException | SQLException e) {
			request.setAttribute("mensagem", "Usuário ou senha incorreto");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
