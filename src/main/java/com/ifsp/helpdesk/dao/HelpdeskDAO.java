package com.ifsp.helpdesk.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HelpdeskDAO<T> {

	public T Insert(T inp) throws SQLException;

	public void Remove(T inp) throws SQLException;

	public void Update(T inp) throws SQLException;

	public ArrayList<T> List() throws SQLException;
	public T Find(int id) throws SQLException;
	

}
