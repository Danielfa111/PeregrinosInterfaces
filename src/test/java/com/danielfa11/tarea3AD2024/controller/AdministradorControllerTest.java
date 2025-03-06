package com.danielfa11.tarea3AD2024.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.utils.Utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdministradorControllerTest {

    @InjectMocks
    private AdministradorController administradorController;

    @Mock
    private TextField txtNombre;

    @Mock
    private TextField txtUsuario;

    @Mock
    private PasswordField ptxtContraseña;

    @Mock
    private TextField txtCorreo;

    @Mock
    private ComboBox<Character> cboxRegion;

    @Mock
    private GridPane panelPrincipal;

    @Mock
    private GridPane panelRegistrar;

    @Mock
    private Pane panelAyuda;

    @Mock
    private StageManager stageManager;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ParadaService paradaService;

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
    public void testClickRegistrar_Success() throws InterruptedException {
        try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
            Alert mockAlert = mock(Alert.class);
            when(mockAlert.getResult()).thenReturn(ButtonType.OK);
            mockedUtils.when(Utils::confirmarDatos).thenReturn(mockAlert);

            mockedUtils.when(() -> Utils.validarNombre(anyString())).thenReturn(true);
            mockedUtils.when(() -> Utils.validarContraseña(anyString())).thenReturn(true);
            mockedUtils.when(() -> Utils.validarEmail(anyString())).thenReturn(true);

            when(txtNombre.getText()).thenReturn("Parada 1");
            when(txtUsuario.getText()).thenReturn("usuario1");
            when(ptxtContraseña.getText()).thenReturn("contraseña1");
            when(txtCorreo.getText()).thenReturn("usuario1@example.com");
            when(cboxRegion.getValue()).thenReturn('A');

            when(usuarioService.existsBy("usuario1")).thenReturn(false);
            when(paradaService.save(any(Parada.class))).thenReturn(new Parada());
            when(usuarioService.save(any(Usuario.class))).thenReturn(new Usuario());

            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                administradorController.clickRegistrar();
                latch.countDown();
            });
            latch.await();

            verify(paradaService, times(1)).save(any(Parada.class));
            verify(usuarioService, times(1)).save(any(Usuario.class));

            verify(txtNombre).clear();
            verify(txtUsuario).clear();
            verify(ptxtContraseña).clear();
            verify(txtCorreo).clear();
            verify(cboxRegion).setValue(null);

            verify(panelPrincipal).setVisible(true);
            verify(panelRegistrar).setVisible(false);
            verify(panelAyuda).setVisible(false);
        }
    }

    @Test
    public void testClickRegistrar_UsuarioExistente() throws InterruptedException {

        when(txtNombre.getText()).thenReturn("Parada 1");
        when(txtUsuario.getText()).thenReturn("usuario1");
        when(ptxtContraseña.getText()).thenReturn("contraseña1");
        when(txtCorreo.getText()).thenReturn("usuario1@example.com");
        when(cboxRegion.getValue()).thenReturn('A');

        when(usuarioService.existsBy("usuario1")).thenReturn(true);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            administradorController.clickRegistrar();
            latch.countDown();
        });
        latch.await();

        verify(paradaService, never()).save(any(Parada.class));
        verify(usuarioService, never()).save(any(Usuario.class));
    }

    @Test
    public void testClickRegistrar_RegionVacia() throws InterruptedException {

        when(txtNombre.getText()).thenReturn("Parada 1");
        when(txtUsuario.getText()).thenReturn("usuario1");
        when(ptxtContraseña.getText()).thenReturn("contraseña1");
        when(txtCorreo.getText()).thenReturn("usuario1@example.com");
        when(cboxRegion.getValue()).thenReturn(null);

        when(usuarioService.existsBy("usuario1")).thenReturn(false);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            administradorController.clickRegistrar();
            latch.countDown();
        });
        latch.await();

        verify(paradaService, never()).save(any(Parada.class));
        verify(usuarioService, never()).save(any(Usuario.class));
    }

    @Test
    public void testClickRegistrar_CamposVacios() throws InterruptedException {

        when(txtNombre.getText()).thenReturn("");
        when(txtUsuario.getText()).thenReturn("");
        when(ptxtContraseña.getText()).thenReturn("");
        when(txtCorreo.getText()).thenReturn("");
        when(cboxRegion.getValue()).thenReturn(null);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            administradorController.clickRegistrar();
            latch.countDown();
        });
        latch.await();

        verify(paradaService, never()).save(any(Parada.class));
        verify(usuarioService, never()).save(any(Usuario.class));
    }
}