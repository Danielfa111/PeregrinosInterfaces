package com.danielfa11.tarea3AD2024.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;

@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long>{

	List<Parada> findByPeregrinos(List<Peregrino> peregrinos);
	
	Parada findByNombreAndRegion(String nombre, char region);
	
	Parada getReferenceByNombreAndRegion(String nombre, char region);
	
	Parada findTopByOrderByIdDesc();
	
	List<String> findNombreBy();
}
