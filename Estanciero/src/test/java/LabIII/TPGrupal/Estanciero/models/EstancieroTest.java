package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.cards.CartaHabeasCorpus;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EstancieroTest {

    @Mock
    RestTemplate restTemplateMock;

    @Mock
    Tablero tableroMock;

    @Mock
    Estanciero estancieroMock2;

    @Mock
    Dado mockDado1;

    @Mock
    Dado mockDado2;

    @Spy
    @InjectMocks
    Estanciero estancieroMock;

    @BeforeAll
    static void setupAll() {
        mockStatic(consola.class);
        mockStatic(Tablero.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosCrearTablero")
    @DisplayName("Crear Tablero")
    void crearTableroOpt12(int i) {
        when(consola.ConsultarMenu(anyString(), any(String[].class))).thenReturn(i);
        switch (i){
            case 1:
                doNothing().when(estancieroMock).NuevaPartida();
                estancieroMock.CrearTablero();
                verify(estancieroMock).NuevaPartida();
                break;
            case 2:
                doNothing().when(estancieroMock).CargarPartida();
                estancieroMock.CrearTablero();
                verify(estancieroMock).CargarPartida();
                break;
        }

    }
    private static Stream<Arguments> parametrosCrearTablero() {
        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(1),
                Arguments.of(2)
        );
        return parametros;
    }


    @Test
    void nuevaPartida() {


    }

    @Test
    void cargarPartida() {

    }

    @Test
    void guardarPartida() {

    }

    @Test
    void seleccionarDificultadTest() {

    }


    @Test
    void agregarJugador() {

    }

    @Test
    void ordenarJugadores() {
        // Crear jugadores de prueba
        Jugador jugador1 = new JugadorNormal();
        jugador1.setNombre("Jugador1");
        Jugador jugador2 = new JugadorNormal();
        jugador2.setNombre("Jugador2");
        Jugador jugador3 = new JugadorNormal();
        jugador3.setNombre("Jugador3");
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        when(tableroMock.getDado1()).thenReturn(mockDado1);
        when(tableroMock.getDado2()).thenReturn(mockDado2);

        when(mockDado1.TirarDado()).thenReturn(3).thenReturn(5).thenReturn(2);
        when(mockDado2.TirarDado()).thenReturn(2).thenReturn(4).thenReturn(6);

        List<Jugador> resultado = estancieroMock.OrdenarJugadores(jugadores);

        assertEquals(jugador1, resultado.get(2));
        assertEquals(jugador2, resultado.get(0));
        assertEquals(jugador3, resultado.get(1));

    }
}