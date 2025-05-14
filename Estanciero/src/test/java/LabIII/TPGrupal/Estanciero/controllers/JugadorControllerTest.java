package LabIII.TPGrupal.Estanciero.controllers;

import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.services.impl.CasillaServiceImpl;
import LabIII.TPGrupal.Estanciero.services.impl.JugadorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JugadorControllerTest {

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    JugadorServiceImpl jugadorServiceMock;

    @Mock
    Jugador jugadorMock;

    @InjectMocks
    JugadorController jugadorController;

    @Test
    void listarProvinciasP() {
        List<String> provinciasP = new ArrayList<>();
        provinciasP.add(String.valueOf(jugadorMock));

        when(jugadorServiceMock.GetProvinciasPByTipo("1")).thenReturn(provinciasP);

        when(jugadorServiceMock.GetProvinciasPByTipo("2")).thenReturn(provinciasP);

        List<String> resultadoEsperado = provinciasP;
        List<String> resultadoActual = jugadorController.listarProvinciasP("1").getBody();

        assertEquals(resultadoEsperado, resultadoActual);
    }
}