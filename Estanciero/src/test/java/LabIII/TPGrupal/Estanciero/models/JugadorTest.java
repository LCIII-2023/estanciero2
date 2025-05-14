



package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.models.cells.Propiedad;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Compañia;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorConservador;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import LabIII.TPGrupal.Estanciero.models.players.JugadorNormal;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JugadorTest {

    @Mock
    private Tablero tableroMock;
    @InjectMocks
    @Spy
    private JugadorEquilibrado jugadorMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tableroMock = Tablero.getPartida();
    }

    @Test
    void obtenerPropiedadesTest() {
        Escritura escritura = new Escritura();
        escritura.setDueño(jugadorMock);
        Escritura escrituraDos = new Escritura();
        escrituraDos.setDueño(jugadorMock);
        Ferrocarril f = new Ferrocarril();
        Compañia c = new Compañia();
        tableroMock.getCasillas().add(escritura);
        tableroMock.getCasillas().add(escrituraDos);
        tableroMock.getCasillas().add(f);
        tableroMock.getCasillas().add(c);
        List<Propiedad> lista = jugadorMock.ObtenerPropiedades();
        assertEquals(2, lista.size());
        System.out.println("Cantidad esperada de propiedades: 2"+" "+"Cantidad obtenida: "+lista.size());
    }

    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosValidarComprar")
    @DisplayName("ValidarCompra(Propiedad p) en Jugador")
    void validarCompraTest(Propiedad p, String esperado, Estado estado) {
        if (estado != null){
            jugadorMock.setEstado(estado);
        }
        String resultado =  jugadorMock.ValidarCompra(p);
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosValidarComprar() {
        //Instancias
        Escritura escrituraPrioritaria = new Escritura();
        escrituraPrioritaria.setPrecio(50000);
        String esperado  = "Dinero insuficiente para comprar\n";

        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setPrecio(500);
        JugadorEquilibrado dueño = mock(JugadorEquilibrado.class);
        ferrocarril.setDueño(dueño);
        String esperadoDos  = "La propiedad ya tiene dueño\n";

        Compañia compañia = new Compañia();
        compañia.setPrecio(50);
        String esperadoTres = "No puede comprar estando en la COMISARIA o en BANCARROTA\n";


        Stream<Arguments> parametros = Stream.of(
                Arguments.of(escrituraPrioritaria,esperado,null ),
                Arguments.of(ferrocarril, esperadoDos, null),
                Arguments.of(compañia, esperadoTres, Estado.BANCARROTA),
                Arguments.of(compañia, esperadoTres, Estado.BANCARROTA)
        );

        return parametros;
    }







    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosValidarVenta")
    @DisplayName(" ValidarVenta(Propiedad p) en Jugador")
    void validarVentaTest(List<Propiedad> propiedades, Propiedad p, String esperado) {

        if(!propiedades.isEmpty()){
            for (Propiedad propiedad : propiedades){
                if(p.getDueño()==null){
                    p.setDueño(jugadorMock);
                }
            }
        }

        if(p.getDueño()==null){
            p.setDueño(jugadorMock);
        }
        when(jugadorMock.ObtenerPropiedades()).thenReturn(propiedades);
        String resultado =  jugadorMock.ValidarVenta(p);
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosValidarVenta() {
        //Instancias
        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setPrecio(500);
        JugadorEquilibrado dueño = mock(JugadorEquilibrado.class);
        ferrocarril.setDueño(dueño);
        String esperadoDos  = "No se puede vender una propiedad ajena\n";

        Escritura escrituraPrioritaria = new Escritura();
        escrituraPrioritaria.setHipotecado(true);
        String esperado  ="No se puede vender una propiedad hipotecada\n";

        List<Propiedad> lista = new ArrayList<>();

        Escritura e = new Escritura();
        e.setProvincia("Mendoza");
        e.setNivel(0);

        Escritura eDos = new Escritura();
        eDos.setProvincia("Mendoza");
        eDos.setNivel(2);

        Escritura eTres = new Escritura();
        eTres.setProvincia("Mendoza");
        eTres.setNivel(3);

        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(e);
        propiedades.add(eDos);
        propiedades.add(eTres);

        Escritura escritura = new Escritura();
        escritura.setHipotecado(false);
        String esperadoTres ="No se puede vender una escritura de una provincia con mejoras\n";
        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(lista,ferrocarril,esperadoDos),
                Arguments.of( lista,escrituraPrioritaria, esperado),
                Arguments.of(propiedades,e,esperadoTres),
                Arguments.of( lista,escritura, null)
        );

        return parametros;
    }




    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosCompraMejora")
    @DisplayName("ValidarCompraMejora(Propiedad p) en Jugador")
    void validarCompraMejoraTest(List<Propiedad> propiedades, Propiedad p, String esperado) {

        if(!propiedades.isEmpty()){
            for (Propiedad propiedad : propiedades){
                tableroMock.getCasillas().add(propiedad);
                if(propiedad.getDueño()==null){
                    propiedad.setDueño(jugadorMock);
                }

            }
        }

        if(p.getDueño()==null){
            p.setDueño(jugadorMock);
        }
        when(jugadorMock.ObtenerPropiedades()).thenCallRealMethod();
        String resultado =  jugadorMock.ValidarCompraMejora(p);
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosCompraMejora() {
        //Instancias
        //Test 1
        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setNombre("Ferrocarril Oeste");
        String esperado   = ferrocarril.getNombre() + " no es una escritura\n";

        //Test 2
        Escritura escrituraHipotecada = new Escritura();
        escrituraHipotecada.setNivel(0);
        escrituraHipotecada.setCosteMejora(100);
        escrituraHipotecada.setHipotecado(true);
        String esperadoDos  ="No se puede mejorar una propiedad hipotecada\n";

        //Test 3
        Escritura eOutPresupuesto = new Escritura();
        eOutPresupuesto.setCosteMejora(100000);
        eOutPresupuesto.setNivel(0);
        eOutPresupuesto.setHipotecado(false);
        String esperadoTres  ="Dinero insuficiente\n";

        //Test 4
        Escritura escrituraFull = new Escritura();
        escrituraFull.setCosteMejora(100);
        escrituraFull.setNivel(5);
        String esperadoCuatro  ="La escritura ya esta mejorada al maximo\n";

        //Lista vacìa
        List<Propiedad> lista = new ArrayList<>();

        //Test 5
        Escritura escrituraAMejorar = new Escritura();
        escrituraAMejorar.setCosteMejora(100);
        escrituraAMejorar.setHipotecado(false);
        escrituraAMejorar.setProvincia("Mendoza");
        escrituraAMejorar.setNivel(0);

        Escritura eDos = new Escritura();
        eDos.setProvincia("Mendoza");
        eDos.setNivel(0);
        JugadorEquilibrado dueño = mock(JugadorEquilibrado.class);
        eDos.setDueño(dueño);

        Escritura eTres = new Escritura();
        eTres.setProvincia("Mendoza");
        eTres.setNivel(0);

        List<Propiedad> propiedades = new ArrayList<>();
        propiedades.add(escrituraAMejorar);
        propiedades.add(eDos);
        propiedades.add(eTres);
        String esperadoCinco = "No se puede mejorar hasta completar la provincia\n";

        //Test 6
        Escritura escrituraAsimetrica = new Escritura();
        escrituraAsimetrica.setCosteMejora(100);
        escrituraAsimetrica.setHipotecado(false);
        escrituraAsimetrica.setProvincia("FORMOSA");
        escrituraAsimetrica.setNivel(1);

        Escritura eDiscriminada = new Escritura();
        eDiscriminada.setProvincia("FORMOSA");
        eDiscriminada.setNivel(0);

        List<Propiedad> escrituras = new ArrayList<>();
        escrituras.add(escrituraAsimetrica);
        escrituras.add(eDiscriminada);

        String esperadoSeis = "El nivel de mejora no puede ser mayor en uno al del resto de provincias\n";

        //Test 7
        Escritura escrituraOk = new Escritura();
        escrituraOk.setCosteMejora(100);
        escrituraOk.setHipotecado(false);
        escrituraOk.setProvincia("TUCUMAN");
        escrituraOk.setNivel(0);

        Escritura escrituraOkDos = new Escritura();
        escrituraOkDos.setProvincia("TUCUMAN");
        escrituraOkDos.setNivel(0);

        List<Propiedad> escriturasOK = new ArrayList<>();
        escriturasOK.add(escrituraOk);
        escriturasOK.add(escrituraOkDos);


        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(lista,ferrocarril,esperado),
                Arguments.of( lista,escrituraHipotecada, esperadoDos),
                Arguments.of( lista,eOutPresupuesto, esperadoTres),
                Arguments.of( lista,escrituraFull, esperadoCuatro),
                Arguments.of(propiedades,escrituraAMejorar,esperadoCinco),
                Arguments.of(escrituras,escrituraAsimetrica,esperadoSeis),
                Arguments.of( escriturasOK,escrituraOk, null)
        );

        return parametros;
    }




    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosVentaMejora")
    @DisplayName("ValidarVentaMejora(Propiedad p) en Jugador")
    void validarVentaMejoraTest(List<Propiedad> propiedades, Propiedad p, String esperado) {
        if(!propiedades.isEmpty()){
            for (Propiedad propiedad : propiedades){
                tableroMock.getCasillas().add(propiedad);
                if(propiedad.getDueño()==null){
                    propiedad.setDueño(jugadorMock);
                }

            }
        }

        if(p.getDueño()==null){
            p.setDueño(jugadorMock);
        }
        when(jugadorMock.ObtenerPropiedades()).thenCallRealMethod();
        String resultado =  jugadorMock.ValidarVentaMejora(p);
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosVentaMejora() {
        //Instancias
        //Test 1 No es escritura
        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setNombre("Ferrocarril Oeste");
        String esperado   = ferrocarril.getNombre() + " no es una escritura\n";

        //Test 2 Hipotecada
        Escritura escrituraHipotecada = new Escritura();
        escrituraHipotecada.setNivel(0);
        escrituraHipotecada.setCosteMejora(100);
        escrituraHipotecada.setHipotecado(true);
        String esperadoDos  ="No se pueden vender mejoras de una propiedad hipotecada\n";

        //Test 3 No tiene mejoras a vender
        Escritura eSinMejoras = new Escritura();
        eSinMejoras.setNivel(0);
        eSinMejoras.setHipotecado(false);
        String esperadoTres  ="La escritura no tiene mejoras que vender\n";


        //Lista vacìa
        List<Propiedad> lista = new ArrayList<>();


        //Test 4
        Escritura escrituraAsimetrica = new Escritura();
        escrituraAsimetrica.setCosteMejora(100);
        escrituraAsimetrica.setHipotecado(false);
        escrituraAsimetrica.setProvincia("FORMOSA");
        escrituraAsimetrica.setNivel(2);

        Escritura eDiscriminada = new Escritura();
        eDiscriminada.setProvincia("FORMOSA");
        eDiscriminada.setNivel(3);

        List<Propiedad> escrituras = new ArrayList<>();
        escrituras.add(escrituraAsimetrica);
        escrituras.add(eDiscriminada);

        String esperadoCuatro = "El nivel de mejora no puede ser menor en uno al del resto de provincias\n";

        //Test 5
        Escritura escrituraOk = new Escritura();
        escrituraOk.setCosteMejora(100);
        escrituraOk.setHipotecado(false);
        escrituraOk.setProvincia("TUCUMAN");
        escrituraOk.setNivel(2);

        Escritura escrituraOkDos = new Escritura();
        escrituraOkDos.setProvincia("TUCUMAN");
        escrituraOkDos.setNivel(0);

        List<Propiedad> escriturasOK = new ArrayList<>();
        escriturasOK.add(escrituraOk);
        escriturasOK.add(escrituraOkDos);


        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(lista,ferrocarril,esperado),
                Arguments.of( lista,escrituraHipotecada, esperadoDos),
                Arguments.of( lista,eSinMejoras, esperadoTres),
                Arguments.of(escrituras,escrituraAsimetrica,esperadoCuatro),
                Arguments.of( escriturasOK,escrituraOk, null)
        );

        return parametros;
    }


    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosvalidarHipoteca")
    @DisplayName("validarHipoteca en Jugador")
    void validarHipotecaTest( Propiedad p, String esperado) {
        if(p.getDueño()==null){
            p.setDueño(jugadorMock);
        }
        String resultado =  jugadorMock.ValidarHipoteca(p);
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }

    private static Stream<Arguments> parametrosvalidarHipoteca() {
        //Instancias
        //Test 1 No le alcanza el dinero paara pagar hipoteca
        Ferrocarril ferrocarril = new Ferrocarril();
        ferrocarril.setHipotecado(true);
        ferrocarril.setValorHipoteca(40000);
        String esperado   ="Dinero insuficiente para pagar hipoteca\n";

        //Test 2 Hipotecar pPropieddad mejorada
        Escritura escrituraMejorada = new Escritura();
        escrituraMejorada.setNivel(3);
        escrituraMejorada.setHipotecado(false);
        String esperadoDos  ="No puede hipotecar una propiedad con mejoras\n";

        //Test 3 No tiene mejoras a vender
        Escritura escrituraHipotecable = new Escritura();
        escrituraHipotecable.setNivel(0);
        escrituraHipotecable.setHipotecado(false);
        escrituraHipotecable.setValorHipoteca(100);


        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(ferrocarril,esperado),
                Arguments.of( escrituraMejorada, esperadoDos),
                Arguments.of( escrituraHipotecable, null)
        );

        return parametros;
    }



    @Test
    void confirmarCompraTest() {
        Escritura e = new Escritura();
        e.setNombre("Escritura");
        e.setPrecio(5000);
        int cashBeforeBuy = jugadorMock.getDinero();

        jugadorMock.ConfirmarCompra(e,e.getPrecio());

        assertSame(e.getDueño(), jugadorMock);

        System.out.println("Dinero antes de la compra: "+ cashBeforeBuy);
        System.out.println("Dinero despues de la compra: "+ jugadorMock.getDinero());
    }

    @Test
    void confirmarVentaTest() {
        JugadorConservador nuevoDuenio = new JugadorConservador();
        nuevoDuenio.setNombre("Marcos");
        nuevoDuenio.setDinero(12000);

        Escritura e = new Escritura();
        e.setNombre("Escritura");
        e.setPrecio(5000);
        e.setDueño(jugadorMock);

        jugadorMock.ConfirmarVenta(e,e.getPrecio(),nuevoDuenio);

        assertSame(e.getDueño(), nuevoDuenio);

        System.out.println("Su nuevo dueño es : "+ nuevoDuenio.getNombre());
    }

    @Test
    void confirmarMejoraTest() {
        Escritura e = new Escritura();
        e.setNombre("Escritura");
        e.setCosteMejora(5000);
        e.setDueño(jugadorMock);
        e.setNivel(4);
        jugadorMock.ConfirmarMejora(e,1);
        assertEquals(5,e.getNivel());
    }

    @ParameterizedTest(name = "[Test {index}]")
    @MethodSource("parametrosConfirmarHipoteca")
    @DisplayName(" confirmarHipoteca en Jugador")
    void confirmarHipotecaTest(Propiedad p, boolean esperado ) {
        p.setDueño(jugadorMock);
        jugadorMock.ConfirmarHipoteca(p);
        boolean resultado = p.isHipotecado();
        assertEquals(esperado,resultado);
        System.out.println("Esperado: " +esperado + " | Recibido: " + resultado);
    }
    private static Stream<Arguments> parametrosConfirmarHipoteca() {
        //Instancias
        //Test  1 Propiedad Hipotecada: Levantar
        Escritura escrituraHipotecada = new Escritura();
        escrituraHipotecada.setHipotecado(true);
        escrituraHipotecada.setValorHipoteca(5000);
        escrituraHipotecada.setNombre("EscrituraHipotecada");

        //Test 2 Hipotecar
        Escritura escrituraSinHipotecar = new Escritura();
        escrituraSinHipotecar.setNivel(0);
        escrituraSinHipotecar.setHipotecado(false);
        escrituraSinHipotecar.setValorHipoteca(100);
        escrituraSinHipotecar.setNombre("Escritura sin Hipotecar");


        //Argumentos
        Stream<Arguments> parametros = Stream.of(
                Arguments.of(escrituraHipotecada, false),
                Arguments.of( escrituraSinHipotecar,true)
        );

        return parametros;
    }
}

