package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarchePresoTest {

    @Mock
    private Tablero tableroMock;

    @Mock
    private Comisaria comisariaMock;

    @Mock
    private Jugador jugadorMock;

    private MarchePreso marchePreso;

    @BeforeAll
    static void beforeAll(){
        mockStatic(Tablero.class);

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<Casilla> casillas = new ArrayList<>();
        for (int i = 0; i <= 14; i++) {
            casillas.add(null);
        }
        casillas.set(14, comisariaMock);
        when(tableroMock.getCasillas()).thenReturn(casillas);

        when(Tablero.getPartida()).thenReturn(tableroMock);

        marchePreso = new MarchePreso(1, "MarchePreso", TipoCasilla.MarchePreso ,"Ir a la cárcel");
    }
    @Test
    void testAccion() {

            marchePreso.Accion(jugadorMock);

            verify(comisariaMock).Encerrar(jugadorMock);

    }
}

