package com.ifsp.helpdesk.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import com.ifsp.helpdesk.model.entities.Cliente;

public class ClienteDAO implements HelpdeskDAO<Cliente> {

	private static ArrayList<Cliente> list;

	public ClienteDAO() {
		if (list == null)
			list = new ArrayList<>();
	}

	@Override
	public Cliente Insert(Cliente client) {
		int lastIndex = 0;
		if (list.stream().count() > 0) {
			lastIndex = list.stream().max(Comparator.comparingInt(x -> x.getId())).get().getId();
		}
		client.setId(lastIndex);
		list.add(client);
		return client;
	}

	@Override
	public void Remove(Cliente client) {
		list.remove(client);
	}

	@Override
	public void Update(Cliente client) {
		Cliente clientOriginal = list.stream().filter(x -> x.getId() == client.getId()).findFirst().get();
		try {
			client.CopyTo(clientOriginal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public ArrayList<Cliente> List() {
		return list;
	}

	@Override
	public Cliente Find(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
