package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class CartaCobrarJugadoresTest {

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carta = new CartaCobrarJugadores();
        carta.setValor(1000);
    }

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Jugador jugadorMock1;

    @Mock
    private Jugador jugadorMock2;

    @Mock
    private Jugador jugadorMock3;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private CartaCobrarJugadores carta;

    @Test
    void usarCarta() {

        //given
        tableroMock = Tablero.getPartida();

        jugadorMock = new JugadorEquilibrado();
        jugadorMock.setDinero(2000);

        jugadorMock1 = new JugadorEquilibrado();
        jugadorMock1.setDinero(3000);

        jugadorMock2 = new JugadorEquilibrado();
        jugadorMock2.setDinero(3000);

        jugadorMock3 = new JugadorEquilibrado();
        jugadorMock3.setDinero(3000);

        tableroMock.getJugadores().addAll(Arrays.asList(jugadorMock, jugadorMock1, jugadorMock2, jugadorMock3));

        //when
        carta.UsarCarta(jugadorMock);

        //then
        assertTrue(jugadorMock.getDinero() == 5000);

        System.out.println(jugadorMock.getDinero());
    }
}