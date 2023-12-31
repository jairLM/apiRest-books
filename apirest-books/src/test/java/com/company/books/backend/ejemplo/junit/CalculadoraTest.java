package com.company.books.backend.ejemplo.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	Calculadora calc;
	
	@BeforeAll
	public static void primero() {
		System.out.println("Primero");
	}
	
	@AfterAll
	public static void ultimo() {
		System.out.println("Ultimo");
	}
	
	@BeforeEach
	public void instanciaObjeto() {
		calc = new Calculadora();
		System.out.println("beforeEach");
	}
	@AfterEach
	public void despuesTest() {
		
		System.out.println("AfterEach");
	}
	
	
	@Test
	@DisplayName("prueba que ocupa assert equal")
	@Disabled("Esta prueba no se ejecuta") 
	public void calculadoraAssertEqualTest() {
		
		
		assertEquals(2, calc.sumar(1, 1));
		assertEquals(3, calc.restar(4, 1));
		assertEquals(25, calc.multiplicar(5, 5));
		assertEquals(10, calc.dividir(100, 10));
		System.out.println("assert equal test");
	}
	
	@Test
	public void calculadoraTrueFalse() {
		
		assertTrue(calc.sumar(1, 1) == 2);
		assertTrue(calc.restar(4, 1) == 3);
		assertFalse(calc.multiplicar(5, 5) == 30);
		assertFalse(calc.dividir(4, 2) == 1);
		System.out.println("assert true/false test");
	}
	
	
}
