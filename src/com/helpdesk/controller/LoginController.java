package com.helpdesk.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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

@WebServlet("/login")
public class LoginController extends HttpServlet{
	UsuarioDAO dao;

	public LoginController() {
			try {
				this.dao = new UsuarioDAO();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
				throws ServletException, IOException {

		Usuario user;
		try {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String url="/login.jsp";
			user = this.dao.Login(login, senha);
			if (user != null) {
				Configuracao.getCurrent().setTecnico(user.getTecnico());
				
				if (user.getTipoUsuario().equals( TecnicoCategoria.ADMINISTRADOR.toString()))
					url="/admin/home.jsp";
				else if (user.getTipoUsuario().equals( TecnicoCategoria.TECNICO.toString()))
					url="/tecnico/home.jsp";
				else {
					request.setAttribute("mensagem", "Usuário ou senha incorreto");
				}

			} else {
				request.setAttribute("mensagem", "Usuário ou senha incorreto");
			}
			getServletContext().
			getRequestDispatcher(url).
				forward(request, response);

		} catch (NoSuchAlgorithmException | SQLException e) {
			request.setAttribute("mensagem", "Usuário ou senha incorreto");
		}

	}
}
