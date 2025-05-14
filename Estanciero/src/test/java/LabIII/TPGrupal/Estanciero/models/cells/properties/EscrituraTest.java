package LabIII.TPGrupal.Estanciero.models.cells.properties;

import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.Jugador;
import LabIII.TPGrupal.Estanciero.models.Tablero;
import LabIII.TPGrupal.Estanciero.models.players.JugadorEquilibrado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class EscrituraTest {

    @Mock
    private Jugador dueñoMock;

    @Mock
    private Tablero tableroMock;

    @Spy
    @InjectMocks
    private Escritura escrituraMock;

    private MockedStatic<Tablero> tableroStaticMock;

    private int[] alquileres = {100, 200, 300, 400, 500, 600};

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        tableroStaticMock = mockStatic(Tablero.class);
        tableroStaticMock.when(Tablero :: getPartida).thenReturn(tableroMock);

        escrituraMock = new Escritura();
        escrituraMock.setDueño(dueñoMock);
        escrituraMock.setAlquileres(alquileres);
        escrituraMock.setProvincia("Test");

    }
    @AfterEach
    void tearDown() {
        tableroStaticMock.close();
    }


    @Test
    void calcularAlquiler() {
        //nivel 0- pcia incompleta
        Escritura escritura1 = new Escritura();
        escritura1.setProvincia("Formosa");
        Escritura escritura2 = new Escritura();
        escritura2.setProvincia("Formosa");
        Escritura escritura3 = new Escritura();
        escritura3.setProvincia("Formosa");
        escritura1.setNivel(0);

        Jugador dueño1 = new JugadorEquilibrado();
        Jugador dueño2 = new JugadorEquilibrado();

        escritura1.setDueño(dueño1);
        escritura2.setDueño(dueño1);
        escritura3.setDueño(dueño2);

        escritura1.setAlquileres(alquileres);

        List<Casilla> casillas1 = new ArrayList<>();
        casillas1.add(escritura1);
        casillas1.add(escritura2);
        casillas1.add(escritura3);

        when(tableroMock.getCasillas()).thenReturn(casillas1);

        tableroMock = Tablero.getPartida();

        int costeAlquiler1 = escritura1.CalcularAlquiler();

        int resultadoEsperado1 = 100;
        int resultadoObtenido1 = escritura1.CalcularAlquiler();

        assertEquals(alquileres[0], costeAlquiler1);

        System.out.println("Esperado: " + resultadoEsperado1 + " | Recibido: " + resultadoObtenido1);

        //nivel 0-pcia completa
        Escritura escritura = new Escritura();
        escritura.setProvincia("Test");
        escritura.setDueño(dueñoMock);
        escrituraMock.setNivel(0);

        List<Casilla> casillas = new ArrayList<>();
        casillas.add(escritura);
        casillas.add(escrituraMock);

        when(tableroMock.getCasillas()).thenReturn(casillas);

        int costeAlquiler = escrituraMock.CalcularAlquiler();

        int resultadoEsperado = 200;
        int resultadoObtenido = escrituraMock.CalcularAlquiler();

        assertEquals(alquileres[0]*2, costeAlquiler);
        System.out.println("Esperado: " + resultadoEsperado + " | Recibido: " + resultadoObtenido);
    }

    @Test
    void mostrarNivel() {

        //1 Campo(Nivel 0)
        escrituraMock.setNivel(0);
        String nivel0Esperado = "1 Campo";
        String nivel0Obtenido = escrituraMock.MostrarNivel();

        //1 Chacra(Nivel 1)
        escrituraMock.setNivel(1);
        String nivel1Esperado = "1 Chacra";
        String nivel1Obtenido = escrituraMock.MostrarNivel();

        //2 Chacras(Nivel 2)
        escrituraMock.setNivel(2);
        String nivel2Esperado = "2 Chacras";
        String nivel2Obtenido = escrituraMock.MostrarNivel();

        //3 Chacras(Nivel 3)
        escrituraMock.setNivel(3);
        String nivel3Esperado = "3 Chacras";
        String nivel3Obtenido = escrituraMock.MostrarNivel();

        //4 Chacras(Nivel 4)
        escrituraMock.setNivel(4);
        String nivel4Esperado = "4 Chacras";
        String nivel4Obtenido = escrituraMock.MostrarNivel();

        //1 Estancia (Nivel 5)
        escrituraMock.setNivel(5);
        String nivel5Esperado = "1 Estancia";
        String nivel5Obtenido = escrituraMock.MostrarNivel();

        assertEquals(nivel0Esperado, nivel0Obtenido);
        assertEquals(nivel1Esperado, nivel1Obtenido);
        assertEquals(nivel2Esperado, nivel2Obtenido);
        assertEquals(nivel3Esperado, nivel3Obtenido);
        assertEquals(nivel4Esperado, nivel4Obtenido);
        assertEquals(nivel5Esperado, nivel5Obtenido);

        System.out.println("(Nivel 0)- Esperado: " + nivel0Esperado + "- Recibido: " + nivel0Obtenido);
        System.out.println("(Nivel 1)- Esperado: " + nivel1Esperado + "- Recibido: " + nivel1Obtenido);
        System.out.println("(Nivel 2)- Esperado: " + nivel2Esperado + "- Recibido: " + nivel2Obtenido);
        System.out.println("(Nivel 3)- Esperado: " + nivel3Esperado + "- Recibido: " + nivel3Obtenido);
        System.out.println("(Nivel 4)- Esperado: " + nivel4Esperado + "- Recibido: " + nivel4Obtenido);
        System.out.println("(Nivel 5)- Esperado: " + nivel5Esperado + "- Recibido: " + nivel5Obtenido);
    }
}