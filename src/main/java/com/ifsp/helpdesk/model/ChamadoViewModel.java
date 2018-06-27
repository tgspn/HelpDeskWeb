package com.ifsp.helpdesk.model;

import com.ifsp.helpdesk.Util.TecnicoType;
import com.ifsp.helpdesk.model.entities.Chamado;
import com.ifsp.helpdesk.model.entities.Tecnico;

public class ChamadoViewModel {
	private Integer id;
	private String categoria;
	private String assunto;
	private String descricao;
	private String status;
	private String notas;
	private Tecnico tecnico;
	
	public ChamadoViewModel(Integer id, String categoria, String assunto, String descricao, String status,String notas,Tecnico tecnico) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.assunto = assunto;
		this.descricao = descricao;
		this.status = status;
		this.notas=notas;
		this.setTecnico(tecnico);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public Chamado ToChamado() {
		return new Chamado(id, descricao, categoria, assunto, status, 1, tecnico, notas);
		
	}
}
