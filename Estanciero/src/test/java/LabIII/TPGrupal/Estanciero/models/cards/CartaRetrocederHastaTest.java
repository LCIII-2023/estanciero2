package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;

class CartaRetrocederHastaTest {

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        //Instancia de la clase
        carta = new CartaRetrocederHasta(1, "PRUEBA", TipoCarta.RetrocederHasta, 1);

        //Anulacion de los metodos de consola (Clase estatica)

    }

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Casilla casillaMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private CartaRetrocederHasta carta;


    @DisplayName("Test UsarCarta() en RetrocederHasta")

    @Test
    void usarCarta() {

            //Instancias
            jugadorMock = new JugadorAgresivo();

            tableroMock = Tablero.getPartida();
            tableroMock.getCasillas().addAll(Arrays.asList(casillaMock, casillaMock, casillaMock, casillaMock, casillaMock));


            //Metodos Simulados
            doNothing().when(casillaMock).Accion(jugadorMock);


            //Calculo del resultado esperado
            int posicionEsperada = carta.getValor();


            //Uso del metodo
            carta.UsarCarta(jugadorMock);


            //Comprobaciones
            Assertions.assertEquals(posicionEsperada, jugadorMock.getPosicion());

            System.out.println("Esperado: " + posicionEsperada + " | Recibido: " + jugadorMock.getPosicion());
    }
}