package com.company.ejemplo1;

public class Piano implements InstrumentoMusical {

	private Afinar afinar;
	public Piano(Afinar afinar) {
		this.afinar = afinar;
	}

	public Piano() {
		super();
	}



	@Override
	public String getSonido() {
		return "Sonido de piano";
	}

	@Override
	public String getAfinacion() {
		
		return "Piano - " + afinar.afinacion();
	}
	
	
	
}
