package com.danielfa11.tarea3AD2024.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.danielfa11.tarea3AD2024.modelo.Carnet;
import com.danielfa11.tarea3AD2024.modelo.Estancia;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.modelo.Peregrino;
import com.danielfa11.tarea3AD2024.services.EstanciaService;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.services.PeregrinoService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ResponsableControllerTest {

    @InjectMocks
    private ResponsableController responsableController;
    
    @Mock
	private Pane panelAyuda;
	
    @Mock
	private WebView webView;
	
    @Mock
	private Label lblBienvenido;
	
    @Mock
	private GridPane panelPrincipal;
	
    @Mock
	private GridPane panelSellar;
	
    @Mock
	private Label lblNombreParada;
		
    @Mock
	private Label lblRegionParada;
	
    @Mock
	private Label lblResponsableParada;
	
    @Mock
	private Label lblDatosPeregrino;
	
    @Mock
	private Label lblPeregrinoNombre;
	
    @Mock
	private Label lblPeregrinoNacionalidad;
	
    @Mock
	private TextField txtId;
	
    @Mock
	private CheckBox ckEstancia;
	
    @Mock
	private Label lblCkVip;
	
    @Mock
	private CheckBox ckVip;
	
    @Mock
	private GridPane panelExportar;
	
    @Mock
	private Label lblNombreParadaExportar;
	
    @Mock
	private Label lblRegionParadaExportar;
	
    @Mock
	private Label lblResponsableParadaExportar;
	
    @Mock
	private DatePicker dpFecha1;
	
    @Mock
	private DatePicker dpFecha2;
		
    @Mock
    private TableView<Estancia> tablaEditar;
        
    	@Mock
        private TableColumn<Estancia, Long> columnaID = new TableColumn<>("Id");
        
    	@Mock
        private TableColumn<Estancia, LocalDate> columnaFecha = new TableColumn<>("Fecha");

    	@Mock
        private TableColumn<Estancia, Boolean> columnaVip = new TableColumn<>("Vip");
        
    	@Mock
        private TableColumn<Estancia, String> columnaPeregrino = new TableColumn<>("Peregrino");
        
    	@Mock
        private TableColumn<Estancia, String> columnaParada = new TableColumn<>("Parada");
        
    @Mock
	private EstanciaService estanciaService;

    @Mock
	private ParadaService paradaService;
	
    @Mock
	private PeregrinoService peregrinoService;	

	@Mock
    private StageManager stageManager;
	
	@Mock
	private Alert alert;
	
	@Mock
	private Parada parada;	
	
	@Mock
	private List<Estancia> estancias = new ArrayList<>(); 
	
	@Mock
	private ObservableList<Estancia> estanciasTabla = FXCollections.observableArrayList(estancias);
    
	@Mock
    private Estancia estancia;
	
	@Mock
	private Peregrino peregrino = new Peregrino();
    
    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {}); 
    }
    
    @BeforeEach
    void setUp() {
    	
    	when(alert.showAndWait()).thenReturn(Optional.of(ButtonType.OK));

        // Configurar el objeto Peregrino simulado
        peregrino = new Peregrino();
        peregrino.setId(1L);
        Carnet carnet = new Carnet();
        carnet.setDistancia(10.0);
        carnet.setNvips(0);
        peregrino.setCarnet(carnet);
        peregrino.setParadas(new ArrayList<>());
        peregrino.setEstancias(new ArrayList<>());

        // Configurar el objeto Parada simulado
        parada = new Parada();
        parada.setId(1L);
        parada.setPeregrinos(new ArrayList<>());

        // Configurar los mocks de los servicios
        when(peregrinoService.existsById(any(Long.class))).thenReturn(true);
        when(peregrinoService.find(any(Long.class))).thenReturn(peregrino);
        when(paradaService.save(any(Parada.class))).thenReturn(parada);
        when(peregrinoService.save(any(Peregrino.class))).thenReturn(peregrino);
       
    }
    
    @Test
    public void testSellarSuccess() {
    	
    	when(txtId.getText()).thenReturn("1");
    	
    	when(ckEstancia.isSelected()).thenReturn(false);

    	CountDownLatch latch = new CountDownLatch(1);

	    Platform.runLater(() -> {
	    	responsableController.clickSellar();    
	        latch.countDown();
	    });

	    try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		verify(peregrinoService, times(1)).save(any(Peregrino.class));
        verify(paradaService, times(1)).save(any(Parada.class));
	}
    
    @Test
    public void testSellarError() {
    	when(txtId.getText()).thenReturn(null);
    	
		Platform.runLater(() -> {
			responsableController.clickSellar();        
		});		
		
		verify(paradaService, never()).save(any(Parada.class));
        verify(peregrinoService, never()).save(any(Peregrino.class));
	}
    
    
}


