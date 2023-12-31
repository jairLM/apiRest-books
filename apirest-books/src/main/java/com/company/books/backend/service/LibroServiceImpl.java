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


import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {

	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class); 
	
	@Autowired
	private ILibroDao libroDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		
		log.info("Iniciando método buscar libros");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta no ok", "-1", "Error al consultar categorias");
			log.error("error al consultar las categorias ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
	}


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
		log.info("Iniciando método buscar por id");
		List<Libro> list = new ArrayList<>();
		LibroResponseRest response = new LibroResponseRest();
		try {
			
			Optional<Libro> libro = libroDao.findById(id);
			if(libro.isPresent()) {
				list.add(libro.get());
				response.getLibroResponse().setLibro(list);
				
			}else {
				log.error("Error en consultar libro por id");
				response.setMetadata("Respuesta no ok","-1", "libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("error al consultar el libro " + e.getMessage());
			response.setMetadata("Respuesta no ok","-1", "libro no encontrada");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
	}


	@Override
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Iniciando método crear libro");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<Libro>();
		
		try {
			
			Libro libroGuardado = libroDao.save(libro);
			if(libroGuardado != null) {
				list.add(libro);
				response.getLibroResponse().setLibro(list);
			}else {
				log.info("Error en añadir libro");
				response.setMetadata("Respuesta no ok", "-1", "libro no guardado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (Exception e) {	
			
			log.error("Error al grabar libro " + e.getMessage());
			response.setMetadata("Respuesta no ok", "-1", "Libro no creado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		response.setMetadata("Respuesta ok", "00", "Libro creado");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}


	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
		log.info("Iniciando método actualizar");
		List<Libro> list = new ArrayList<Libro>();
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			Optional<Libro> libroEncontrado = libroDao.findById(id);
			
			if(libroEncontrado.isPresent()) {
				libroEncontrado.get().setTitulo(libro.getTitulo());
				libroEncontrado.get().setDescripcion(libro.getDescripcion());
				libroEncontrado.get().setCategoria(libro.getCategoria());
				
				Libro libroActulizar = libroDao.save(libroEncontrado.get());
				
				if(libroActulizar  != null) {
					response.setMetadata("Respuesta ok", "00", "Libro actualizado");
					list.add(libroActulizar);
					response.getLibroResponse().setLibro(list);
				}else {
					
					log.error("Error en actualizar libro");
					response.setMetadata("Respuesta nok", "-1", "Libro no actualizad");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
					
				}
			}else {
				log.error("Error en actualizar libro");
				response.setMetadata("Respuesta nok", "-1", "Libro no actualizado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			log.error("Error en actualizar libro " + e.getMessage());
			response.setMetadata("Respuesta nok", "-1", "Libro no actualizado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
	}


	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminar(Long id) {
		
		log.info("Iniciando método eliminar por id");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			Optional<Libro> libroEncontrado = libroDao.findById(id);
			
			if(libroEncontrado.isPresent()) {
				libroDao.delete(libroEncontrado.get());
				
			}else {
				log.error("Error en borra el libro");
				response.setMetadata("Respuesta nok", "-1", "Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			log.error("Error en borrar libro " + e.getMessage());
			response.setMetadata("Respuesta nok", "-1", "Libro no borrado");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
	}
	
}
