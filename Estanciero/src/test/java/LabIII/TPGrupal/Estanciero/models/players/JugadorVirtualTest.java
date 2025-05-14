package LabIII.TPGrupal.Estanciero.models.players;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class JugadorVirtualTest {

    @BeforeAll
    static void setUp() {
        mockStatic(consola.class);
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Spy
    JugadorConservador jugadorVirtual;

    @Test
    void turno() {
        Escritura e = new Escritura();
        jugadorVirtual.setDinero(0);

        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(e);

        when(jugadorVirtual.ObtenerPropiedades()).thenReturn(casillas);

        jugadorVirtual.Turno(e);

        verify(jugadorVirtual).Comprar(e, e.getPrecio());
        verify(jugadorVirtual).Mejorar();
    }
}