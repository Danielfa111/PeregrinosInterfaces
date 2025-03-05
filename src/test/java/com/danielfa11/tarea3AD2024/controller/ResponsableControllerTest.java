package com.danielfa11.tarea3AD2024.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.danielfa11.tarea3AD2024.modelo.Estancia;
import com.danielfa11.tarea3AD2024.modelo.Parada;
import com.danielfa11.tarea3AD2024.services.EstanciaService;
import com.danielfa11.tarea3AD2024.services.ParadaService;
import com.danielfa11.tarea3AD2024.utils.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

@ExtendWith(MockitoExtension.class)
class ResponsableControllerTest {

    @InjectMocks
    private ResponsableController responsableController;
    
    @Mock
    private EstanciaService estanciaService;
    
    @Mock
    private ParadaService paradaService;
    
    @Mock
    private Alert alert;
    
    
    private Estancia estancia;
    private Parada parada;
    
    @BeforeEach
    void setUp() {
        estancia = new Estancia();
        estancia.setFecha(LocalDate.now());
        
        parada = new Parada();
        parada.setId(1L);
        parada.setNombre("Parada 1");
        
        when(estanciaService.save(any(Estancia.class))).thenReturn(estancia);
        when(paradaService.save(any(Parada.class))).thenReturn(parada);
        when(alert.getResult().equals(ButtonType.OK)).thenReturn(true);
    }
    
    @Test
    void testClickSellar() {
        responsableController.clickSellar();
        verify(estanciaService, times(1)).save(any(Estancia.class));
        verify(paradaService, times(1)).save(any(Parada.class));
    }
    
    @Test
    void testClickExportar() {
        responsableController.clickExportar();
        verify(estanciaService, times(1)).findByFechaBetweenAndParada(any(LocalDate.class), any(LocalDate.class), any(Parada.class));
    }
    
    @Test
    void testResponsableValidation() {
        assertNotNull(estancia.getFecha());
    }
}


