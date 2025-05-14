package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Carta;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Mazo;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class SuerteDestinoTest {

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    private Mazo mazoMock;

    @Mock
    private Carta cartaMock;

    @Spy
    @InjectMocks
    private SuerteDestino suerteCasilla;

    @Spy
    @InjectMocks
    private SuerteDestino destinoCasilla;

    @BeforeAll
    static void beforeAll(){
        mockStatic(Tablero.class);

    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(tableroMock.getMazoSuerte()).thenReturn(mazoMock);
        when(tableroMock.getMazoDestino()).thenReturn(mazoMock);

        mazoMock.getCartas().add(cartaMock);


        suerteCasilla = new SuerteDestino(1, "SUERTE", TipoCasilla.SuerteDestino, "testSuerte");
        destinoCasilla = new SuerteDestino(2, "DESTINO",TipoCasilla.SuerteDestino, "testDestino");
    }

    @AfterEach
    void desHacerMockStatic() {
        Mockito.framework().clearInlineMocks();
    }

    @Test
    void testAccionSuerteNoDevuelveCarta() {

        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(cartaMock.UsarCarta(jugadorMock)).thenReturn(false);

        suerteCasilla.Accion(jugadorMock);

        assertTrue(mazoMock.getCartas().isEmpty());
    }

    @Test
    void testAccionSuerteDevuelveCarta() {

        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(cartaMock.UsarCarta(jugadorMock)).thenReturn(true);

        suerteCasilla.Accion(jugadorMock);

        assertFalse(mazoMock.getCartas().isEmpty());
    }
    @Test
    void testAccionDestinoNoDevuelveCarta() {

        when(Tablero.getPartida()).thenReturn(tableroMock);

        when(cartaMock.UsarCarta(jugadorMock)).thenReturn(false);

        destinoCasilla.Accion(jugadorMock);

        assertTrue(mazoMock.getCartas().isEmpty());
    }
}