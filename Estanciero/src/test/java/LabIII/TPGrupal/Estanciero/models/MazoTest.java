package LabIII.TPGrupal.Estanciero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MazoTest {

    @Mock
    private Carta cartaMock;

    @Mock
    private Mazo mazoMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLevantarCarta() {
        List<Carta> cartas = new ArrayList<>();
        cartas.add(cartaMock);
        Mazo mazo = new Mazo(cartas);


        Carta carta = mazo.LevantarCarta();

        assertEquals(cartaMock, carta);
        assertTrue(mazo.getCartas().isEmpty());

    }

    @Test
    public void testGuardarCarta() {
        Mazo mazo = new Mazo();

        mazo.GuardarCarta(cartaMock);

        assertFalse(mazo.getCartas().isEmpty());
        assertEquals(cartaMock, mazo.getCartas().get(0));

    }

    @Test
    public void testMezclarMazo() {
        List<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cartas.add(mock(Carta.class));
        }
        List<Carta> cartas2 = new ArrayList<>(cartas);
        mazoMock = new Mazo(cartas);
        Mazo mazo = new Mazo(cartas2);

        mazo.MezclarMazo();
        assertNotEquals(mazo.getCartas().get(0), mazoMock.getCartas().get(0));
    }
}