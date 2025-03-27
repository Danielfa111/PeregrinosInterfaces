package com.danielfa11.tarea3AD2024.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.danielfa11.tarea3AD2024.config.StageManager;
import com.danielfa11.tarea3AD2024.modelo.Estancia;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.modelo.Sesion;
import com.danielfa11.tarea3AD2024.services.EstanciaService;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;
import com.danielfa11.tarea3AD2024.utils.Utils;
import com.danielfa11.tarea3AD2024.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

@Controller
public class ResponsableController implements Initializable{

	@FXML
	private Pane panelAyuda;
	
	@FXML
	private WebView webView;
	
	@FXML
	private Label lblBienvenido;
	
	@FXML
	private GridPane panelPrincipal;
	
	@FXML
	private GridPane panelSellar;
	
	@FXML
	private Label lblNombreParada;
		
	@FXML
	private Label lblRegionParada;
	
	@FXML
	private Label lblResponsableParada;
	
	@FXML
	private Label lblDatosPeregrino;
	
	@FXML
	private Label lblPeregrinoNombre;
	
	@FXML
	private Label lblPeregrinoNacionalidad;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private CheckBox ckEstancia;
	
	@FXML
	private Label lblCkVip;
	
	@FXML
	private CheckBox ckVip;
	
	@FXML
	private GridPane panelExportar;
	
	@FXML
	private Label lblNombreParadaExportar;
	
	@FXML
	private Label lblRegionParadaExportar;
	
	@FXML
	private Label lblResponsableParadaExportar;
	
	@FXML
	private DatePicker dpFecha1;
	
	@FXML
	private DatePicker dpFecha2;
		
	@FXML
    private TableView<Estancia> tablaEditar;
        
        @FXML
        private TableColumn<Estancia, Long> columnaID = new TableColumn<>("Id");
        
        @FXML
        private TableColumn<Estancia, LocalDate> columnaFecha = new TableColumn<>("Fecha");

        @FXML
        private TableColumn<Estancia, Boolean> columnaVip = new TableColumn<>("Vip");
        
        @FXML
        private TableColumn<Estancia, String> columnaPeregrino = new TableColumn<>("Peregrino");
        
        @FXML
        private TableColumn<Estancia, String> columnaParada = new TableColumn<>("Parada");
        
	@Autowired
	private EstanciaService estanciaService;

	@Autowired
	private ParadaService paradaService;
	
	@Autowired
	private PeregrinoService peregrinoService;	

	@Lazy
    @Autowired
    private StageManager stageManager;
	
	private Parada parada;	
	
	private List<Estancia> estancias = new ArrayList<>(); 
	
	private ObservableList<Estancia> estanciasTabla = FXCollections.observableArrayList(estancias);
	
	/*
	 * Metodo para cargar datos al iniciar la vista
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		String url = getClass().getResource("/help/menuAyuda.html").toExternalForm();
		webView.getEngine().load(url);
		
		lblBienvenido.setText("Bienvenido/a, "+Sesion.getSesion().getUsuario());
		
		parada = paradaService.find(Sesion.getSesion().getId());	
		
		// Sellar Parada
		
		lblNombreParada.setText("Nombre: "+parada.getNombre() );
		lblRegionParada.setText("Region: "+parada.getRegion() );
		lblResponsableParada.setText("Responsable: "+parada.getResponsable() );
		
		// Exportar Parada
		
		lblNombreParadaExportar.setText("Nombre: "+parada.getNombre() );
		lblRegionParadaExportar.setText("Region: "+parada.getRegion() );
		lblResponsableParadaExportar.setText("Responsable: "+parada.getResponsable() );
		
		tablaEditar.setItems(estanciasTabla);
		
		columnaID.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		columnaVip.setCellValueFactory(new PropertyValueFactory<>("vip"));
		columnaPeregrino.setCellValueFactory(cellData ->
			new SimpleStringProperty(cellData.getValue().getPeregrino().getNombre())		
		);
		
		columnaParada.setCellValueFactory(cellData ->
			new SimpleStringProperty(cellData.getValue().getParada().getNombre())
		);
		
		txtId.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.isBlank()) {
				lblDatosPeregrino.setVisible(false);
				lblPeregrinoNombre.setVisible(false);
				lblPeregrinoNacionalidad.setVisible(false);
			}
			else if(peregrinoService.existsById(Long.valueOf(newValue))){
				
				Peregrino peregrino = peregrinoService.find(Long.valueOf(txtId.getText()));	
				
				lblPeregrinoNombre.setText("Nombre: "+peregrino.getNombre());
				lblPeregrinoNacionalidad.setText("Nacionalidad: "+peregrino.getNacionalidad());			
				
				lblDatosPeregrino.setVisible(true);
				lblPeregrinoNombre.setVisible(true);
				lblPeregrinoNacionalidad.setVisible(true);
				
				
			}
			else {
				lblDatosPeregrino.setVisible(false);
				lblPeregrinoNombre.setVisible(false);
				lblPeregrinoNacionalidad.setVisible(false);
			}
			
		});
		
		ckEstancia.selectedProperty().addListener((observable, oldValue, newValue) -> {
		
			if(ckEstancia.isSelected()) {
				ckVip.setVisible(true);
				lblCkVip.setVisible(true);
			}
			else {
				ckVip.setVisible(false);
				lblCkVip.setVisible(false);
			}
			
		});
		
	}
	
	/*
	 * Metodo para sellar a un peregrino en la parada
	 */

	public void clickSellar() {
		
		if(Utils.confirmarDatos().getResult().equals(ButtonType.OK)) {
			if(txtId.getText().isBlank()) {
				alertaIdVacio();
			}
			else if(!validarId(txtId.getText())){
				alertaIdIncorrecto();
			}
			else if(!peregrinoService.existsById(Long.valueOf(txtId.getText()))) {
				alertaIdInexistente();
			}
			else if(peregrinoService.existsById(Long.valueOf(txtId.getText()))) {
				
				Peregrino peregrino = peregrinoService.find(Long.valueOf(txtId.getText()));					
				
				boolean peregrinoYaSellado = false;
				
				for(Peregrino p : parada.getPeregrinos()) {
					if(p.getId().equals(peregrino.getId())) {
						peregrinoYaSellado = true;
					}
				}				
				
				if(peregrinoYaSellado){						
					alertaPeregrino();
					}
				else {					
			        
					peregrino.getCarnet().setDistancia(peregrino.getCarnet().getDistancia()+5);		
					peregrino.getParadas().add(parada);							
					peregrinoService.save(peregrino);					
					parada.getPeregrinos().add(peregrino);
					paradaService.save(parada);	
					
					
					
					if(ckEstancia.isSelected()) {
						
						Estancia estancia = new Estancia();
						
						if(ckVip.isSelected()) {
							peregrino.getCarnet().setNvips(peregrino.getCarnet().getNvips()+1);

							estancia.setVip(true);
						}					
			
						estancia.setFecha(LocalDate.now());						
						estancia.setParada(parada);
						estancia.setPeregrino(peregrino);

						peregrino.getEstancias().add(estancia);
						peregrinoService.save(peregrino);

					}
					
					alertaConfirmacion();
				}
			}
		}
	}
	
	/*
	 * Metodo para exportar las estancias de una parada entre dos fechas
	 */

	
	public void clickExportar() {
		
		if(Utils.confirmarDatos().getResult().equals(ButtonType.OK)) {
			
			estanciasTabla.clear();
			
			if(dpFecha1.getValue() == null || dpFecha2.getValue() == null){
				alertaFechasVacias();
			}		
			else if(dpFecha1.getValue().isAfter(LocalDate.now()) || dpFecha2.getValue().isAfter(LocalDate.now())){
				alertaFechasFuturas();
			}
			else if(dpFecha1.getValue().isAfter(dpFecha2.getValue())) {
				alertaFechasIncorrectas();
			}
			else if(dpFecha1.getValue().isBefore(dpFecha2.getValue())){

				estanciasTabla.addAll(estanciaService.findByFechaBetweenAndParada(dpFecha1.getValue(), dpFecha2.getValue(), parada));
				
			
				dpFecha1.setValue(null);
				dpFecha2.setValue(null);
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		        alert.setTitle("Atención");
		        alert.setContentText("¿Quiere volver a exportar estancias?");
		        alert.showAndWait();
		        
		        if(!alert.getResult().equals(ButtonType.OK)) {
		        	panelPrincipal.setVisible(true);
		        	panelSellar.setVisible(false);
		    		panelExportar.setVisible(false);		
		    		panelAyuda.setVisible(false);
		        }
			}
		}

	}
	
	/*
	 * Metodo para cargar el menu de exportacion
	 */
	
	public void clickMenuExportar() {
		
		panelExportar.setVisible(true);
		panelPrincipal.setVisible(false);
		panelSellar.setVisible(false);
		panelAyuda.setVisible(false);
	}
	
	/*
	 * Metodo para cargar el menu de sellado
	 */
	
	public void clickMenuSellar() {
		
		panelSellar.setVisible(true);
		panelExportar.setVisible(false);
		panelPrincipal.setVisible(false);
		panelAyuda.setVisible(false);
		
	}
	
	/*
	 * Metodo para cargar el menu de ayuda
	 */
	
	public void clickAyuda() {
		
		panelSellar.setVisible(false);
		panelExportar.setVisible(false);
		panelPrincipal.setVisible(false);
		panelAyuda.setVisible(true);
		
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
	 * Metodo para validar el id
	 */
	
	private boolean validarId(String text) {

        String regex = "^[1-9]\\d*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if(matcher.matches()) {
        	return true;
        }
        else{
        	return false;
        }
    }
	
	/*
	 *  Alerta para id vacio
	 */
	
	private void alertaIdVacio() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Id vacio");
		alerta.setContentText("Introduce el id del peregrino");
		alerta.show();
	}
	
	/*
	 *  Alerta para id incorrecto
	 */
	
	private void alertaIdIncorrecto() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Id incorrecto");
		alerta.setContentText("Introduce un numero");
		alerta.show();
	}
	
	/*
	 *  Alerta para id inexistente
	 */
	
	private void alertaIdInexistente() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Id incorrecto");
		alerta.setContentText("No existe peregrino relacionado con ese id");
		alerta.show();
	}
	
	/*
	 *  Alerta para peregrino existente
	 */
	
	private void alertaPeregrino() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Peregrino ya existente");
		alerta.setContentText("El peregrino ya ha sellado en esta parada");
		alerta.show();
	}
	
	/*
	 *  Alerta para fechas vacias
	 */
	
	private void alertaFechasVacias() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Fechas vacias");
		alerta.setContentText("Escoge las fechas");
		alerta.show();
	}
	
	/*
	 *  Alerta para fechas imposible
	 */
	
	private void alertaFechasFuturas() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Fechas incorrectas");
		alerta.setContentText("Una de las fechas es un dia despues del actual");
		alerta.show();
	}
	
	/*
	 *  Alerta para rango de fechas imposible
	 */

	private void alertaFechasIncorrectas() {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Fechas incorrectas");
		alerta.setContentText("La primera fecha es despues de la segunda fecha");
		alerta.show();
	}
	
	/*
	 *  Alerta para confirmar el sellado del peregrino
	 */
	
	private void alertaConfirmacion() {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Peregrino sellado");
		alerta.setContentText("Se ha sellado al peregrino con exito");
		alerta.show();
	}
}
