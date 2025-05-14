package LabIII.TPGrupal.Estanciero.controllers;

import LabIII.TPGrupal.Estanciero.models.Carta;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.services.impl.CartaServiceImpl;
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

class CartaControllerTest {

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    CartaServiceImpl cartaServiceMock;

    @Mock
    Carta cartaMock;

    @InjectMocks
    CartaController cartaController;
    @Test
    void listarCartas() {
        List<Carta> cartas = new ArrayList<>();
        cartas.add(cartaMock);

        when(cartaServiceMock.GetAllCartasBase()).thenReturn(cartas);

        List<Carta> resultadoEsperado = cartas;
        List<Carta> resultadoActual = cartaController.listarCartas().getBody();

        assertEquals(resultadoEsperado, resultadoActual);
    }
}