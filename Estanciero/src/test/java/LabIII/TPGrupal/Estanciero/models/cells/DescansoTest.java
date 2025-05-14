package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

class DescansoTest {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Jugador jugador4;
    private final InputStream entrada = System.in;
    private ByteArrayInputStream testIn;

    @Mock
    private Tablero tableroMock;

    @Mock
    private Casilla casillaMock;

    @Spy
    @InjectMocks
    Descanso descansospy;

    private  final InputStream systemIn=System.in;
    private final PrintStream systemOut=System.out;


    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        descansospy=new Descanso();
        tableroMock= Tablero.getPartida();
        jugador1 = new JugadorEquilibrado();
        jugador1.setEstado(Estado.JUGANDO);
        jugador2 = new JugadorEquilibrado();
        jugador2.setEstado(Estado.DESCANSO);
        jugador3 = new JugadorEquilibrado();
        jugador3.setEstado(Estado.JUGANDO);
        jugador4 = new JugadorEquilibrado();
        jugador4.setEstado(Estado.JUGANDO);
        tableroMock.getJugadores().addAll(Arrays.asList(jugador1, jugador2, jugador3, jugador4));


    }
    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

    @Test
    void accionTest() {

        for (int i = 0; i < 8; i++) {
            tableroMock.getCasillas().addAll(Arrays.asList(casillaMock, casillaMock, casillaMock, casillaMock, casillaMock));
        }
        doNothing().when(casillaMock).Accion(jugador2);
        jugador4.setNombre("Pablo");
        jugador4.setPosicion(21);
        //when
        descansospy.Accion(jugador1);
        descansospy.AgregarJugador(jugador4);
        descansospy.DescontarTurno(jugador4);
        descansospy.DescontarTurno(jugador4);
        descansospy.Accion(jugador4);

        assertEquals(Estado.DESCANSO, jugador1.getEstado());

        assertTrue(Estado.JUGANDO != jugador2.getEstado());
        assertEquals(Estado.JUGANDO, jugador4.getEstado());

    }
    @Test
    void agregarJugadorTest(){
        descansospy.AgregarJugador(jugador4);
        assertEquals(Estado.DESCANSO, jugador4.getEstado());
    }

    @Test
    void soltarJugadorTest(){

        for (int i = 0; i < 8; i++) {
            tableroMock.getCasillas().addAll(Arrays.asList(casillaMock, casillaMock, casillaMock, casillaMock, casillaMock));
        }
        doNothing().when(casillaMock).Accion(jugador2);
        jugador2.setNombre("Lety");
        jugador2.setPosicion(21);
        descansospy.SoltarJugador(jugador2);
        assertEquals(Estado.JUGANDO, jugador2.getEstado());
    }

    @Test
    void  descontarTurnoTest(){
        descansospy.AgregarJugador(jugador3);
        int resultado = 1;
        descansospy.DescontarTurno(jugador3);

    }
    //@Disabled
    @Test
    void consultarPermanenciaTest() {

        provideInput("1\n"); // Simular entrada de consola antes de llamar al método

        Jugador j = new JugadorNormal();
        descansospy.AgregarJugador(j);

        boolean resultado = descansospy.ConsultarPermanencia(j);

        assertFalse(resultado);

    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn (testIn);
    }
}