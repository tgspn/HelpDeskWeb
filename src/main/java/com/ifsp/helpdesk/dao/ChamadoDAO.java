package com.ifsp.helpdesk.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ifsp.helpdesk.Util.SituacaoType;
import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.repository.ChamadoRepository;

public class ChamadoDAO implements HelpdeskDAO<Chamado> {

	private ChamadoRepository repository;
	private ArrayList<Chamado> list;

	public ChamadoDAO() throws ClassNotFoundException {
		repository = new ChamadoRepository();
		if (list == null)
			list = new ArrayList<>();
	}

	@Override
	public Chamado Insert(Chamado model) throws SQLException {

		int result = repository.Insert(model);
		List();
		return model;
	}

	@Override
	public void Remove(Chamado model) throws SQLException {

		repository.Delete(model.getId());

		list.remove(model);
	}

	@Override
	public void Update(Chamado model) throws SQLException {

		repository.Update(model);
	}
	
	public ArrayList<Chamado>ListBySituacao(SituacaoType situacao) throws SQLException{
		ArrayList<Chamado> select = repository.findAllForSituacao(situacao.toString());
		
		fillList(select);
		
		return list;
	}

	public ArrayList<Chamado> ListBySituacaoAndCategoria(SituacaoType situacao, String categoria)
			throws SQLException {
		if(categoria.isEmpty())
			return ListBySituacao(situacao);
		
		List<Chamado> select = repository.findAllBySituacaoAndCategoria(situacao.toString(),categoria);
		
		fillList(select);
		
		return list;
	}
	@Override
	public ArrayList<Chamado> List() throws SQLException {
		ArrayList<Chamado> select = repository.findAll();
		fillList(select);
		return list;
	}
	public ArrayList<Chamado> ListForTecnico(int id) throws SQLException {
		ArrayList<Chamado> select = repository.findAllForTecnico(id);
		fillList(select);
		return list;
	}
	@Override
	public Chamado Find(int id) throws SQLException {
		return repository.find(id);
	}
	private void fillList(java.util.List<Chamado> select) {
		for (Chamado c : select) {
			boolean flag=false;
			for (Chamado ch : list ) {
				if (c.getId() == ch.getId()) {
					try {
						c.CopyTo(ch);
						flag=true;
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(!flag)
				list.add(c);
		}
		Chamado[] arr=list.toArray(new Chamado[0]);
		for (Chamado c : arr) {
			boolean flag=false;
			for (Chamado ch : select ) {
				if (c.getId() == ch.getId()) {
					try {
						c.CopyTo(ch);
						flag=true;
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(!flag)
				list.remove(c);
		}
		
	}

	

	

	

}
