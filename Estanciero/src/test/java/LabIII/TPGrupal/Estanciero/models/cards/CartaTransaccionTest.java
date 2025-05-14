package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class CartaTransaccionTest {

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        carta = new CartaTransaccion(1, "PRUEBA", TipoCarta.Transaccion, 1000);

    }

    @Mock
    private Jugador jugadorMock;

    @Spy
    @InjectMocks
    private CartaTransaccion carta;

    @DisplayName("Test UsarCarta() en Transaccion")

    @Test
    void usarCarta() {


        jugadorMock = new JugadorAgresivo();


        int dineroEsperado = jugadorMock.getDinero() + carta.getValor();


        carta.UsarCarta(jugadorMock);


        Assertions.assertEquals(dineroEsperado, jugadorMock.getDinero());

        System.out.println("Esperado: " + dineroEsperado + " | Recibido: " + jugadorMock.getDinero());
    }
}