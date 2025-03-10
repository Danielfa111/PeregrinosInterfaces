package com.danielfa11.tarea3AD2024.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;


@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

	Carnet findByPropietario(Peregrino propietario);
	
}
