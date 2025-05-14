package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

class CartaMoverCantidadTest {
    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }
    //SetUp
    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        carta = new CartaMoverCantidad(1, "PRUEBA", TipoCarta.MoverCantidad , 5);
        tableroMock = Tablero.getPartida();
    }

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Casilla casillaMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private CartaMoverCantidad carta;

    @Test
    void usarCarta() {

        //Instancias
        jugadorMock = new JugadorAgresivo();

        tableroMock = Tablero.getPartida();
        tableroMock.getCasillas().addAll(Arrays.asList(casillaMock, casillaMock, casillaMock, casillaMock, casillaMock));


        //Metodos Simulados
        doNothing().when(casillaMock).Accion(jugadorMock);

        int posicion = jugadorMock.getPosicion() + carta.getValor();
        int posicionEsperada = (posicion % tableroMock.getCasillas().size() + tableroMock.getCasillas().size()) % tableroMock.getCasillas().size();


        //Uso del metodo
        carta.UsarCarta(jugadorMock);

        Assertions.assertEquals(posicionEsperada, jugadorMock.getPosicion());

        System.out.println("Esperado: " + posicionEsperada + " | Recibido: " + jugadorMock.getPosicion());
    }
}