package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		log.info("Inició método buscar categorias");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			response.getCategoriaResponse().setCategoria(categoria);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al consultar categorias");
			log.error("error al consultar las categorias ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		log.info("Inicio de método buscar por Id");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			if(categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.error("Error en consultar categoria");
				response.setMetadata("Respuesta no ok","-1", "categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("error al consultar las categorias");
			response.setMetadata("Respuesta no ok","-1", "categoria no encontrada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		log.info("Iniciando método Post crear categoria");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			
			Categoria categoriaGuardada = categoriaDao.save(categoria);
			
			if(categoriaGuardada != null) {
				list.add(categoria);
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.error("Error en añadir categoria");
				response.setMetadata("Respuesta no ok","-1", "categoria no guardada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			log.error("error al grabar las categorias");
			response.setMetadata("Respuesta no ok","-1", "categoria no grabada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		response.setMetadata("Respuesta ok", "00", "Respuesta creada");
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		log.info("Inicio método actualizar");
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<Categoria>();
		
		try {
			
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if(categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());//actualizado
				
				if (categoriaActualizar != null) {
					response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
					list.add(categoriaActualizar);
					response.getCategoriaResponse().setCategoria(list);
				}else {
					log.error("Error en actualizar categoria");
					response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);
				}
				
			}else {
				log.error("Error en actualizar categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error en actualizar categoria " + e.getMessage());
			response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
		
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inciando método eliminar");
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Categoria eliminada");
		} catch (Exception e) { 
			log.error("Error en eliminar categoria");
			response.setMetadata("Respuesta nok", "-1", "Categoria no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
	}

	
	
}
