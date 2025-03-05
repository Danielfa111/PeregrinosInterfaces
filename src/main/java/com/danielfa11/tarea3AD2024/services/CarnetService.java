package com.danielfa11.tarea3AD2024.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.repositorios.CarnetRepository;

@Service
public class CarnetService {

	@Autowired
	private CarnetRepository carnetRepository;
	
	public Carnet save(Carnet entity) {
		return carnetRepository.save(entity);
	}

	public Carnet update(Carnet entity) {
		return carnetRepository.save(entity);
	}

	public void delete(Carnet entity) {
		carnetRepository.delete(entity);
	}

	public void delete(Long id) {
		carnetRepository.deleteById(id);
	}

	public Carnet find(Long id) {
		return carnetRepository.findById(id).get();
	}

	public List<Carnet> findAll() {
		return carnetRepository.findAll();
	}
	
	public Carnet findByPropietario(Peregrino propietario){
		return carnetRepository.findByPropietario(propietario);
	}
	
	public void deleteInBatch(List<Carnet> carnets) {
		carnetRepository.deleteAll(carnets);
	}
}

