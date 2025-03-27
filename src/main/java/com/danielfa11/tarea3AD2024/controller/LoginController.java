package com.danielfa11.tarea3AD2024.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

@Controller
public class LoginController implements Initializable{

	@FXML
	private Label lblTitulo;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private PasswordField ptxtContraseña;
	
	@FXML
	private TextField txtContraseña;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private Button btnRegistro;
	
	@FXML
	private ImageView btnVer;
	
	@FXML
	private Text txtOlvido;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired 
	private UsuarioService usuarioService;
	
	private FileInputStream fis;
	
    private Properties p = new Properties();
    
    private String adminUser;
    
    private String adminPass;
    
    /*
	 * Metodo para cargar datos al iniciar la vista
	 */
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		ptxtContraseña.textProperty().bindBidirectional(txtContraseña.textProperty());
		try {
			fis = new FileInputStream("src/main/resources/application.properties");
			p.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                            
        adminUser = p.getProperty("adminuser");
        adminPass = p.getProperty("adminpass");
        
	}
	
	/*
	 * Metodo para hacer login
	 */
	
	public void clickLogin() {
		
		if(txtUsuario.getText().equals(adminUser)  && ptxtContraseña.getText().equals(adminPass)){
			stageManager.switchScene(FxmlView.ADMINISTRADOR);
		}		
		else if(usuarioExistente(txtUsuario.getText(), ptxtContraseña.getText())) {
			
			Usuario usuario = usuarioService.find(txtUsuario.getText());
			
			Sesion.getSesion().setId(usuario.getId());
			Sesion.getSesion().setPerfil(usuario.getRol());
			Sesion.getSesion().setUsuario(usuario.getUsuario());
			Sesion.getSesion().setCorreo(usuario.getCorreo());

			if(Sesion.getSesion().getPerfil().equals("Peregrino")) {
				stageManager.switchScene(FxmlView.PEREGRINO);
			}
			else if(Sesion.getSesion().getPerfil().equals("Parada")){
				stageManager.switchScene(FxmlView.RESPONSABLE);
			}
				
		}
	
	}
	
	/*
	 * Metodo para cambiar a la vista de registro
	 */
	
	public void clickRegistro() {
		stageManager.switchScene(FxmlView.REGISTRO);
	}
	
	/*
	 * Alerta de contraseña incorrecta
	 */
	
	private void alertaLogin() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Contraseña incorrecta");
		alerta.setContentText("La contraseña no es correcta");
		alerta.show();
	}
	
	/*
	 * Metodo para mostrar o esconder contraseña
	 */

	public void mostrarContraseña() {	
		if(ptxtContraseña.isVisible()) {
			btnVer.setImage( new Image ("/images/Login/EsconderContraseña.png"));
			txtContraseña.setVisible(true);
			ptxtContraseña.setVisible(false);
		}
		else {
			btnVer.setImage( new Image ("/images/Login/MostrarContraseña.png"));
			txtContraseña.setVisible(false);
			ptxtContraseña.setVisible(true);
		}
	}
	
	/*
	 * Metodo para validar usuario existente o vacio
	 */
	
	private boolean usuarioExistente(String usuario, String contraseña) {

		if(usuarioService.existsByUsuarioAndContraseña(usuario, contraseña)) {
			return true;
		}
		else if(usuarioService.existsBy(usuario)){
			alertaLogin();
			return false;
		}
		else if(usuario.isBlank()) {
			alertaUsuarioVacio();
			return false;
		}	
		else {
			alertaUsuario();
		}
		return false;
		
	}
	
	/*
	 * Alerta de usuario vacio
	 */
	
	private void alertaUsuarioVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario vacio");
		alerta.setContentText("Introduce un usuario");
		alerta.show();
	}
	
	/*
	 * Alerta de login fallido
	 */
	
	private void alertaUsuario() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Login fallido");
		alerta.setContentText("Ese usuario no existe");
		alerta.show();
	}
}
