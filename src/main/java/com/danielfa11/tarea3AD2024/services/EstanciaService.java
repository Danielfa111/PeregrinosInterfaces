package com.danielfa11.tarea3AD2024.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielfa11.tarea3AD2024.modelo.Estancia;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.repositorios.EstanciaRepository;


@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	
	public Estancia save(Estancia entity) {
		return estanciaRepository.save(entity);
	}

	public Estancia update(Estancia entity) {
		return estanciaRepository.save(entity);
	}

	public void delete(Estancia entity) {
		estanciaRepository.delete(entity);
	}

	public void delete(Long id) {
		estanciaRepository.deleteById(id);		
	}
	
	public List<Estancia> findByPeregrino(Peregrino peregrino){
		return estanciaRepository.findByPeregrino(peregrino);
	}
	
	public List<Estancia> findByFechaBetweenAndParada(LocalDate fecha1, LocalDate fecha2, Parada Parada){
		return estanciaRepository.findByFechaBetweenAndParada(fecha1, fecha2, Parada);
	}

}
