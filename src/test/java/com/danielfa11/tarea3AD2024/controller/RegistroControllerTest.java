package com.danielfa11.tarea3AD2024.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
public class RegistroControllerTest {

	@InjectMocks
	private RegistroController registroController;
	
	@Mock
	private TextField txtNombre;
	
	@Mock
	private TextField txtUsuario;
	
	@Mock
	private PasswordField ptxtContraseña;
	
	@Mock
	private PasswordField ptxtConfirmar;
	
	@Mock
	private TextField txtCorreo;
	
	@Mock
	private ComboBox<String> cboxNacionalidad;
	
	@Mock
	private ComboBox<Parada> cboxParada;
	
	@Mock
	private Button btnCancelar;
	
	@Mock
	private Button btnRegistrar;

	@Mock
    private StageManager stageManager;
	
	@Mock
	private ParadaService paradaService;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Mock
	private PeregrinoService peregrinoService;
	
	@Mock
	private ObservableList<String> paises; 
	
	@Mock
	private ObservableList<Parada> paradas;
	
	@Mock
	private Alert alert;
	
	@BeforeEach
	public void setUp() {
		
		
		Parada parada = new Parada();
		parada.setId(1L);
		
		when(paradaService.find(1L)).thenReturn(parada);
		
		
		when(alert.getResult().equals(ButtonType.OK)).thenReturn(true);
		
		when(txtNombre.getText()).thenReturn("daniel");
		when(txtUsuario.getText()).thenReturn("daniel");
		when(ptxtContraseña.getText()).thenReturn("daniel");
		when(ptxtConfirmar.getText()).thenReturn("daniel");
		when(txtCorreo.getText()).thenReturn("dani@gmail.com");
	
		when(cboxNacionalidad.getValue()).thenReturn("ES");
		when(cboxParada.getValue()).thenReturn(parada);
		
		Carnet carnet = new Carnet();
		carnet.setFechaexp(LocalDate.now());
		
		Peregrino peregrino = new Peregrino();
		peregrino.setId(1L);
		peregrino.setNombre("daniel");
		peregrino.setNacionalidad("ES");
		peregrino.setCarnet(carnet);
		peregrino.getParadas().add(parada);
		
		when(peregrinoService.find(1L)).thenReturn(peregrino);
		
		when(peregrinoService.save(peregrino)).thenReturn(peregrino);
		
		parada.getPeregrinos().add(peregrino);
		when(paradaService.save(parada)).thenReturn(parada);
		
		Usuario usuario = new Usuario();
		usuario.setUsuario("daniel");
		usuario.setContraseña("daniel");
		usuario.setCorreo("dani@gmail.com");
		usuario.setRol("Peregrino");
		
		
		when(peregrinoService.findTopByOrderByIdDesc().getId()).thenReturn(1L);
		when(peregrinoService.findTopByOrderByIdDesc()).thenReturn(peregrino);
		
		when(usuarioService.save(usuario)).thenReturn(usuario);

		
	}
	
	@Test
	public void testRegistroSuccess() {
		
		registroController.clickRegistrar();
		
		verify(usuarioService, times(1)).save(any(Usuario.class));
		verify(peregrinoService, times(1)).save(any(Peregrino.class));
		verify(paradaService, times(1)).save(any(Parada.class));
		verify(stageManager, times(1)).switchScene(FxmlView.PEREGRINO);
		
	}
	
	
	
}
