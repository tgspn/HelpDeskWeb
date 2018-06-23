package com.ifsp.helpdesk.model.entities;

public class Funcao {
private int id;
private String nome;
private String posicao;
public Funcao(int id, String nome, String posicao) {
	this.id = id;
	this.nome = nome;
	this.posicao = posicao;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getPosicao() {
	return posicao;
}
public void setPosicao(String posicao) {
	this.posicao = posicao;
}

}