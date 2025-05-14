package LabIII.TPGrupal.Estanciero.models.cards;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaMultaMejoraTest {

    @Mock
    private Tablero tableroMock;

    private MockedStatic<Tablero> tableroStaticMock;


    @InjectMocks
    private CartaMultaMejora carta;

    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carta = new CartaMultaMejora(1, "Multa por mejoras", TipoCarta.MultaMejora, 100);
        tableroStaticMock = mockStatic(Tablero.class);
        tableroStaticMock.when(Tablero::getPartida).thenReturn(tableroMock);

    }

    @Test
    public void usarCartaTest() {
        Jugador jugador = mock(Jugador.class);
        Escritura propiedad1 = mock(Escritura.class);
        Escritura propiedad2 = mock(Escritura.class);

        when(propiedad1.getNivel()).thenReturn(2);
        when(propiedad2.getNivel()).thenReturn(3);

        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(propiedad1);
        propiedades.add(propiedad2);

        when(jugador.ObtenerPropiedades()).thenReturn(propiedades);


        boolean resultado = carta.UsarCarta(jugador);

        
        assertTrue(resultado);
        verify(tableroMock).CambiarBalanceJugador(500, jugador);


    }
}
