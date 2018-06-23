package com.ifsp.helpdesk.model;

import com.ifsp.helpdesk.Util.TecnicoType;

public class ChamadoViewModel {
	private int id;
	private String categoria;
	private String assunto;
	private String descricao;
	private String status;
	
	public ChamadoViewModel(int id, String categoria, String assunto, String descricao, String status) {
		super();
		this.id = id;
		this.categoria = categoria;
		this.assunto = assunto;
		this.descricao = descricao;
		this.status = status;
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
}
