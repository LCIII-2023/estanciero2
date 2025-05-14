package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

class CartaAvanzarHastaTest {

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);
    }


    @BeforeEach
    void beforeEach(TestInfo test) {
        MockitoAnnotations.openMocks(this);

        carta = new CartaAvanzarHasta();
        carta.setValor(1);//indicamos la cantidad de casillas que va a avanzar el jugador

        System.out.println(test.getTestMethod().get().getName() + " - " + test.getDisplayName());
    }

    @Mock
    private Tablero tableroMock;

    @Mock
    private Casilla casillaMock;

    @Spy
    @InjectMocks
    private CartaAvanzarHasta carta;

    @ParameterizedTest(name = "Test {index}")
    @MethodSource("ParametrosCarta")//de donde vienen los parametros
    @DisplayName("UsarCarta() en CartaAvanzarHasta")//
    void testUsarCarta(Jugador jugador) {

        //Instancias
        tableroMock = Tablero.getPartida();
        tableroMock.getCasillas().addAll(Arrays.asList(casillaMock, casillaMock, casillaMock));

        doNothing().when(casillaMock).Accion(jugador);//el metodo accion no va a hacer nada


        //Resultados
        carta.UsarCarta(jugador);
        int posicionObtenida = jugador.getPosicion();
        int posicionEsperada = carta.getValor();


        //Comprobaciones
        System.out.println(posicionEsperada == posicionObtenida ? ("Existoso") : ("Posicion Esperada: " + posicionEsperada + " - Posicion Obtenida: " + posicionObtenida));

        assertEquals(posicionEsperada, posicionObtenida);
    }

    private static Stream<Arguments> ParametrosCarta(){
        Jugador jugador1 = new JugadorEquilibrado();
        Jugador jugador2 = new JugadorEquilibrado();
        Jugador jugador3 = new JugadorEquilibrado();
        Jugador jugador4 = new JugadorEquilibrado();

        jugador1.setPosicion(0);
        jugador2.setPosicion(3);
        jugador3.setPosicion(-50);
        jugador4.setPosicion(50);

        Stream<Arguments> parametro = Stream.of(
                Arguments.of(jugador1),
                Arguments.of(jugador2),
                Arguments.of(jugador3),
                Arguments.of(jugador4)
        );

        return parametro;

    }
}