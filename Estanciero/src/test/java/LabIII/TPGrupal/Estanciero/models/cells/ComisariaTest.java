package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Dado;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cards.CartaHabeasCorpus;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import LabIII.TPGrupal.Estanciero.models.players.JugadorConservador;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComisariaTest {

    @Mock
    private Random random;

    @Mock
    private JugadorNormal jugadorNormalMock;

    @Mock
    private Tablero tableroMock;

    @Mock
    private CartaHabeasCorpus cartaMock;

    @Mock
    private Casilla casillaMock;

    @Spy
    private Jugador jugadorMock;

    @Spy
    private Dado dadoMock;

    @Spy
    private Dado dadoMock2;

    @Spy
    @InjectMocks
    private Comisaria comisariaMock;

    private List<Jugador> jugadores;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Jugador> jugadores = new ArrayList<>();
        comisariaMock = new Comisaria(14, "COMISARIA", TipoCasilla.Comisaria, "Estás de paso por la comisaría", jugadores);
    }

    @AfterEach
    void desHacerMockStatic() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void testAccionJugadorEnComisariaLiberadoPorTurnos() {
        mockStatic(Tablero.class);
        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(tableroMock.getDado1()).thenReturn(dadoMock);
        when(tableroMock.getDado2()).thenReturn(dadoMock2);
        when(tableroMock.getDado1().getValor()).thenReturn(1);
        when(tableroMock.getDado2().getValor()).thenReturn(1);

        List<Jugador> jugadores2 = new ArrayList<>();
        jugadorMock = new JugadorConservador();
        jugadores2.add(jugadorMock);
        comisariaMock.setJugadores(jugadores2);

        List<Casilla> casillas = new ArrayList<>();
        casillas.add(casillaMock);
        when(tableroMock.getCasillas()).thenReturn(casillas);
        doNothing().when(casillaMock).Accion(jugadorMock);

        comisariaMock.Accion(jugadorMock);

        assertFalse(jugadores2.contains(jugadorMock));
    }

    @Test
    void testAccionJugadorEnComisariaNoLiberado() {
        mockStatic(Tablero.class);
        when(Tablero.getPartida()).thenReturn(tableroMock);
        List<Casilla> casillas = new ArrayList<>();
        casillas.add(comisariaMock);
        when(tableroMock.getCasillas()).thenReturn(casillas);

        when(tableroMock.getDado1()).thenReturn(dadoMock);
        when(tableroMock.getDado2()).thenReturn(dadoMock2);
        when(tableroMock.getDado1().getValor()).thenReturn(3);
        when(tableroMock.getDado2().getValor()).thenReturn(5);

        List<Jugador> jugadores3 = new ArrayList<>();
        jugadorMock = new JugadorConservador();
        jugadorMock.setTurnoEstado(1);
        jugadores3.add(jugadorMock);
        comisariaMock.setJugadores(jugadores3);

        jugadorMock.setDinero(0);

        comisariaMock.Accion(jugadorMock);

        assertEquals(jugadorMock, jugadores3.get(0));
    }



    @Test
    void testEncerrarJugador() {
        mockStatic(Tablero.class);
        when(Tablero.getPartida()).thenReturn(tableroMock);

        jugadorMock.setEstado(Estado.JUGANDO);
        jugadorMock.setDinero(0);

        comisariaMock.Encerrar(jugadorMock);

        assertEquals(Estado.COMISARIA, jugadorMock.getEstado());
    }

    @Test
    void testLiberarJugador() {
        Jugador jugadorMock = new JugadorConservador();
        jugadorMock.setEstado(Estado.COMISARIA);
        List<Jugador> jugadores3 = new ArrayList<>();
        jugadorMock = new JugadorConservador();
        jugadorMock.setTurnoEstado(0);
        jugadores3.add(jugadorMock);
        comisariaMock.setJugadores(jugadores3);

        comisariaMock.Liberar(jugadorMock);

        assertEquals(false, jugadores3.contains(jugadorMock));
        assertEquals(Estado.JUGANDO, jugadorMock.getEstado());
    }

    @Test
    void testDescontarTurno() {
        List<Jugador> jugadores3 = new ArrayList<>();
        jugadorMock = new JugadorConservador();
        jugadorMock.setTurnoEstado(1);
        jugadores3.add(jugadorMock);
        comisariaMock.setJugadores(jugadores3);

        comisariaMock.DescontarTurno(jugadorMock);

        assertEquals(jugadorMock, jugadores3.get(0));
    }

    @Test
    void testConsultarPermanenciaPagarFianza() {
        mockStatic(Tablero.class);
        mockStatic(consola.class);

        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(jugadorNormalMock.getDinero()).thenReturn(1500);

        when(consola.ConsultarMenu(anyString(), any(String[].class))).thenReturn(1);

        boolean resultado = comisariaMock.ConsultarPermanencia(jugadorNormalMock);

        assertFalse(resultado);
    }

    @Test
    void testConsultarPermanenciaEsperar() {
        mockStatic(consola.class);

        List<Jugador> jugadores3 = new ArrayList<>();
        jugadorMock = new JugadorConservador();
        jugadorMock.setTurnoEstado(1);
        jugadores3.add(jugadorMock);
        comisariaMock.setJugadores(jugadores3);

        boolean resultado = comisariaMock.ConsultarPermanencia(jugadorMock);

        assertTrue(resultado);
    }

    @Test
    void testJugadorDespistado(){
        mockStatic(Tablero.class);
        mockStatic(consola.class);
        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(jugadorNormalMock.getDinero()).thenReturn(500);

        when(consola.ConsultarMenu(anyString(), any(String[].class))).thenReturn(1).thenReturn(2).thenReturn(3);

        boolean resultado = comisariaMock.ConsultarPermanencia(jugadorNormalMock);

        assertTrue(resultado);
    }
}
