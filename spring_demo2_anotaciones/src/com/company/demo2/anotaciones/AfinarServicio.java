package com.company.demo2.anotaciones;

import org.springframework.stereotype.Component;

@Component
public class AfinarServicio implements Afinar {

	@Override
	public String afinacion() {
		return "Obteniendo afinacion del instrumento";
	}

}
