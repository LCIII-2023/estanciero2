package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class TributarioTest {

    @InjectMocks
    @Spy
    Tributario tributario;
    @BeforeAll
    static void beforeAll(){
        mockStatic(consola.class);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tributario = new Tributario();
        tributario.setMonto(-500);
    }

    @Test
    void accion() {

        Jugador jugadorAgresivo = new JugadorAgresivo();
        jugadorAgresivo.setEstado(Estado.JUGANDO);
        jugadorAgresivo.setDinero(5000);
        tributario.Accion(jugadorAgresivo);

        assertTrue(jugadorAgresivo.getDinero()==4500);
    }
}