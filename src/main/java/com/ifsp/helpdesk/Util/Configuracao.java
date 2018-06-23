package com.ifsp.helpdesk.Util;

import com.ifsp.helpdesk.model.entities.*;

public class Configuracao {
	
	private static Configuracao current;
	private Tecnico tecnico;
	
	public static Configuracao getCurrent() {
		if(current==null)
			current=new Configuracao();
		
		return current;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
}
