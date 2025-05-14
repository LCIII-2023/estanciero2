package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Carta;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Mazo;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class CartaMultaSuerteTest {
    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }
    //SetUp
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        carta = new CartaMultaSuerte(1, "PRUEBA", TipoCarta.MultaSuerte , -5000);

        tableroMock = Tablero.getPartida();
    }

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Mazo mazoMock;

    @Mock
    private Carta cartaMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private CartaMultaSuerte carta;


    @DisplayName("Test UsarCarta() en MultaSuerte")

    @Test
    void usarCarta() {


        jugadorMock = new JugadorAgresivo();

        tableroMock = Tablero.getPartida();
        tableroMock.getMazoSuerte().getCartas().add(cartaMock);

        when(cartaMock.UsarCarta(jugadorMock)).thenReturn(false);


        int dineroEsperado = jugadorMock.getDinero() + carta.getValor(); //Se espera que se sume el coste de la multa
        int tamañoEsperado = tableroMock.getMazoSuerte().getCartas().size() - 1; //Se espera que se levante una carta


        carta.UsarCarta(jugadorMock);


        Assertions.assertTrue(dineroEsperado == jugadorMock.getDinero() ||
                tamañoEsperado == tableroMock.getMazoSuerte().getCartas().size()
        );

        System.out.println("Esperado: " + dineroEsperado + " o " + tamañoEsperado + " | Recibido: " + jugadorMock.getDinero() + " y " + tableroMock.getMazoSuerte().getCartas().size());

    }
}