package com.ifsp.helpdesk.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.model.entities.Tecnico;

public class ChamadoRepository extends repositoryBase<Chamado> {

	public ChamadoRepository() throws ClassNotFoundException {

	}

	public ArrayList<Chamado> findAllForTecnico(int idFuncionario) throws SQLException {
		return select("", "idTecnico=" + idFuncionario);
	}

	public ArrayList<Chamado> findAllForSituacao(String string) throws SQLException {
		return select("", "situacao='" + string + "'");
	}
	public List<Chamado> findAllBySituacaoAndCategoria(String string, String categoria) throws SQLException {
		// TODO Auto-generated method stub
		return select("", "situacao='" + string + "' AND chamado.categoria='"+categoria+"'");
	}

	@Override
	protected String[] getFieldsInsert() {

		List<String> list = new ArrayList<>();
		list.add("descricao");
		list.add("categoria");
		list.add("assunto");
		list.add("situacao");
		list.add("nota");
		list.add("idTecnico");
		return list.toArray(new String[0]);
	}

	@Override
	protected String getPrimaryKeyField() {
		return "chamado.id";
	}

	@Override
	protected void setPrimaryKeyValue(Chamado obj, int value) {
		obj.setId(value);
	}

	@Override
	protected int getPrimaryKeyValue(Chamado obj) {
		return obj.getId();
	}

	@Override
	protected Object[] getValueInsert(Chamado obj) {

		List<Object> list = new ArrayList<>();
		list.add(obj.getDescricao());
		list.add(obj.getCategoria());
		list.add(obj.getAssunto());
		list.add(obj.getSituacao());
		list.add(obj.getNota());
		list.add(obj.getTecnico().getId());
		return list.toArray();
	}

	@Override
	protected Map<String, Object> getUpdateValues(Chamado obj) {
		Map<String, Object> map = new HashMap();

		map.put("descricao", obj.getDescricao());
		map.put("categoria", obj.getCategoria());
		map.put("assunto", obj.getAssunto());
		map.put("situacao", obj.getSituacao());
		map.put("idCliente", obj.getIdCliente());
		map.put("nota", obj.getNota());
		map.put("idTecnico", obj.getTecnico() != null ? obj.getTecnico().getId() : 0);
		return map;
	}

	@Override
	protected Chamado fillObject(ResultSet result) throws SQLException {
		Tecnico tecnico = null;
		if (result.getString("idTecnico") != null)
			tecnico = Tecnico.FromResultSet(result);

		return new Chamado(result.getInt("id"), result.getString("descricao"), result.getString("categoria"),
				result.getString("assunto"), result.getString("situacao"), result.getInt("idCliente"), tecnico,result.getString("nota"));
	}

	@Override
	protected Map<String, SQLiteTypes> defineFields() {

		Map<String, SQLiteTypes> map = new HashMap<>();
		map.put("id", SQLiteTypes.INTERGER_NOT_NULL_PRIMARYKEY_AUTOINCREMENT);
		map.put("descricao", SQLiteTypes.STRING);
		map.put("categoria", SQLiteTypes.STRING);
		map.put("assunto", SQLiteTypes.STRING);
		map.put("situacao", SQLiteTypes.STRING);
		map.put("idCliente", SQLiteTypes.INTEGER);
		map.put("nota", SQLiteTypes.STRING);
		map.put("idTecnico", SQLiteTypes.INTEGER);
		return map;

	}

	@Override
	protected String defineTableName() {
		return "chamado";
	}

	@Override
	protected String defineDefaultJoin() {
		return "LEFT JOIN tecnico on tecnico.id=idTecnico";
	}

	@Override
	protected String defineDefaultParams() {

		return "chamado.id,descricao,chamado.categoria,assunto,situacao,idCliente,tecnico.id as idTecnico,nome,tecnico.categoria as usertype,email,telefone,idFuncao,nota";
	}

	public static void Initialize() {
		ChamadoRepository r;
		try {
			r = new ChamadoRepository();
			r.CreateTable();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
