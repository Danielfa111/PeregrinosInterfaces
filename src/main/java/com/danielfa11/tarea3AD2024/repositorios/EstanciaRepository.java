package com.danielfa11.tarea3AD2024.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfa11.tarea3AD2024.modelo.Estancia;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;


@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {
	
	List<Estancia> findByPeregrino(Peregrino peregrino);
	
	List<Estancia> findByFechaBetweenAndParada(LocalDate fecha1, LocalDate fecha2, Parada IdParada);
}
