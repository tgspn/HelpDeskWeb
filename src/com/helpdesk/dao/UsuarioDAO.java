package com.helpdesk.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.helpdesk.model.Tecnico;
import com.helpdesk.model.Usuario;
import com.helpdesk.repository.UsuarioRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioDAO implements HelpdeskDAO<Usuario> {

	private UsuarioRepository repository;

	public UsuarioDAO() throws ClassNotFoundException {
		repository = new UsuarioRepository();
		
	}
	@Override
	public Usuario Insert(Usuario inp) throws SQLException {
		repository.Insert(inp);
		
		return inp;
	}

	@Override
	public void Remove(Usuario inp) throws SQLException {
		repository.Delete(inp.getId());
		
	}

	@Override
	public void Update(Usuario inp) throws SQLException {
		repository.Update(inp);
		
	}

	@Override
	public ArrayList<Usuario> List() throws SQLException {
		ArrayList<Usuario> list=new ArrayList<>();
		list.addAll(repository.findAll());
		return list;
	}
	public Usuario Login(String user,String senha) throws NoSuchAlgorithmException, SQLException {
		return repository.Login(user, senha);
				
	}
	public Usuario findByTecnico(Tecnico tecnico) throws SQLException {
		
		return repository.findByTecnicoId(tecnico.getId());
	}
	@Override
	public Usuario Find(int id) throws SQLException {
		return repository.find(id);
	}

	

}
