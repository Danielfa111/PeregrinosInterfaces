package com.danielfa11.tarea3AD2024.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.repositorios.ParadaRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadaRepository paradaRepository;
	
	public Parada save(Parada entity) {
		return paradaRepository.save(entity);
	}

	public Parada update(Parada entity) {
		return paradaRepository.save(entity);
	}

	public void delete(Parada entity) {
		paradaRepository.delete(entity);
	}

	public void delete(Long id) {
		paradaRepository.deleteById(id);
	}

	public Parada find(Long id) {
		return paradaRepository.findById(id).get();
	}

	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}
	
	public List<Parada> findByPeregrinos(List<Peregrino> peregrinos){
		return paradaRepository.findByPeregrinos(peregrinos);
	}
	
	public Parada findByNombreAndRegion(String nombre, char region) {
		return paradaRepository.findByNombreAndRegion(nombre, region);
	}
	
	public Parada getReferenceByNombreAndRegion(String nombre, char region) {
		return paradaRepository.getReferenceByNombreAndRegion(nombre, region);
	}
	
	public Parada findTopByOrderByIdDesc() {
		return paradaRepository.findTopByOrderByIdDesc();
	}
	
	public List<String> findNombreBy(){
		return paradaRepository.findNombreBy();
	}
	
	public void deleteInBatch(List<Parada> paradas) {
		paradaRepository.deleteAll(paradas);
	}
}

