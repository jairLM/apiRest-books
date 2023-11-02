package com.company.ejemplo1;

public class Violin implements InstrumentoMusical {
		
	private Afinar afinar;
		
	public Violin() {
		super();
	}	
	
	//dependecia por setter
	public void setAfinar(Afinar afinar) {
		this.afinar = afinar;
	}

	@Override
	public String getSonido() {
		return "Obteniendo el sonido del violín";
	}

	@Override
	public String getAfinacion() {
		return "Violín - " + afinar.afinacion();
	}

}
