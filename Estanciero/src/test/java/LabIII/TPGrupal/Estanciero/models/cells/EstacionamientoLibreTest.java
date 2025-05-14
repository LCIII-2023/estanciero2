package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class EstacionamientoLibreTest {

    @Mock
    private Jugador jugadorMock;

    @Spy
    @InjectMocks
    private EstacionamientoLibre estacionamientoLibreMock;

    @BeforeEach
    void setUp() {
        estacionamientoLibreMock  = new EstacionamientoLibre(1, "EstacionamientoLibre", TipoCasilla.EstacionamientoLibre ,"Prueba");
    }

    @Test
    void testAccion() {
        jugadorMock = new JugadorEquilibrado();

        estacionamientoLibreMock.Accion(jugadorMock);

        assertTrue(estacionamientoLibreMock.getDescripcion() == "Prueba");
    }
}