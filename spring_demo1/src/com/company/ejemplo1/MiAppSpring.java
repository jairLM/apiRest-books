package com.company.ejemplo1;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MiAppSpring {

	public static void main(String[] args) {
		//Carcar configuracion de spring desde un archivo xml
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("applicationContext.xml");
		InstrumentoMusical instrumento = context.getBean("miInstrumento",InstrumentoMusical.class);
		
		System.out.println(instrumento.getSonido());
		System.out.println(instrumento.getAfinacion());
		
		
		//violin
		Violin violin = context.getBean("miViolin", Violin.class);
		
		System.out.println(violin.getSonido());
		System.out.println(violin.getAfinacion());
		
		context.close();//cerramos el contexto de spring

	}

}
