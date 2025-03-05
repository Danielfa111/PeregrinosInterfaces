package com.danielfa11.tarea3AD2024.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.utils.Utils;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class RegistroController implements Initializable{

	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private PasswordField ptxtContraseña;
	
	@FXML
	private PasswordField ptxtConfirmar;
	
	@FXML
	private TextField txtCorreo;
	
	@FXML
	private ComboBox<String> cboxNacionalidad;
	
	@FXML
	private ComboBox<Parada> cboxParada;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnRegistrar;

	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PeregrinoService peregrinoService;
	
	private ObservableList<String> paises; 
	
	private ObservableList<Parada> paradas;
	
	@SuppressWarnings("unused")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		paises = FXCollections.observableArrayList(Utils.getDiccionarioPaises().keySet());
		
		paradas =  FXCollections.observableArrayList(paradaService.findAll());
		
		cboxNacionalidad.setItems(paises);
		
		cboxNacionalidad.setCellFactory(listView -> new ListCell<String>() {
			@Override
            protected void updateItem(String pais, boolean empty) {
                super.updateItem(pais, empty);
                if (empty || pais == null) {
                    setText(null);
                } else {
                    setText(pais+" : "+Utils.getDiccionarioPaises().get(pais));
                }
            }
        });
		
		cboxParada.setItems(paradas);
		
		cboxParada.setCellFactory(listView -> new ListCell<Parada>() {
			@Override
            protected void updateItem(Parada parada, boolean empty) {
                super.updateItem(parada, empty);
                if (empty || parada == null) {
                    setText(null);
                } else {
                    setText(parada.getNombre());
                }
            }
        });
		
	}
	
	public void clickRegistrar() {
		
		if(Utils.confirmarDatos().getResult().equals(ButtonType.OK)) {
			if(Utils.validarNombre(txtNombre.getText())
				&& usuarioExistente(txtUsuario.getText())
				&& Utils.validarContraseña(ptxtContraseña.getText())
				&& contraseñasIguales()
				&& Utils.validarEmail(txtCorreo.getText())
				&& validarNacionalidad()
				) {

				Parada parada = paradaService.find(cboxParada.getValue().getId());
				
				Carnet carnet = new Carnet();
				carnet.setFechaexp(LocalDate.now());
				
				Peregrino peregrino = new Peregrino();
				peregrino.setNombre(txtNombre.getText());
				peregrino.setNacionalidad(Utils.getDiccionarioPaises().get(cboxNacionalidad.getValue()));
				peregrino.setCarnet(carnet);
				peregrino.getParadas().add(parada);
				peregrino = peregrinoService.save(peregrino);
				
				parada.getPeregrinos().add(peregrino);
				parada = paradaService.save(parada);						
				
				Usuario usuario = new Usuario();
				usuario.setUsuario(txtUsuario.getText());
				usuario.setContraseña(ptxtContraseña.getText());
				usuario.setCorreo(txtCorreo.getText());
				usuario.setRol("Peregrino");
				if(peregrinoService.findTopByOrderByIdDesc()==null) {
					usuario.setId(1L);
				}
				else {
					usuario.setId(peregrinoService.findTopByOrderByIdDesc().getId());
				}
				
				usuario = usuarioService.save(usuario);
				
				if(peregrinoService.findTopByOrderByIdDesc()==null) {
					Sesion.getSesion().setId(1L);
				}
				else {
					Sesion.getSesion().setId(peregrinoService.findTopByOrderByIdDesc().getId());
				}
				
				
				Sesion.getSesion().setPerfil(usuario.getRol());
				Sesion.getSesion().setUsuario(usuario.getUsuario());
				
				stageManager.switchScene(FxmlView.PEREGRINO);
			}
		}
	}
	
	
	public void clickCancelar() {
		stageManager.switchScene(FxmlView.LOGIN);
	}
	
	
	
	private boolean contraseñasIguales() {
		if(ptxtContraseña.getText().equals(ptxtConfirmar.getText())) {
			return true;
		}
		else
		{
			alertaContraseñasIguales();
			return false;
		}
		
	}	

	private void alertaContraseñasIguales() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Contraseñas no coinciden");
		alerta.setContentText("Las contraseñas no coinciden");
		alerta.show();
	}
	
	private boolean validarNacionalidad() {
		if(cboxNacionalidad.getValue() == null) {
			alertaNacionalidadVacia();
			return false;
		}
		else{
			return true;
		}
	}
	
	private void alertaNacionalidadVacia() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Nacionalidad vacia");
		alerta.setContentText("Escoge nacionalidad");
		alerta.show();
	}
	
	private boolean usuarioExistente(String usuario) {
		if(usuario.isBlank()) {
			alertaUsuarioVacio();
			return false;
		}	
		else if(usuarioService.existsBy(usuario)) {
			alertaUsuario();
			return false;
		}
		return true;
	}
	
	private void alertaUsuario() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario existente");
		alerta.setContentText("Ese usuario ya existe");
		alerta.show();
	}
	
	private void alertaUsuarioVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario vacio");
		alerta.setContentText("Introduce un usuario");
		alerta.show();
	}

}
