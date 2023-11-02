package com.company.ejemplo1;

public class Guitarra implements InstrumentoMusical {
	
	private Afinar afinar;
	
	public Guitarra(Afinar afinar) {
		this.afinar = afinar;
	}

	@Override
	public String getSonido() {
		return "Sonido de guitarra";
	}

	@Override
	public String getAfinacion() {
		
		return "Guitarra - " + afinar.afinacion();
	}
	
	 
	
	
}
