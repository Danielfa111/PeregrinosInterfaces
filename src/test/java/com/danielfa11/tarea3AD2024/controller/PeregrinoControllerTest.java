package com.danielfa11.tarea3AD2024.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.CarnetService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PeregrinoControllerTest {

	@InjectMocks
    private PeregrinoController peregrinoController;
	
	@Mock
	private MenuItem miExportar;
	
	@Mock
	private MenuItem miCSesion;
		
	@Mock
	private MenuItem miSalir;
	
	@Mock
	private MenuItem miEditar;
	
	@Mock
	private MenuItem miAyuda;
	
	@Mock
	private Pane panelAyuda;
	
	@Mock
	private ImageView btnVer;
	
	@Mock
	private WebView webView;
	
	@Mock
	private GridPane panelPrincipal;
	
	@Mock
	private Label lblBienvenido;
	
	@Mock
	private GridPane panelEditar;
	
	@Mock
	private TextField txtNombre;
	
	@Mock
	private TextField txtUsuario;
	
	@Mock
	private TextField txtContraseña;
	
	@Mock
	private PasswordField ptxtContraseña;
	
	@Mock
	private TextField txtCorreo;
	
	@Mock
	private ComboBox<String> cboxNacionalidad;
	
	@Mock
	private Button btnCancelar;
	
	@Mock
	private Button btnConfirmar;
	
	@Mock
	private PeregrinoService peregrinoService;
	
	@Mock
	private Peregrino peregrino;
	
	@Mock
	private UsuarioService usuarioService;
	
	@Mock
	private Usuario usuario;
	
	@Mock
	private CarnetService carnetService;
	
	@Mock
	private Carnet carnetPeregrino;
	
	@Mock
	private DataSource ds;

	@Mock
    private StageManager stageManager;
	
	@Mock
	private ObservableList<String> paises; 
	
	
	@Mock
	private Alert alert;
	
	@BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {}); 
    }
    
	@BeforeEach
	void setUp() {
	    when(alert.showAndWait()).thenReturn(Optional.of(ButtonType.OK));
	}

	// Da errores
	@Test
	public void testClickConfirmar_Success() throws InterruptedException {
	    when(txtNombre.getText()).thenReturn("Peregrino 1");
	    when(txtUsuario.getText()).thenReturn("usuario1");
	    when(ptxtContraseña.getText()).thenReturn("contraseña1");
	    when(txtCorreo.getText()).thenReturn("usuario1@example.com");
	    when(cboxNacionalidad.getValue()).thenReturn("ES");

	    when(usuarioService.existsBy("usuario1")).thenReturn(false);
	    when(peregrinoService.save(any(Peregrino.class))).thenReturn(new Peregrino());
	    when(usuarioService.save(any(Usuario.class))).thenReturn(new Usuario());

	    CountDownLatch latch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        peregrinoController.clickConfirmar();
	        latch.countDown();
	    });
	    latch.await();

	    verify(peregrinoService, times(1)).save(any(Peregrino.class));
	    verify(usuarioService, times(1)).save(any(Usuario.class));

	    verify(txtNombre).clear();
	    verify(txtUsuario).clear();
	    verify(ptxtContraseña).clear();
	    verify(txtCorreo).clear();
	    verify(cboxNacionalidad).setValue(null);

	    verify(panelPrincipal).setVisible(true);
	    verify(panelEditar).setVisible(false);
	    verify(panelAyuda).setVisible(false);
	}

	@Test
	public void testClickConfirmar_EmptyFields() throws InterruptedException {
	    when(txtNombre.getText()).thenReturn("");
	    when(txtUsuario.getText()).thenReturn("");
	    when(ptxtContraseña.getText()).thenReturn("");
	    when(txtCorreo.getText()).thenReturn("");
	    when(cboxNacionalidad.getValue()).thenReturn(null);

	    CountDownLatch latch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        peregrinoController.clickConfirmar();
	        latch.countDown();
	    });
	    latch.await();

	    verify(peregrinoService, never()).save(any(Peregrino.class));
	    verify(usuarioService, never()).save(any(Usuario.class));
	}

	@Test
	public void testClickConfirmar_UsuarioExistente() throws InterruptedException {
	    when(txtNombre.getText()).thenReturn("Peregrino 1");
	    when(txtUsuario.getText()).thenReturn("usuario1");
	    when(ptxtContraseña.getText()).thenReturn("contraseña1");
	    when(txtCorreo.getText()).thenReturn("usuario1@example.com");
	    when(cboxNacionalidad.getValue()).thenReturn("ES");

	    when(usuarioService.existsBy("usuario1")).thenReturn(true);

	    CountDownLatch latch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        peregrinoController.clickConfirmar();
	        latch.countDown();
	    });
	    latch.await();

	    verify(peregrinoService, never()).save(any(Peregrino.class));
	    verify(usuarioService, never()).save(any(Usuario.class));
	}

	@Test
	public void testClickConfirmar_InvalidEmail() throws InterruptedException {
	    when(txtNombre.getText()).thenReturn("Peregrino 1");
	    when(txtUsuario.getText()).thenReturn("usuario1");
	    when(ptxtContraseña.getText()).thenReturn("contraseña1");
	    when(txtCorreo.getText()).thenReturn("invalid-email");
	    when(cboxNacionalidad.getValue()).thenReturn("ES");

	    CountDownLatch latch = new CountDownLatch(1);
	    Platform.runLater(() -> {
	        peregrinoController.clickConfirmar();
	        latch.countDown();
	    });
	    latch.await();

	    verify(peregrinoService, never()).save(any(Peregrino.class));
	    verify(usuarioService, never()).save(any(Usuario.class));
	}
    
    
	
}
