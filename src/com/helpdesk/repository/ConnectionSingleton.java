package com.helpdesk.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.sqlite.JDBC;
public class ConnectionSingleton {
	
	private static ConnectionSingleton instance;
	private Connection connection;

	private ConnectionSingleton() throws ClassNotFoundException {


		
		try {
			Class.forName("org.sqlite.JDBC");
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projetos\\Java\\DW2\\HelpDesk\\sample.db");			
		}

		catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
	}
	
	public Connection getConnection() {
		
		return connection;
		
	}
	public void Close() {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) { // Use SQLException class instead.
			System.err.println(e);
		}
	}
	public static ConnectionSingleton getInstance() throws ClassNotFoundException {
		if(instance==null)
			instance=new ConnectionSingleton();
		
		return instance;
	}
}
