package LabIII.TPGrupal.Estanciero.models.players;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class JugadorNormalTest {

    @Spy
    JugadorNormal jugadorMock;

    @Spy
    JugadorNormal jugadorNormal;

    @BeforeAll
    static void setUp() {
        mockStatic(consola.class);
    }

    @BeforeEach
    void setUp(TestInfo test) {
        MockitoAnnotations.openMocks(this);

        System.out.println("\nCorriendo: " + test.getTestMethod().get().getName() + " - " + test.getDisplayName());


        jugadorMock = new JugadorNormal();
        jugadorMock.setDinero(10000);
        jugadorMock.setEstado(Estado.JUGANDO);

    }

    @Test
    void turno() {

        Ferrocarril f =new Ferrocarril();
        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(f);

        when(jugadorNormal.ObtenerPropiedades()).thenReturn(casillas);
        when(jugadorNormal.Comprar(f, f.getPrecio())).thenReturn(true);
        when(consola.ConsultarMenu(any(), any())).thenReturn(1).thenReturn(5);

        jugadorNormal.Turno(f);


        verify(jugadorNormal).Comprar(any(Ferrocarril.class), anyInt());
    }

    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosComprar")
    @DisplayName("Comprar() en JugadorNormal")
    void comprar(Propiedad propiedadIngresada, int precio, boolean esperado) {

        when(consola.ConsultarMenu(anyString(), any(String[].class))).thenReturn(1);

        boolean resultado = jugadorMock.Comprar(propiedadIngresada, precio);

        assertEquals(esperado, resultado);

        System.out.println("Esperado: " + esperado + " | Recibido: " + resultado);
    }

    static Stream<Arguments> parametrosComprar() {
        Propiedad propiedadMock1 = mock(Propiedad.class);
        when(propiedadMock1.getNombre()).thenReturn("Propiedad 1");
        when(propiedadMock1.getPrecio()).thenReturn(5000);

        Propiedad propiedadMock2 = mock(Propiedad.class);
        when(propiedadMock2.getNombre()).thenReturn("Propiedad 2");
        when(propiedadMock2.getPrecio()).thenReturn(15000);

        return Stream.of(
                Arguments.of(propiedadMock1, propiedadMock1.getPrecio(), true),
                Arguments.of(propiedadMock2, propiedadMock2.getPrecio(), false)
        );

    }

    @Test
    void venderTest() {

        Ferrocarril f =new Ferrocarril();
        f.setPrecio(100);
        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(f);

        when(jugadorNormal.ObtenerPropiedades()).thenReturn(casillas);
        when(jugadorNormal.ValidarVenta(f)).thenReturn(null);
        when(consola.ConsultarMenu(any(), any())).thenReturn(1);
        when(jugadorNormal.ObtenerComprador()).thenReturn(null);

        boolean resultado = jugadorNormal.Vender();
        assertTrue(resultado);
        verify(jugadorNormal).ConfirmarVenta(f,f.getPrecio(),null);

    }

    @Test
    void mejorarTest() {
        Escritura e =new Escritura();
        e.setNombre("Escritura");
        e.setNivel(1);
        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(e);
        int i=0;

        when(jugadorNormal.ObtenerPropiedades()).thenReturn(casillas);
        when(jugadorNormal.ValidarCompraMejora(e)).thenReturn(null);
        when(jugadorNormal.ValidarVentaMejora(e)).thenReturn(null);
        //when(jugadorNormal.getPropiedades().get(i).getNombre()).thenReturn(e.getNombre());
        when(consola.ConsultarMenu(any(), any())).thenReturn(1);

        boolean resultado = jugadorNormal.Mejorar();
        assertTrue(resultado);
    }

    @Test
    void hipotecar() {

        Escritura e =new Escritura();
        e.setHipotecado(false);
        e.setValorHipoteca(100);
        e.setNombre("Escritura");
        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(e);

        when(jugadorNormal.ObtenerPropiedades()).thenReturn(casillas);
        when(jugadorNormal.ValidarHipoteca(e)).thenReturn(null);
        when(consola.ConsultarMenu(any(), any())).thenReturn(1);


        boolean resultado = jugadorNormal.Hipotecar();
        assertTrue(resultado);
    }
}