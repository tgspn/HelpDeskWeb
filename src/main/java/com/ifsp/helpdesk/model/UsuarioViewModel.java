package com.ifsp.helpdesk.model;

import com.ifsp.helpdesk.Util.TecnicoCategoria;
import com.ifsp.helpdesk.model.entities.Tecnico;
import com.ifsp.helpdesk.model.entities.Usuario;

public class UsuarioViewModel {
	private Integer id;
	private String usuario;
	private String senha;
	private Integer tecnico_id;
	private Tecnico tecnico = new Tecnico();
	private TecnicoCategoria tipoUsuario;

	public UsuarioViewModel() {
	}

	public UsuarioViewModel(Usuario usuario) {
		this.id = usuario.getId();
		this.usuario = usuario.getUsuario();
		this.senha = usuario.getSenha();
		if (usuario.getTecnico() != null) {
			this.tecnico_id = usuario.getTecnico().getId();
			this.tecnico = usuario.getTecnico();
		}
		System.out.println(usuario.getTipoUsuario());
		this.tipoUsuario = TecnicoCategoria.valueOf(usuario.getTipoUsuario().toUpperCase());
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TecnicoCategoria getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TecnicoCategoria tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getTecnico_id() {
		return tecnico_id;
	}

	public void setTecnico_id(Integer tecnico_id) {
		this.tecnico_id = tecnico_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
}
