package LabIII.TPGrupal.Estanciero.models.cells.properties;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Dado;
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

class CompañiaTest {

    @Mock
    private Jugador dueñoMock;

    @Mock
    private Tablero tableroMock;

    @Mock
    private Dado dado1Mock;

    @Mock
    private Dado dado2Mock;

    @Spy
    @InjectMocks
    private Compañia compañia;

    private int[] alquileres = {100, 200, 300}; // Ejemplo de valores de alquiler

    @BeforeAll
    static void beforeAll(){
        mockStatic(Tablero.class);

    }

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        when(Tablero.getPartida()).thenReturn(tableroMock);
        // Configuramos el tablero mock para devolver los dados mock
        when(tableroMock.getDado1()).thenReturn(dado1Mock);
        when(tableroMock.getDado2()).thenReturn(dado2Mock);

        // Creamos la instancia de Compañia
        compañia = new Compañia();
        compañia.setDueño(dueñoMock);
        compañia.setAlquileres(alquileres);
    }

    @Test
    void calcularAlquiler(){


        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(compañia);

        when(dueñoMock.ObtenerPropiedades()).thenReturn(propiedades);


        when(dado1Mock.getValor()).thenReturn(3);
        when(dado2Mock.getValor()).thenReturn(4);

        // Una compania
        int costeAlquiler = compañia.CalcularAlquiler();

        // Dos companias
        propiedades.add(compañia);
        int costeAlquiler2 = compañia.CalcularAlquiler();

        // Tres companias
        propiedades.add(compañia);
        int costeAlquiler3 = compañia.CalcularAlquiler();


        assertEquals((3 + 4) * alquileres[0], costeAlquiler);
        assertEquals((3 + 4) * alquileres[1], costeAlquiler2);
        assertEquals((3 + 4) * alquileres[2], costeAlquiler3);


    }
}
