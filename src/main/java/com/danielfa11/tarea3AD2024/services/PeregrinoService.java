package com.danielfa11.tarea3AD2024.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.repositorios.PeregrinoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	public Peregrino save(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public Peregrino update(Peregrino entity) {
		return peregrinoRepository.save(entity);
	}

	public void delete(Peregrino entity) {
		peregrinoRepository.delete(entity);
	}

	public void delete(Long id) {
		peregrinoRepository.deleteById(id);
	}

	public Peregrino find(Long id) {
		return peregrinoRepository.findById(id).get();
	}
	
	public boolean comprobarPeregrino(Long id) {
		try {
			peregrinoRepository.getReferenceById(id);
		}
		catch(EntityNotFoundException e) {
			return false;
		}

		return true;
	}
	
	public boolean existsById(Long id) {
		return peregrinoRepository.existsById(id);		
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}
	
	public Peregrino findTopByOrderByIdDesc() {
		return peregrinoRepository.findTopByOrderByIdDesc();
	}
	
	public void deleteInBatch(List<Peregrino> paradas) {
		peregrinoRepository.deleteAll(paradas);
	}
	
}
