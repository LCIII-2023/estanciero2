package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Comisaria;
import LabIII.TPGrupal.Estanciero.models.players.JugadorConservador;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class CartaHabeasCorpusTest {

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Jugador jugadorMock2;

    @Mock
    private Comisaria comisariaMock;

    @Mock
    private Tablero tableroMock;

    @Mock
    private Casilla casillaMock;

    private List<Jugador> jugadores = new ArrayList<>();


    @Spy
    @InjectMocks
    private CartaHabeasCorpus cartaHabeasCorpusMock;

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tableroMock = Tablero.getPartida();

        for (int i = 0; i < 15; i++) {

            if(i == 14){

                tableroMock.getCasillas().add(comisariaMock);
            }

            tableroMock.getCasillas().add(casillaMock);
        }

        when(comisariaMock.getJugadores()).thenReturn(jugadores);
    }

    @Test
    void usarCartaEnComisaria() {
        // Given
        jugadores.add(jugadorMock);

        // When
        boolean result = cartaHabeasCorpusMock.UsarCarta(jugadorMock);

        // Then
        assertTrue(result);
    }

}