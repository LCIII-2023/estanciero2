package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Comisaria;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaMarchePresoTest {

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);


        tableroStaticMock = mockStatic(Tablero.class);
        tableroStaticMock.when(Tablero::getPartida).thenReturn(tableroMock);

        when(tableroMock.getCasillas()).thenReturn(casillasMock);
        when(casillasMock.get(14)).thenReturn(comisariaMock);

    }

    @Mock
    private Jugador jugadorMock;

    @Mock
    private Tablero tableroMock;

    @Mock
    private Comisaria comisariaMock;

    @Mock
    private List<Casilla> casillasMock;

    private MockedStatic<Tablero> tableroStaticMock;

    @Spy
    @InjectMocks
    private CartaMarchePreso carta;

    @Test
    void usarCarta() {

        Estado estadoEsperado = Estado.COMISARIA;
        carta.UsarCarta(jugadorMock);


        verify(comisariaMock).Encerrar(jugadorMock); // Verifica que se llame al Encerrar de la Comisaria

        when(jugadorMock.getEstado()).thenReturn(estadoEsperado);

        Assertions.assertEquals(estadoEsperado, jugadorMock.getEstado());

        System.out.println("Esperado: " + estadoEsperado + " | Recibido: " + jugadorMock.getEstado());
    }
}