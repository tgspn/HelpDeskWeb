package com.ifsp.helpdesk.model;

import java.util.ArrayList;

import com.ifsp.helpdesk.model.entities.Chamado;

public class TecnicoListViewModel {
	private ArrayList<ChamadoViewModel> meusChamados;
	private ArrayList<ChamadoViewModel> fila;
	
	public ArrayList<ChamadoViewModel> getMeusChamados() {
		return meusChamados;
	}
	public void setMeusChamados(ArrayList<ChamadoViewModel> meusChamados) {
		this.meusChamados = meusChamados;
	}
	public ArrayList<ChamadoViewModel> getFila() {
		return fila;
	}
	public void setFila(ArrayList<ChamadoViewModel> fila) {
		this.fila = fila;
	}
	public void ParseMeusChamados(ArrayList<Chamado> listForTecnico) {
		setMeusChamados(ParseChamado(listForTecnico));
		
	}
	public void ParseFila(ArrayList<Chamado> list) {
		setFila(ParseChamado(list));
		
	}
	
	private ArrayList<ChamadoViewModel> ParseChamado(ArrayList<Chamado> list){
		meusChamados=new ArrayList<>();
		for(Chamado chamado:list) {
			meusChamados.add(new ChamadoViewModel(chamado.getId(),chamado.getCategoria(),chamado.getAssunto(),chamado.getDescricao(),chamado.getSituacao()));
		}
		
		return meusChamados;
	}
	
	
}
