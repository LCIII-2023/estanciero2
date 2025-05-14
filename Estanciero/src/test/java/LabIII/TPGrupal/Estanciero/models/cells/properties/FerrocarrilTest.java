package LabIII.TPGrupal.Estanciero.models.cells.properties;

import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class FerrocarrilTest {
    @Mock
    private Jugador dueñoMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private Ferrocarril ferrocarril;

    private int[] alquileres = {100, 200, 300, 400};

    @BeforeAll
    static void beforeAll(){
        mockStatic(Tablero.class);

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(Tablero.getPartida()).thenReturn(tableroMock);

        ferrocarril = new Ferrocarril();
        ferrocarril.setDueño(dueñoMock);
        ferrocarril.setAlquileres(alquileres);
    }

    @Test
    void calcularAlquiler() {

        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(ferrocarril);

        when(dueñoMock.ObtenerPropiedades()).thenReturn(propiedades);


        // Un ferrocarril
        int costeAlquiler = ferrocarril.CalcularAlquiler();

        // Dos ferrocarriles
        propiedades.add(ferrocarril);
        int costeAlquiler2 = ferrocarril.CalcularAlquiler();

        // Tres ferrocarriles
        propiedades.add(ferrocarril);
        int costeAlquiler3 = ferrocarril.CalcularAlquiler();

        // cuatro ferrocarriles
        propiedades.add(ferrocarril);
        int costeAlquiler4 = ferrocarril.CalcularAlquiler();

        assertEquals(alquileres[0], costeAlquiler);
        assertEquals(alquileres[1], costeAlquiler2);
        assertEquals(alquileres[2], costeAlquiler3);
        assertEquals(alquileres[3], costeAlquiler4);

    }
}