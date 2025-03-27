package com.danielfa11.tarea3AD2024.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.utils.Utils;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;

@Controller
public class AdministradorController implements Initializable{

	@FXML
	private MenuItem miRegistrar;
	
	@FXML
	private MenuItem miCSesion;
		
	@FXML
	private MenuItem miSalir;
	
	@FXML
	private MenuItem miAyuda;
	
	@FXML
	private GridPane panelPrincipal;
	
	@FXML
	private Label lblBienvenido;
	
	@FXML
	private GridPane panelRegistrar;
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private PasswordField ptxtContraseña;
	
	@FXML
	private TextField txtCorreo;
	
	@FXML
	private ComboBox<Character> cboxRegion;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnRegistrar;
	
	@FXML
	private Pane panelAyuda;
	
	@FXML
	private WebView webView;
	
	@Lazy
    @Autowired
    private StageManager stageManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ParadaService paradaService;
	
	private List<Character> regiones = Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
	
	/*
	 * Metodo para cargar datos al iniciar la vista
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		String url = getClass().getResource("/help/menuAyuda.html").toExternalForm();
		webView.getEngine().load(url);
	
	 	cboxRegion.getItems().addAll(regiones);
	}
	
	/*
	 * Metodo para volver al panel principal
	 */
	
	public void clickCancelar() {
		
		panelPrincipal.setVisible(true);
		panelRegistrar.setVisible(false);	
		panelAyuda.setVisible(false);
		
	}
	
	/*
	 * Metodo para registrar una parada
	 */
	public void clickRegistrar() {
		
		if(Utils.confirmarDatos().getResult().equals(ButtonType.OK)) {
			if(Utils.validarNombre(txtNombre.getText())
				&& Utils.validarUsuario(txtUsuario.getText())
				&& usuarioExistente(txtUsuario.getText())
				&& Utils.validarContraseña(ptxtContraseña.getText())
				&& Utils.validarEmail(txtCorreo.getText())
				&& validarRegion()
				) {

				Parada parada = new Parada();
				parada.setNombre(txtNombre.getText());
				parada.setRegion(cboxRegion.getValue());
				parada.setResponsable(txtUsuario.getText());
				
				parada = paradaService.save(parada);
				
				Usuario usuario = new Usuario();
				usuario.setUsuario(txtUsuario.getText());
				usuario.setContraseña(ptxtContraseña.getText());
				usuario.setCorreo(txtCorreo.getText());
				usuario.setRol("Parada");
				usuario.setId(parada.getId());
				
				usuario = usuarioService.save(usuario);
				
				Sesion.getSesion().setId(usuario.getId());
				Sesion.getSesion().setPerfil(usuario.getRol());
				Sesion.getSesion().setUsuario(usuario.getUsuario());
				
				txtNombre.clear();
				txtUsuario.clear();
				ptxtContraseña.clear();
				txtCorreo.clear();
				cboxRegion.valueProperty().set(null);
				
				panelPrincipal.setVisible(true);
				panelRegistrar.setVisible(false);	
				panelAyuda.setVisible(false);
				
				alertaConfirmacion();
				
			}
		}
	}
	
	/*
	 * Metodo para entrar en el menu de registro de paradas
	 */	
	public void clickMenuRegistrar() {
		panelPrincipal.setVisible(false);
		panelRegistrar.setVisible(true);
		panelAyuda.setVisible(false);
	}
	
	
	/*
	 * Metodo para cerrar sesion y regresar al login
	 */	
	public void clickCerrarSesion() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setContentText("¿Desea cerrar sesion?");
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.OK)) 
        {
			Sesion.getSesion().setId(-1L);
			Sesion.getSesion().setUsuario("");;
			Sesion.getSesion().setPerfil("");
			stageManager.switchScene(FxmlView.LOGIN);
        }
	}
	
	/*
	 * Metodo para salir de la aplicación
	 */		
	public void clickSalir()
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setContentText("¿Desea salir de la aplicación?");
        alert.showAndWait();
        
        if (alert.getResult().equals(ButtonType.OK)) 
        {
            Platform.exit();
        }
	}
	
	/*
	 * Metodo para entrar en el menu de ayuda
	 */	
	public void clickAyuda() {
		
		panelAyuda.setVisible(true);
		panelPrincipal.setVisible(false);
		panelRegistrar.setVisible(false);
		
	}
	
	
	/*
	 * Metodo para generar un informe de parada
	 */	
	public void clickDatos() {
		
		Alert alerta = new Alert(AlertType.CONFIRMATION);
		alerta.setTitle("Generacion de informe");
		alerta.setContentText("Si aceptas se generara el informe y se abrira en forma de pdf en google chrome");
        alerta.showAndWait();
        
        if (alerta.getResult().equals(ButtonType.OK)) 
        {
        	try {
    	        
    	        List<Parada> paradas = paradaService.findAll();
    	        JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(paradas);

    	        JasperDesign jasperDesign = new JasperDesign();
    	        jasperDesign.setName("ReporteParadas");
    	        jasperDesign.setColumnCount(1);
    	        jasperDesign.setPageWidth(595);
    	        jasperDesign.setPageHeight(842);
    	        jasperDesign.setLeftMargin(20);
    	        jasperDesign.setRightMargin(20);
    	        jasperDesign.setTopMargin(20);
    	        jasperDesign.setBottomMargin(20);
    	  
    	        String[] fieldNames = {"nombre", "region", "responsable", "peregrinos"};
    	        Class<?>[] fieldTypes = {String.class, Character.class, String.class, List.class};

    	        for (int i = 0; i < fieldNames.length; i++) {
    	        	
    	            JRDesignField field = new JRDesignField();
    	            field.setName(fieldNames[i]);
    	            field.setValueClass(fieldTypes[i]);
    	            jasperDesign.addField(field);
    	            
    	        }

    	        JRDesignBand titleBand = new JRDesignBand();
    	        titleBand.setHeight(50);

    	        JRDesignTextField titleTextField = new JRDesignTextField();
    	        titleTextField.setX(0);
    	        titleTextField.setY(0);
    	        titleTextField.setWidth(300);
    	        titleTextField.setHeight(30);
    	        titleTextField.setExpression(new JRDesignExpression("\"Informe de Paradas\""));
    	        titleBand.addElement(titleTextField);

    	        jasperDesign.setTitle(titleBand);

    	        JRDesignBand headerBand = new JRDesignBand();
    	        headerBand.setHeight(20);

    	        int xOffset = 0;
    	        int width = 150;

    	        for (String fieldName : fieldNames) {
    	        	
    	            JRDesignTextField headerField = new JRDesignTextField();
    	            headerField.setX(xOffset);
    	            headerField.setY(0);
    	            headerField.setWidth(width);
    	            headerField.setHeight(20);
    	            headerField.setExpression(new JRDesignExpression("\"" + fieldName.toUpperCase() + "\""));
    	            headerBand.addElement(headerField);

    	            xOffset += width;
    	            
    	        }

    	        jasperDesign.setColumnHeader(headerBand);

    	        JRDesignBand detailBand = new JRDesignBand();
    	        detailBand.setHeight(20);

    	        xOffset = 0;

                for (String fieldName : fieldNames) {
                    JRDesignTextField valueField = new JRDesignTextField();
                    valueField.setX(xOffset);
                    valueField.setY(0);
                    valueField.setWidth(width);
                    valueField.setHeight(20);
                    if (fieldName.equals("peregrinos")) {
                    	
                        valueField.setExpression(new JRDesignExpression("$F{peregrinos}.size()"));
                        
                    } else {
                    	
                        valueField.setExpression(new JRDesignExpression("$F{" + fieldName + "}"));
                        
                    }
                    detailBand.addElement(valueField);

                    xOffset += width;
                }	       

    	        ((JRDesignSection)jasperDesign.getDetailSection()).addBand(detailBand);

    	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new java.util.HashMap<>(), jrDataSource);

    	        Path tempFile = Files.createTempFile("reporte_paradas", ".pdf");
    	        JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.toString());

    	        System.out.println("Informe generado correctamente en: " + tempFile);

    	        ProcessBuilder processBuilder = new ProcessBuilder(
    	        		
    	            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
    	            "--disable-translate",
    	            "--start-maximized",
    	            tempFile.toString()
    	            
    	        );
    	        processBuilder.start();
    	        
    	    } catch (JRException | IOException e) {
    	        e.printStackTrace();
    	    }
        }
		
		
	}
	
	
	
	
	/*
	 * Metodo para validar la region de la parada
	 */	
	private boolean validarRegion() {
		if(cboxRegion.getValue() == null) {
			alertaRegionVacia();
			return false;
		}
		else{
			return true;
		}
	}
	
	
	/*
	 * Metodo para mostrar una alerta de region vacia
	 */	
	private void alertaRegionVacia() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Region vacia");
		alerta.setContentText("Escoge region");
		alerta.show();
	}

	
	/*
	 * Metodo para entrar en el menu de registro de paradas
	 */	
	private boolean usuarioExistente(String usuario) {
		
		if(usuario.isBlank()) {
			alertaUsuarioVacio();
		}	
		else if(usuarioService.existsBy(usuario)) {
			alertaUsuario();
			return false;
		}
		return true;
	}
	
	/*
	 * Alerta para usuario existente
	 */	
	
	private void alertaUsuario() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario existente");
		alerta.setContentText("Ese usuario ya existe");
		alerta.show();
	}
	
	/*
	 * Alerta para usuario vacio
	 */	
	
	private void alertaUsuarioVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario vacio");
		alerta.setContentText("Introduce un usuario");
		alerta.show();
	}
	
	/*
	 * Alerta para confirmacion de registro
	 */	
	
	private void alertaConfirmacion() {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Registro correcto");
		alerta.setContentText("Se ha registrado la parada");
		alerta.show();
	}
	
	
	
	
}
