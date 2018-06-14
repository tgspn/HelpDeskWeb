package com.helpdesk.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.helpdesk.model.Tecnico;
import com.helpdesk.repository.TecnicoRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TecnicoDAO implements HelpdeskDAO<Tecnico> {

	private ArrayList<Tecnico> list;
	private TecnicoRepository repository;

	public TecnicoDAO() throws ClassNotFoundException {
		this.repository = new TecnicoRepository();
		list = new ArrayList<Tecnico>();
	}

	@Override
	public Tecnico Insert(Tecnico model) throws SQLException {
		repository.Insert(model);
		list.add(model);
		return model;
	}

	@Override
	public void Remove(Tecnico model) throws SQLException {
		repository.Delete(model.getId());
		list.remove(model);
	}

	@Override
	public void Update(Tecnico model) throws SQLException {
		repository.Update(model);

	}

	@Override
	public ArrayList<Tecnico> List() throws SQLException {
		list.addAll(repository.findAll());
		return list;
	}

	@Override
	public Tecnico Find(int id) throws SQLException {
		return repository.find(id);
	}

}
