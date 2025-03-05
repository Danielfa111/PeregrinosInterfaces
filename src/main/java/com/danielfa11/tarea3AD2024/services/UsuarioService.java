package com.danielfa11.tarea3AD2024.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.repositorios.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario save(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public Usuario update(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}

	public void delete(String usuario) {
		usuarioRepository.deleteById(usuario);		
	}
	
	public Usuario find(String usuario) {
		return usuarioRepository.findById(usuario).get();
	}

	public Usuario getReferenceByUsuario(String usuario) {
		return usuarioRepository.getReferenceById(usuario);
	}
	
	public Usuario findByUsuarioAndContraseña(String usuario, String contraseña) {
		return usuarioRepository.findByUsuarioAndContraseña(usuario, contraseña);
	}
	
	public boolean existsBy(String usuario) {
		return usuarioRepository.existsById(usuario);
	}
	
	public boolean existsByUsuarioAndContraseña(String usuario, String contraseña) {
		return usuarioRepository.existsByUsuarioAndContraseña(usuario, contraseña);
	}
	
}
