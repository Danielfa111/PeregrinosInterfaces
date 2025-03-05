package com.danielfa11.tarea3AD2024.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielfa11.tarea3AD2024.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	
	Usuario findByUsuarioAndContraseña(String usuario, String contraseña);
	
	boolean existsByUsuarioAndContraseña(String usuario, String contraseña);
	
}
