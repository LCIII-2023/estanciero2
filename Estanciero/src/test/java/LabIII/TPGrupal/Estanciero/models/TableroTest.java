package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.console.consola;
import LabIII.TPGrupal.Estanciero.models.cards.CartaCobrarJugadores;
import LabIII.TPGrupal.Estanciero.models.cells.EstacionamientoLibre;
import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.Tributario;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Compañia;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Color;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.enums.Forma;
import LabIII.TPGrupal.Estanciero.models.players.JugadorAgresivo;
import LabIII.TPGrupal.Estanciero.models.players.JugadorConservador;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.verification.AtLeast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static LabIII.TPGrupal.Estanciero.console.consola.MostrarTablero;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TableroTest {
    @Spy
    private Tablero tableroMock;

    @BeforeAll
    static void setUp() {mockStatic(consola.class);}

    @BeforeEach
    void setUp(TestInfo test) {MockitoAnnotations.openMocks(this);}


    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosCambiarBalanceJugadorTest")
    @DisplayName("CambiarBalanceJugadorTest() en Tablero")
    void cambiarBalanceJugadorTest(int caso, int dineroInicial, int valor){

        Jugador jugador = new JugadorNormal();

        switch(caso){
            case 1:
                jugador.setDinero(dineroInicial);
                tableroMock.CambiarBalanceJugador(valor, jugador);
                assertEquals(1500, jugador.getDinero());
                System.out.println("Esperado: " + 1500 + " | Recibido: " + jugador.getDinero());
                break;
            case 2:
                jugador.setDinero(dineroInicial);
                doNothing().when(tableroMock).EliminarJugador(jugador);
                tableroMock.CambiarBalanceJugador(valor, jugador);
                verify(tableroMock).EliminarJugador(jugador);
                System.out.println("Esperado: " + -100 + " | Recibido: " + jugador.getDinero());
                break;
            case 3:
                jugador.setDinero(dineroInicial);
                tableroMock.setCantidadGanadora(10000);
                doNothing().when(tableroMock).TerminarPartida();
                tableroMock.CambiarBalanceJugador(valor, jugador);
                verify(tableroMock).TerminarPartida();
                System.out.println("Esperado: " + 11000 + " | Recibido: " + jugador.getDinero());
                break;
        }

    }
    private static Stream<Arguments> parametrosCambiarBalanceJugadorTest() {
        return Stream.of(
                Arguments.of(1, 1000, 500),
                Arguments.of(2, 400, -500),
                Arguments.of(3, 10000, 1000)
        );
    }

    @Test
    void MostrarTableroTest(){
        when(consola.ConsultarMenu(anyString(),any())).thenReturn(1).thenReturn(2);
        List<Casilla> casillas = new ArrayList<>();
        EstacionamientoLibre e = new EstacionamientoLibre();
        casillas.add(e);
        tableroMock.setCasillas(casillas);
        tableroMock.MostrarTablero();
        verify(consola.class, new AtLeast(1));

    }

    @Test
    void terminarPartidaTest() {

        JugadorConservador jugadorUno = new JugadorConservador();
        jugadorUno.setNombre("Pablo");
        jugadorUno.setDinero(5000);
        JugadorEquilibrado jugadorDos = new JugadorEquilibrado();
        jugadorDos.setNombre("Marce");
        jugadorDos.setDinero(4000);
        JugadorAgresivo jugadorTres = new JugadorAgresivo();
        jugadorTres.setNombre("Rodri");
        jugadorTres.setDinero(2000);

        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorUno);
        jugadores.add(jugadorDos);
        jugadores.add(jugadorTres);


        tableroMock.setJugadores(jugadores);

        tableroMock.TerminarPartida();

        assertEquals(tableroMock.getResultado().get(0), jugadorUno);

        System.out.println("Ganador: "+tableroMock.getResultado().get(0).getNombre() );
    }

    @Test
    void eliminarJugadorTest() {
        //Configuro Jugador a eliminar
        JugadorConservador jugadorUno = new JugadorConservador();
        jugadorUno.setNombre("Pablo");
        jugadorUno.setDinero(5000);
        jugadorUno.setEstado(Estado.JUGANDO);

        //Creo 2 Jugadores màs  para que queden
        JugadorEquilibrado jugadorDos = new JugadorEquilibrado();
        jugadorDos.setNombre("Marce");
        jugadorDos.setDinero(4000);
        JugadorAgresivo jugadorTres = new JugadorAgresivo();
        jugadorTres.setNombre("Rodri");
        jugadorTres.setDinero(2000);

        //Creo lista de jugadores y agrego los creados
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorUno);
        jugadores.add(jugadorDos);
        jugadores.add(jugadorTres);

        //Lo pongo en la partida a esta lista de jugadores
        tableroMock.setJugadores(jugadores);


        //creo propiedades como casillas y las agrego a la partida
        Escritura escritura = new Escritura();
        escritura.setDueño(jugadorUno);
        escritura.setNivel(0);
        Escritura escrituraDos = new Escritura();
        escrituraDos.setDueño(jugadorUno);
        escrituraDos.setNivel(2);

        Ferrocarril f = new Ferrocarril();
        Compañia c = new Compañia();
        tableroMock.getCasillas().add(escritura);
        tableroMock.getCasillas().add(escrituraDos);
        tableroMock.getCasillas().add(f);
        tableroMock.getCasillas().add(c);

        //Creo cartas y se las agrego a la partida

        CartaCobrarJugadores carta = new CartaCobrarJugadores();
        carta.setId(2);
        CartaCobrarJugadores cartaDos = new CartaCobrarJugadores();
        cartaDos.setId(17);
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        cartas.add(cartaDos);
        jugadorUno.setCartas(cartas);

        tableroMock.EliminarJugador(jugadorUno);


        assertFalse(tableroMock.getJugadores().contains(jugadorUno));
        System.out.println("Eliminamos de 3 jugadores 1 y debe quedar 2: "+"Resultado: "+tableroMock.getJugadores().size());
    }


    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosmoverJugadorTest")
    @DisplayName("IniciarPartida() en Tablero")
    void moverJugadorTest(int posicion, int esperado) {
        tableroMock = Tablero.getPartida();
        JugadorConservador jugadorUno = new JugadorConservador();
        jugadorUno.setNombre("Pablo");
        jugadorUno.setPosicion(1);
        jugadorUno.setDinero(1500);

        //////Casillas
        Tributario casilla = new Tributario();
        casilla.setDescripcion("Descripcion");
        casilla.setMonto(-500);
        Tributario casillaDos = new Tributario();
        casillaDos.setDescripcion("DescripcionDos");
        Tributario casillaTres = new Tributario();
        casillaTres.setDescripcion("DescripcionTres");
        Tributario casillaCuatro = new Tributario();
        casillaCuatro.setDescripcion("DescripcionCuatro");
        Tributario casillaCinco = new Tributario();
        casillaCinco.setDescripcion("DescripcionCinco");
        tableroMock.getCasillas().add(casilla);
        tableroMock.getCasillas().add(casillaDos);
        tableroMock.getCasillas().add(casillaTres);
        tableroMock.getCasillas().add(casillaCuatro);
        tableroMock.getCasillas().add(casillaCinco);


        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugadorUno);
        tableroMock.setJugadores(jugadores);
        //////Casillas
        tableroMock.MoverJugador(posicion,jugadorUno);
        int resultado = jugadorUno.getPosicion();
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }
    private static Stream<Arguments> parametrosmoverJugadorTest() {
        //Instancias
        List<Casilla> casillas = new ArrayList<>();
        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(3,3),
                Arguments.of(45,5)
        );

        return parametros;
    }

    @Test
    void tirarDadosTest() {
        int resultado = tableroMock.TirarDados();
        assertTrue(resultado <12 && resultado > 0);
        System.out.println("Valor de la tirada: "+resultado);
    }


    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosiniciarPartidaTest")
    @DisplayName("IniciarPartida() en Tablero")
    void iniciarPartidaTest(int i, List<Jugador> jugadores, List<Propiedad> casillas, boolean esperado ) {
        casillas.get(0).setDueño(jugadores.get(0));
        casillas.get(1).setDueño(jugadores.get(1));

        List<Casilla> casillaList = new ArrayList<>();
        casillaList.add(casillas.get(0));
        casillaList.add(casillas.get(1));
        tableroMock.setCasillas(casillaList);
        tableroMock.setJugadores(jugadores);


        doReturn(false).when(tableroMock).GestionarTurno(any(Jugador.class));
        when(consola.ConsultarMenu(any(), any())).thenReturn(i);

        boolean resultado = tableroMock.IniciarPartida();

        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosiniciarPartidaTest() {
        //Instancias

        //Tengo q colocar nombre, un peon, dinero, posicion, una lista de cartas (1)
        //una propiedad con Nombre,  .CalcularAlquiler() q no sea escritura para simplificar

        //Peon
        Peon peon = new Peon();
        peon.setColor(Color.BLANCO);
        peon.setForma(Forma.CIRCULO);

        //Carta
        CartaCobrarJugadores c = mock(CartaCobrarJugadores.class);
        List<Carta> cartas = new ArrayList<>();
        cartas.add(c);

        //Propiedades
        Ferrocarril f = new Ferrocarril();
        f.setNombre("Ferrocarril Oeste");
        int[] alquileres = {100, 200, 300, 400, 500};
        f.setAlquileres(alquileres);

        Compañia compania = new Compañia();
        compania.setAlquileres(alquileres);
        compania.setNombre("Comapñia Buena Suerte");

        //Voy a pasar una lista de casillas
        List<Propiedad> casillas = new ArrayList<>();
        casillas.add(compania);
        casillas.add(f);
        //Es necesario q haya màs de un jugador

        //Jugador 1
        JugadorEquilibrado jugador = new JugadorEquilibrado();
        jugador.setNombre("Pablo");
        jugador.setPeon(peon);
        jugador.setDinero(5000);
        jugador.setPosicion(0);
        jugador.setCartas(cartas);
        jugador.setEstado(Estado.JUGANDO);

        //Jugador 2
        JugadorEquilibrado jugadorDos = new JugadorEquilibrado();
        jugadorDos.setNombre("Marce");
        jugadorDos.setPeon(peon);
        jugadorDos.setDinero(5000);
        jugadorDos.setPosicion(0);
        jugadorDos.setCartas(cartas);
        jugadorDos.setEstado(Estado.JUGANDO);

        //Creo y agrego jugador
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador);
        jugadores.add(jugadorDos);

        //int i, List<Jugador> jugadores, List<Propiedad> casillas, boolean esperado

        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(1,jugadores,casillas,true),
                Arguments.of(2,jugadores,casillas, false)
        );

        return parametros;
    }


}
