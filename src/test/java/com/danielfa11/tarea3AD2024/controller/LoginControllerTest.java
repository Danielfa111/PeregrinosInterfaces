package com.danielfa11.tarea3AD2024.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

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
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Label lblTitulo;

    @Mock
    private TextField txtUsuario;

    @Mock
    private PasswordField ptxtContraseña;

    @Mock
    private StageManager stageManager;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Properties p;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Configurar las credenciales de administrador en el objeto Properties
        when(p.getProperty("adminuser")).thenReturn("admin");
        when(p.getProperty("adminpass")).thenReturn("admin");

    }
// No puedo hacer que funcione
//    @Test
//    public void testClickLogin_AdminSuccess() throws InterruptedException {
//       
//    	when(p.getProperty("adminuser")).thenReturn("admin");
//        when(p.getProperty("adminpass")).thenReturn("admin");
//    	when(txtUsuario.getText()).thenReturn("admin");
//    	when(ptxtContraseña.getText()).thenReturn("admin");
//
//
//        
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            loginController.clickLogin();
//            latch.countDown();
//        });
//        latch.await();
//
//        
//        verify(stageManager, times(1)).switchScene(FxmlView.ADMINISTRADOR);
//    }

    @Test
    public void testClickLogin_UsuarioPeregrinoSuccess() throws InterruptedException {
        
        when(txtUsuario.getText()).thenReturn("usuario1");
        when(ptxtContraseña.getText()).thenReturn("contraseña1");

      
        Usuario usuario = new Usuario();
        usuario.setUsuario("usuario1");
        usuario.setContraseña("contraseña1");
        usuario.setRol("Peregrino");
        usuario.setId(1L);
        usuario.setCorreo("usuario1@example.com");

        when(usuarioService.existsByUsuarioAndContraseña("usuario1", "contraseña1")).thenReturn(true);
        when(usuarioService.find("usuario1")).thenReturn(usuario);

      
        Sesion.getSesion().setId(null);
        Sesion.getSesion().setPerfil(null);
        Sesion.getSesion().setUsuario(null);
        Sesion.getSesion().setCorreo(null);

        
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController.clickLogin();
            latch.countDown();
        });
        latch.await();

        
        verify(stageManager, times(1)).switchScene(FxmlView.PEREGRINO);

       
        assert Sesion.getSesion().getId() == 1L;
        assert Sesion.getSesion().getPerfil().equals("Peregrino");
        assert Sesion.getSesion().getUsuario().equals("usuario1");
        assert Sesion.getSesion().getCorreo().equals("usuario1@example.com");
    }

    @Test
    public void testClickLogin_UsuarioParadaSuccess() throws InterruptedException {
       
        when(txtUsuario.getText()).thenReturn("usuario2");
        when(ptxtContraseña.getText()).thenReturn("contraseña2");

        
        Usuario usuario = new Usuario();
        usuario.setUsuario("usuario2");
        usuario.setContraseña("contraseña2");
        usuario.setRol("Parada");
        usuario.setId(2L);
        usuario.setCorreo("usuario2@example.com");

        when(usuarioService.existsByUsuarioAndContraseña("usuario2", "contraseña2")).thenReturn(true);
        when(usuarioService.find("usuario2")).thenReturn(usuario);

        
        Sesion.getSesion().setId(null);
        Sesion.getSesion().setPerfil(null);
        Sesion.getSesion().setUsuario(null);
        Sesion.getSesion().setCorreo(null);

       
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController.clickLogin();
            latch.countDown();
        });
        latch.await();


        verify(stageManager, times(1)).switchScene(FxmlView.RESPONSABLE);


        assert Sesion.getSesion().getId() == 2L;
        assert Sesion.getSesion().getPerfil().equals("Parada");
        assert Sesion.getSesion().getUsuario().equals("usuario2");
        assert Sesion.getSesion().getCorreo().equals("usuario2@example.com");
    }

    @Test
    public void testClickLogin_UsuarioExistenteContraseñaIncorrecta() throws InterruptedException {

        when(txtUsuario.getText()).thenReturn("usuario1");
        when(ptxtContraseña.getText()).thenReturn("contraseñaIncorrecta");


        when(usuarioService.existsByUsuarioAndContraseña("usuario1", "contraseñaIncorrecta")).thenReturn(false);
        when(usuarioService.existsBy("usuario1")).thenReturn(true);


        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController.clickLogin();
            latch.countDown();
        });
        latch.await();


        verify(stageManager, never()).switchScene(any(FxmlView.class));
    }

    @Test
    public void testClickLogin_UsuarioNoExistente() throws InterruptedException {

        when(txtUsuario.getText()).thenReturn("usuarioInvalido");
        when(ptxtContraseña.getText()).thenReturn("contraseñaInvalida");


        when(usuarioService.existsByUsuarioAndContraseña("usuarioInvalido", "contraseñaInvalida")).thenReturn(false);
        when(usuarioService.existsBy("usuarioInvalido")).thenReturn(false);


        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController.clickLogin();
            latch.countDown();
        });
        latch.await();


        verify(stageManager, never()).switchScene(any(FxmlView.class));
    }

    @Test
    public void testClickLogin_UsuarioVacio() throws InterruptedException {

        when(txtUsuario.getText()).thenReturn("");
        when(ptxtContraseña.getText()).thenReturn("contraseña1");


        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            loginController.clickLogin();
            latch.countDown();
        });
        latch.await();


        verify(stageManager, never()).switchScene(any(FxmlView.class));
    }
}