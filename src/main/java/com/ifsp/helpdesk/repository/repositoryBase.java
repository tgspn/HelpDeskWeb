package com.ifsp.helpdesk.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class repositoryBase<T> {

	private String table;
	protected ConnectionSingleton conexaoInstance;
	private Connection connection;
	private String defaultJoin;
	private String defaultParams;

	public repositoryBase() throws ClassNotFoundException {

		conexaoInstance = ConnectionSingleton.getInstance();
		connection = conexaoInstance.getConnection();
		table = defineTableName();
		defaultJoin = defineDefaultJoin();
		defaultParams = defineDefaultParams();
		CheckTable();
	}

	private void CheckTable() {
		try {
			select("*", "", "limit 1");
		} catch (SQLException e) {
			try {
				CreateTable();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private Statement createStatement() throws SQLException {
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);
		return statement;
	}

	public void CreateTable() throws SQLException {
		Statement statement = createStatement();
		statement.executeUpdate("DROP TABLE IF EXISTS " + table);

		Map<String, SQLiteTypes> fields = defineFields();

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(table);
		sb.append("(");
		for (Entry<String, SQLiteTypes> entry : fields.entrySet()) {
			if (!sb.toString().endsWith("("))
				sb.append(",");

			sb.append(entry.getKey());
			sb.append(" ");
			sb.append(entry.getValue());

		}
		sb.append(")");

		statement.executeUpdate(sb.toString());
	}

	protected ArrayList<T> select() throws SQLException {
		return select(defaultParams);
	}

	protected ArrayList<T> select(String params) throws SQLException {
		return select(params, "");
	}

	protected ArrayList<T> select(String params, String where) throws SQLException {
		return select(params, where, "");
	}

	protected ArrayList<T> select(String params, String where, String extra) throws SQLException {
		return select(params, defaultJoin, where, extra);
	}

	protected ArrayList<T> select(String params, String joins, String where, String extra) throws SQLException {
		Statement statement = createStatement();

		if (params.isEmpty() && !defaultParams.isEmpty())
			params = defaultParams;
		else
			params = "*";

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(params);
		sb.append(" FROM ");
		sb.append(table);
		if (!joins.isEmpty()) {
			sb.append(" ");
			sb.append(joins);
			sb.append(" ");
		}
		if (!where.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(where);
		}
		if (!extra.isEmpty()) {
			sb.append(" ");
			sb.append(extra);
		}

		System.out.println(sb.toString());

		ResultSet result = statement.executeQuery(sb.toString());
		ArrayList<T> list = new ArrayList();
		while (result.next()) {
			T obj = fillObject(result);
			list.add(obj);
		}
		return list;
	}

	public int Insert(T obj) throws SQLException {
		Statement statement = createStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" ( ");
		sb.append(String.join(",", getFieldsInsert()));
		sb.append(" ) ");
		sb.append(" VALUES( ");
		sb.append(InsertRefect(getValueInsert(obj)));
		sb.append(" ) ");

		if (statement.executeUpdate(sb.toString()) == 1) {

			int lastId = LastInsertedId();
			setPrimaryKeyValue(obj, lastId);

			return lastId;

		}

		throw new SQLException("Falha no insert");

	}

	private int LastInsertedId() throws SQLException {

		Statement statement = createStatement();

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		} else {
			throw new SQLException("Falha no insert");
		}

	}

	public int Update(T obj) throws SQLException {
		StringBuilder sb = new StringBuilder();

		sb.append(getPrimaryKeyField());
		sb.append(" = ");
		sb.append(getPrimaryKeyValue(obj));

		return Update(obj, sb.toString());
	}

	public int Update(T obj, String where) throws SQLException {
		Statement statement = createStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(table);
		sb.append(" SET ");

		Map<String, Object> map = getUpdateValues(obj);

		for (Entry<String, Object> e : map.entrySet()) {
			if (!sb.toString().endsWith(" SET "))
				sb.append(",");

			sb.append(e.getKey());
			sb.append(" = ");
			if (e.getValue() instanceof String) {
				sb.append("'");
				sb.append(e.getValue());
				sb.append("'");
			} else {
				sb.append(e.getValue());
			}
		}

		if (!where.isEmpty()) {
			sb.append(" WHERE ");
			sb.append(where);
		}

		return statement.executeUpdate(sb.toString());

	}

	public void Delete(int id) throws SQLException {
		Statement st = createStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(getPrimaryKeyField());
		sb.append(" = ");
		sb.append(id);

		st.executeUpdate(sb.toString());

	}

	private String InsertRefect(Object[] obj) {
		StringBuilder sb = new StringBuilder();

		for (Object o : obj) {
			if (sb.length() > 0)
				sb.append(",");
			if (o instanceof String) {
				sb.append("'");
				sb.append(o);
				sb.append("'");
			} else {
				sb.append(o);
			}
		}

		return sb.toString();
	}

	public ArrayList<T> findAll() throws SQLException {
		return select();
	}

	public T find(int id) throws SQLException {
		ArrayList<T> result= select("",String.format(" {0} = {1} ", getPrimaryKeyField(), id));
		if(result.isEmpty())
			return null;
		return result.get(0);
	}

	protected abstract String[] getFieldsInsert();

	protected abstract String getPrimaryKeyField();

	protected abstract int getPrimaryKeyValue(T obj);

	protected abstract void setPrimaryKeyValue(T obj, int value);

	protected abstract Object[] getValueInsert(T obj);

	protected abstract Map<String, Object> getUpdateValues(T obj);

	protected abstract T fillObject(ResultSet result) throws SQLException;

	protected abstract Map<String, SQLiteTypes> defineFields();

	protected abstract String defineTableName();

	protected abstract String defineDefaultJoin();

	protected abstract String defineDefaultParams();

}
