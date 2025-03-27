package com.danielfa11.tarea3AD2024.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.modelo.Usuario;
import com.danielfa11.tarea3AD2024.services.CarnetService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;
import com.danielfa11.tarea3AD2024.services.UsuarioService;
import com.danielfa11.tarea3AD2024.utils.Utils;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class PeregrinoController implements Initializable{
	
	@FXML
	private MenuItem miExportar;
	
	@FXML
	private MenuItem miCSesion;
		
	@FXML
	private MenuItem miSalir;
	
	@FXML
	private MenuItem miEditar;
	
	@FXML
	private MenuItem miAyuda;
	
	@FXML
	private Pane panelAyuda;
	
	@FXML
	private ImageView btnVer;
	
	@FXML
	private WebView webView;
	
	@FXML
	private GridPane panelPrincipal;
	
	@FXML
	private Label lblBienvenido;
	
	@FXML
	private GridPane panelEditar;
	
	@FXML
	private TextField txtNombre;
	
	@FXML
	private TextField txtUsuario;
	
	@FXML
	private TextField txtContraseña;
	
	@FXML
	private PasswordField ptxtContraseña;
	
	@FXML
	private TextField txtCorreo;
	
	@FXML
	private ComboBox<String> cboxNacionalidad;
	
	@FXML
	private Button btnCancelar;
	
	@FXML
	private Button btnConfirmar;
	
	@Autowired
	private PeregrinoService peregrinoService;
	
	private Peregrino peregrino;
	
	@Autowired
	private UsuarioService usuarioService;
	
	private Usuario usuario;
	
	@Autowired
	private CarnetService carnetService;
	
	private Carnet carnetPeregrino;
	
	@Autowired
	private DataSource ds;

	@Lazy
    @Autowired
    private StageManager stageManager;
	
	private ObservableList<String> paises; 
	
	private boolean usuarioSinCambiar = false;
	
	/*
	 * Metodo para cargar datos al iniciar la vista
	 */
	
	@SuppressWarnings("unused")
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		
		ptxtContraseña.textProperty().bindBidirectional(txtContraseña.textProperty());
		
		String url = getClass().getResource("/help/menuAyuda.html").toExternalForm();
		webView.getEngine().load(url);
		
		paises = FXCollections.observableArrayList(Utils.getDiccionarioPaises().keySet());
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
		

		peregrino = peregrinoService.find(Sesion.getSesion().getId());
		usuario = usuarioService.find(Sesion.getSesion().getUsuario());
		lblBienvenido.setText("Bienvenido/a, "+peregrino.getNombre());
		
		txtUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
			
			if(newValue.equals(usuario.getUsuario())){
				usuarioSinCambiar = true;
			}
			else {
				usuarioSinCambiar = false;
			}
			
		});
		
	}
	
	/*
	 * Metodo para exportar el carnet del peregrino
	 */
	
	public void clickExportar() {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Atención");
        alert.setContentText("Su carnet sera exportado en la carpeta Carnets dentro de Resources del proyecto actual y se abrira en Google Chrome");
        alert.showAndWait();		
        
        if (alert.getResult().equals(ButtonType.OK)) 
        {
        	
        	try {
    			
    			Map<String, Object> parameters = new HashMap<>();
    	        parameters.put("IdPeregrino", Sesion.getSesion().getId());
    			
    			JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/reports/Peregrinos.jasper"));
    			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, ds.getConnection() );

    			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);

    			File pdfFile = File.createTempFile("reporte", ".pdf");
    			try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
    			    fos.write(pdfBytes);
    			}

                String chromePath = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
                ProcessBuilder processBuilder = new ProcessBuilder(chromePath, pdfFile.getAbsolutePath());

                Process process = processBuilder.start();
                process.waitFor();
                
    				
    			} catch (JRException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}   	
        	
    		carnetPeregrino = carnetService.find(Sesion.getSesion().getId());
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        try {
	            // Creando: Fábrica de Constructores de Documento, Constructor de Documento e Implementación DOM
	            DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
	            DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
	            DOMImplementation implementacion = constructorDocumento.getDOMImplementation();
	
	            // Creando el documento XML y el elemento documento
	            Document documento = implementacion.createDocument(null, "carnet", null);
	            Element carnet = documento.getDocumentElement();
	
	            // Modificando y creando instrucciones de procesamiento
	            documento.setXmlVersion("1.0");
	            ProcessingInstruction ip = documento.createProcessingInstruction("xml-stylesheet", "type=\"text/xml\" href=\"test.xsl\"");
	            documento.insertBefore(ip, carnet);
	
	            Element id, fechaexp, expedidoen, peregrino, nacionalidad, hoy, distanciatotal, paradas, parada, orden, nombre, region, estancias, estancia, fecha, vip;
	            Text idValor, fechaexpValor, expedidoenValor, nombreValor, nacionalidadValor, hoyValor, distanciatotalValor, ordenValor, regionValor, fechaValor, paradaValor;
	
	            // Creando un elemento id
	            id = documento.createElement("id");
	            carnet.appendChild(id);
	            idValor = documento.createTextNode(String.valueOf(carnetPeregrino.getId()));
	            id.appendChild(idValor);
	            
	            // Creando un elemento fechaexp
	            fechaexp = documento.createElement("fechaexp");
	            carnet.appendChild(fechaexp);
	            fechaexpValor = documento.createTextNode(String.valueOf(carnetPeregrino.getFechaexp().format(formato)));
	            fechaexp.appendChild(fechaexpValor);
	            
	            // Creando un elemento expedidoen
	            expedidoen = documento.createElement("expedidoen");
	            carnet.appendChild(expedidoen);
	            expedidoenValor = documento.createTextNode("parada "+String.valueOf(carnetPeregrino.getParadaInicial().getNombre()));
	            expedidoen.appendChild(expedidoenValor);
	            
	            // Creando un elemento peregrino
	            peregrino = documento.createElement("peregrino");
	            carnet.appendChild(peregrino);
	            
	                // Creando un elemento nombre dentro de peregrino
	                nombre = documento.createElement("nombre");
	                peregrino.appendChild(nombre);
	                nombreValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getNombre()));
	                nombre.appendChild(nombreValor);
	                
	                // Creando un elemento nacionalidad dentro de peregrino
	                nacionalidad = documento.createElement("nacionalidad");
	                peregrino.appendChild(nacionalidad);
	                nacionalidadValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getNacionalidad()));
	                nacionalidad.appendChild(nacionalidadValor);
	                
	            // Creando un elemento hoy
	            hoy = documento.createElement("hoy");
	            carnet.appendChild(hoy);
	            hoyValor = documento.createTextNode(String.valueOf(LocalDate.now().format(formato)));
	            hoy.appendChild(hoyValor);
	            
	            // Creando un elemento distanciatotal
	            distanciatotal = documento.createElement("distanciatotal");
	            carnet.appendChild(distanciatotal);
	            distanciatotalValor = documento.createTextNode(String.format("%.1f",carnetPeregrino.getDistancia()));
	            distanciatotal.appendChild(distanciatotalValor);
	            
	            // Creando un elemento paradas
	            paradas = documento.createElement("paradas");
	            carnet.appendChild(paradas);
	            
	                // Creando los elementos parada                              
	                for(int i=0;i<carnetPeregrino.getPropietario().getParadas().size(); i++)
	                {
	                    
	                    // Creando elemento parada dentro de paradas
	                    parada = documento.createElement("parada");
	                    paradas.appendChild(parada);
	                        // Creando elemento orden dentro de parada
	                        orden = documento.createElement("orden");
	                        parada.appendChild(orden);
	                        ordenValor = documento.createTextNode(String.valueOf(i+1));
	                        orden.appendChild(ordenValor);
	                        // Creando elemento nombre dentro de parada
	                        nombre = documento.createElement("nombre");
	                        parada.appendChild(nombre);
	                        nombreValor = documento.createTextNode(carnetPeregrino.getPropietario().getParadas().get(i).getNombre());
	                        nombre.appendChild(nombreValor);
	                        // Creando elemento region dentro de parada
	                        region = documento.createElement("region");
	                        parada.appendChild(region);
	                        regionValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getParadas().get(i).getRegion()));
	                        region.appendChild(regionValor);
	                }
	            
	            // Creando un elemento estancias
	            estancias = documento.createElement("estancias");           
	            carnet.appendChild(estancias);
	            
	                // Creando los elemento estancia
	                for(int i=0;i<carnetPeregrino.getPropietario().getEstancias().size(); i++)
	                {
	                    
	                    // Creando elemento estancia dentro de estancias
	                    estancia = documento.createElement("estancia");
	                    estancias.appendChild(estancia);
	                        // Creando elemento id dentro de estancia
	                        id = documento.createElement("id");
	                        estancia.appendChild(id);
	                        idValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getEstancias().get(i).getId()));
	                        id.appendChild(idValor);
	                        // Creando elemento fecha dentro de estancia
	                        fecha = documento.createElement("fecha");
	                        estancia.appendChild(fecha);
	                        fechaValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getEstancias().get(i).getFecha().format(formato)));
	                        fecha.appendChild(fechaValor);
	                        // Creando elemento parada dentro de estancia
	                        parada = documento.createElement("parada");
	                        estancia.appendChild(parada);
	                        paradaValor = documento.createTextNode(String.valueOf(carnetPeregrino.getPropietario().getEstancias().get(i).getParada().getNombre()));
	                        parada.appendChild(paradaValor);
	                        // Creando elemento vip dentro de estancia
	                        if(carnetPeregrino.getPropietario().getEstancias().get(i).isVip())
	                        {
	                            vip = documento.createElement("vip");
	                            estancia.appendChild(vip);
	                        }
	                        
	                }
	             
	                
	            Source fuente = new DOMSource(documento);
	            File fichero = new File("src/main/resources/Carnets/"+carnetPeregrino.getPropietario().getNombre()+"_carnet.xml");
	            Result resultado = new StreamResult(fichero);
	            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
	            Transformer transformador = fabricaTransformador.newTransformer();
	            transformador.transform(fuente, resultado);
	        } catch (ParserConfigurationException ex) {
	            System.out.println("Error: " + ex.getMessage());
	        } catch (TransformerConfigurationException ex) {
	            System.out.println("Error: " + ex.getMessage());
	        } catch (TransformerException ex) {
	            System.out.println("Error: " + ex.getMessage());
	        }
		}
	}
	
	/*
	 * Metodo para entrar al menu de edicion de datos del peregrino
	 */
	
	public void clickEditar() {
		
		panelPrincipal.setVisible(false);
		panelEditar.setVisible(true);	
		panelAyuda.setVisible(false);
		btnVer.setVisible(true);
		
		txtNombre.setText(peregrino.getNombre());
		txtUsuario.setText(usuario.getUsuario());
		ptxtContraseña.setText(usuario.getContraseña());
		txtCorreo.setText(usuario.getCorreo());
		cboxNacionalidad.setValue(Utils.getKeyByValue(Utils.getDiccionarioPaises(), peregrino.getNacionalidad()));
	}
	
	/*
	 * Metodo para mostrar o esconder la contraseña
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
	 * Metodo para volver al menu principal
	 */
	
	public void clickCancelar() {
		
		panelPrincipal.setVisible(true);
		panelEditar.setVisible(false);
		panelAyuda.setVisible(false);
		btnVer.setVisible(false);
		
		txtNombre.clear();
		txtUsuario.clear();
		ptxtContraseña.clear();
		txtCorreo.clear();
		cboxNacionalidad.valueProperty().set(null);
		
	}
	
	/*
	 * Metodo para editar los datos del peregrino
	 */
	
	public void clickConfirmar() {
		
		if(Utils.confirmarDatos().getResult().equals(ButtonType.OK)) {
			if(Utils.validarNombre(txtNombre.getText())
				&& usuarioExistente(txtUsuario.getText())
				&& Utils.validarContraseña(ptxtContraseña.getText())
				&& Utils.validarEmail(txtCorreo.getText())
				&& validarNacionalidad()
				) {

				peregrino.setNombre(txtNombre.getText());
				peregrino.setNacionalidad(Utils.getDiccionarioPaises().get(cboxNacionalidad.getValue()));
				
				peregrino = peregrinoService.save(peregrino);			
				
				usuario.setUsuario(txtUsuario.getText());
				usuario.setContraseña(ptxtContraseña.getText());
				usuario.setCorreo(txtCorreo.getText());
				
				usuario = usuarioService.save(usuario);
				
				Sesion.getSesion().setUsuario(usuario.getUsuario());

				txtNombre.clear();
				txtUsuario.clear();
				ptxtContraseña.clear();
				txtCorreo.clear();
				cboxNacionalidad.valueProperty().set(null);
				
				alertaConfirmacion();
				
				panelPrincipal.setVisible(true);
				panelEditar.setVisible(false);	
				panelAyuda.setVisible(false);
				btnVer.setVisible(false);
			}
		}
		

	}
	
	/*
	 * Metodo para cerrar sesion
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
	 * Metodo para abrir el menu de ayuda
	 */
	
	public void clickAyuda() {
		panelPrincipal.setVisible(false);
		panelEditar.setVisible(false);	
		panelAyuda.setVisible(true);
		btnVer.setVisible(false);
	}
	
	/*
	 * Metodo para salir de la aplicacion
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
	 * Metodo para validar nacionalidad
	 */
	
	private boolean validarNacionalidad() {
		if(cboxNacionalidad.getValue() == null) {
			alertaNacionalidadVacia();
			return false;
		}
		else{
			return true;
		}
	}
	
	/*
	 * Alerta para nacionalidad vacia
	 */
	
	private void alertaNacionalidadVacia() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Nacionalidad vacia");
		alerta.setContentText("Escoge nacionalidad");
		alerta.show();
	}
	
	/*
	 * Alerta para usuario existente
	 */
	
	private boolean usuarioExistente(String usuario) {
	
		if(usuarioSinCambiar) {
			return true;
		}
		
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
	 * Alerta para usuario incorrecto
	 */
	
	private void alertaUsuario() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Usuario existente");
		alerta.setContentText("Ese usuario ya existe");
		alerta.show();
	}
	
	/*
	 * Alerta para confirmar la edicion de datos
	 */
	
	private void alertaConfirmacion() {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Edicion completada");
		alerta.setContentText("Se han editado los datos");
		alerta.show();
	}
	

}
