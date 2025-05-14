package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.models.enums.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeonTest {

    Peon peon;
    @Test
    void testToString() {
        peon = new Peon();
        String c = ""+"\u001B[0m"+"●"+"\u001B[0m" ;
        assertEquals(c,peon.toString());
        System.out.println("Esperado "+c+" Resultado: "+peon.toString());

    }
}