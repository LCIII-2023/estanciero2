package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.models.cells.EstacionamientoLibre;
import LabIII.TPGrupal.Estanciero.models.enums.Color;
import LabIII.TPGrupal.Estanciero.models.enums.Forma;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;


class CasillaTest {

    @Mock
    Tablero tableroMock;

    @Mock
    JugadorNormal jugadorMock;

    @Spy
    @InjectMocks
    EstacionamientoLibre estacionamiento;

    @BeforeAll
    static void beforeAll(){
        mockStatic(Tablero.class);

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void mostrarPeones() {

        List<Casilla> casillas = new ArrayList<>();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorMock);
        casillas.add(estacionamiento);
        Peon p = new Peon(Color.ROJO, Forma.CIRCULO);
        when(jugadorMock.getPeon()).thenReturn(p);
        when(Tablero.getPartida()).thenReturn(tableroMock);
        when(tableroMock.getCasillas()).thenReturn(casillas);
        when(tableroMock.getJugadores()).thenReturn(jugadores);
        String h = estacionamiento.MostrarPeones(1);
        assertEquals("\u001B[31m●\u001B[0m ", h);

    }

}
