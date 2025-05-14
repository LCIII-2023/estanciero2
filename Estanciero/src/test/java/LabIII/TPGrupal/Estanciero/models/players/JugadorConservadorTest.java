package LabIII.TPGrupal.Estanciero.models.players;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Compañia;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

class JugadorConservadorTest {

    @BeforeAll
    static void setUp() {
        mockStatic(consola.class);
    }

    @BeforeEach
    void setUp(TestInfo test) {
        MockitoAnnotations.openMocks(this);

        System.out.println("\nCorriendo: " + test.getTestMethod().get().getName() + " - " + test.getDisplayName());

        jugadorMock.setProvinciasPrioritarias(Arrays.asList("FORMOSA", "RIO NEGRO", "SALTA"));
        jugadorMock.setDinero(10000);
        jugadorMock.setEstado(Estado.JUGANDO);
    }
    @Mock
    private Tablero tableroMock;
    @InjectMocks
    @Spy
    JugadorConservador jugadorMock;

    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosComprar")
    @DisplayName("Comprar() en JugadorConservador")
    void comprar(Propiedad propiedadIngresada, int precio, boolean esperado) {
        boolean resultado = jugadorMock.Comprar(propiedadIngresada, precio);

        assertEquals(esperado, resultado);

        System.out.println("Esperado: " + esperado + " | Recibido: " + resultado);
    }

    @Test
    @Disabled
    void vender() {
    }

    @Test
    void mejorar() {
        Escritura eUno = new Escritura(19, "MENDOZA ZONA CENTRO", TipoCasilla.Escritura, 3400,
                new int[] {250, 1350, 3800, 10500, 14200, 18000}, 1700,
                false, null, "MENDOZA", "CENTRO", 2, 2000);
        eUno.setDueño(jugadorMock);
        Escritura eDos =new Escritura(20, "FORMOSA ZONA NORTE", TipoCasilla.Escritura, 3800,
                new int[] {300, 1500, 4200, 11500, 15000, 19000}, 1900,
                false, null, "FORMOSA", "NORTE", 0, 2000);
        Escritura eTres = new Escritura(23, "SANTA FE ZONA SUR", TipoCasilla.Escritura, 4200,
                new int[] {350, 1700, 4750, 13000, 16000, 20000}, 2100,
                false, null, "SANTA FE", "SUR", 0, 2600);
        Escritura eCuatro =new Escritura(29, "TUCUMAN ZONA SUR", TipoCasilla.Escritura, 5000,
                new int[] {400, 2200, 6000, 15000, 18000, 21000}, 2500,
                false, null, "TUCUMAN", "SUR", 0, 5000);
        eCuatro.setDueño(jugadorMock);

        Ferrocarril ferrocarril = new Ferrocarril();
        Compañia c = new Compañia();
        c.setDueño(jugadorMock);

        tableroMock.getCasillas().add(eUno);
        tableroMock.getCasillas().add(eDos);
        tableroMock.getCasillas().add(eTres);
        tableroMock.getCasillas().add(eCuatro);
        tableroMock.getCasillas().add(ferrocarril);
        tableroMock.getCasillas().add(c);

        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(eUno);
        propiedades.add(eCuatro);
        propiedades.add(c);
        doReturn(propiedades).when(jugadorMock).ObtenerPropiedades();



        boolean resultadoUno = eUno.getDueño().Mejorar();
        boolean resultadoC = c.getDueño().Mejorar();
        boolean resultadoee4 = eCuatro.getDueño().Mejorar();


        assertEquals(true, resultadoUno);
        assertEquals(false, resultadoC);
        assertEquals(false,resultadoee4);

        System.out.println("Esperado: " + "true" + " | Recibido: " + resultadoUno);
        System.out.println("Esperado: " + "false" + " | Recibido: " + resultadoC);
        System.out.println("Esperado: " + "false" + " | Recibido: " + resultadoee4);

        System.out.println("Nivel de la propiedad pos mejora "+eUno.getNivel());
    }

    @Test
    @Disabled
    void hipotecar() {
    }


    private static Stream<Arguments> parametrosComprar() {
        //Instancias
        Escritura escrituraPrioritaria = new Escritura();
        escrituraPrioritaria.setPrecio(5000);
        escrituraPrioritaria.setProvincia("FORMOSA");

        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setPrecio(5000);

        Compañia compañia = new Compañia();
        compañia.setPrecio(5000);

        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(escrituraPrioritaria, escrituraPrioritaria.getPrecio(), true),         //Provincia prioritaria a precio base
                Arguments.of(escrituraPrioritaria, escrituraPrioritaria.getPrecio() * 2, false),    //Provincia prioritaria a precio x2
                Arguments.of(escrituraPrioritaria, escrituraPrioritaria.getPrecio() * 3, false),    //Provincia prioritaria a precio x3
                Arguments.of(ferrocarril, ferrocarril.getPrecio(), false),                          //Ferrocarril
                Arguments.of(compañia, compañia.getPrecio(), false)                                 //Compañia
        );

        return parametros;
    }
}