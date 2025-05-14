package LabIII.TPGrupal.Estanciero.controllers;

import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.services.impl.CasillaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CasillaControllerTest {

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    CasillaServiceImpl casillaServiceMock;

    @Mock
    Casilla casillaMock;

    @InjectMocks
    CasillaController casillaController;

    @Test
    void listarCasillas() {
        List<Casilla> casillas = new ArrayList<>();
        casillas.add(casillaMock);

        when(casillaServiceMock.GetAllCasillasBase()).thenReturn(casillas);

        List<Casilla> resultadoEsperado = casillas;
        List<Casilla> resultadoActual = casillaController.listarCasillas().getBody();

        assertEquals(resultadoEsperado, resultadoActual);
    }
}