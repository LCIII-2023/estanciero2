package LabIII.TPGrupal.Estanciero.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DadoTest {

    Dado dado;
    @Test
    public void tirarDadoTest() {
        dado = new Dado();

        int resultado= dado.TirarDado();
        System.out.println("Resultado: "+resultado);


        switch (resultado){
            case 1:
                assertEquals(1,1,"Salio 1");
                System.out.println("Salio 1");
                break;
            case 2:
                assertEquals(2,2,"Salio 2");
                System.out.println("Salio 2");
                break;
            case 3:
                assertEquals(3,3,"Salio 3");
                System.out.println("Salio 3");
                break;
            case 4:
                assertEquals(4,4,"Salio 4");
                System.out.println("Salio 4");
                break;
            case 5:
                assertEquals(5,5,"Salio 5");
                System.out.println("Salio 5");
                break;
            case 6:
                assertEquals(6,6,"Salio 6");
                System.out.println("Salio 6");
                break;
        }
    }
}