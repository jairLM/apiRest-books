package com.company.books.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

public class CategoriaServiceImplTest {

	@InjectMocks
	CategoriaServiceImpl service;
	
	@Mock
	ICategoriaDao categoriaDao;
	List<Categoria> list = new ArrayList<Categoria>();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);//uso de anotacion de mockito
		this.cargarCategorias();
	}
	
	@Test
	public void buscarCategoriaTest() {
		
		when(categoriaDao.findAll()).thenReturn(list);
		
		ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
		
		assertEquals( 4 , response.getBody().getCategoriaResponse().getCategoria().size());
		
		verify(categoriaDao, times(1)).findAll();//verificar que solo se llame una vez el findall
		
	}
	
	public void cargarCategorias() {
		
		Categoria categoria1 = new Categoria(Long.valueOf(1), "Abarrotes", "Distintos abarrotes");
		Categoria categoria2 = new Categoria(Long.valueOf(2), "Lacteos", "Distintos lacteos");
		Categoria categoria3 = new Categoria(Long.valueOf(3), "Bebidas", "Bebidas sin azucar");
		Categoria categoria4 = new Categoria(Long.valueOf(4), "Carnes blancas", "Distintas carnes");
		
		list.add(categoria1);
		list.add(categoria2);
		list.add(categoria3);
		list.add(categoria4);
		
	}
	
	
	
	
	
}
