package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Estanciero;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Compañia;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorConservador;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PropiedadTest {


    @Spy
    private JugadorEquilibrado jugadorMock;



    @Mock
    private Tablero tableroMock;




    @BeforeAll
    static void setUp() {
        mockStatic(consola.class);
    }
   @BeforeEach
   public void setUp(TestInfo testInfo) {
       MockitoAnnotations.initMocks(this);

   }
   @AfterEach
   void tearDown() {
       Mockito.framework().clearInlineMocks();
   }

    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosPropiedad")
    @DisplayName("Accion() en Propiedad")
   void accion(Propiedad propiedad, int esperado) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorMock);
        tableroMock.setJugadores(jugadores);
        propiedad.Accion(jugadorMock);
        assertEquals(esperado, jugadorMock.getDinero());
        System.out.println("Esperado: " +esperado + " | Recibido: " + jugadorMock.getDinero());

   }
    private static Stream<Arguments> parametrosPropiedad() {
        //Instancias
        //Test1 propiedad con dueno
        Propiedad propiedad = new Ferrocarril();
        propiedad.setPrecio(500);
        propiedad.setHipotecado(false);
        propiedad.setId(0);

        int esperado  = 34500;

        Jugador dueno = new JugadorEquilibrado();
        dueno.setEstado(Estado.JUGANDO);
        dueno.setDinero(0);

        propiedad.setDueño(dueno);

        //Test2 propiedad sin dueno
        Propiedad ferrocarril = new Ferrocarril();
        ferrocarril.setPrecio(600);
        ferrocarril.setHipotecado(false);
        ferrocarril.setId(0);
        int esperadoDos  = 34400;



        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(ferrocarril, esperadoDos)
        );

        return parametros;
    }

}
