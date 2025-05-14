package LabIII.TPGrupal.Estanciero.models.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Forma {
    //Enumeracion formas para los peones
    CIRCULO("●"),
    CUADRADO("■"),
    TRIANGULO("▲"),
    CORAZON("♥"),
    DIAMANTE("♦");


    //Atributos
    private final String forma;


    //Constructor
    Forma(String forma) {
        this.forma = forma;
    }


    //Metodos
    //Override del toString
    @Override
    public String toString() {
        return forma;
    }


    //Devuelve una lista de String que contiene cada forma seguido del nombre de la misma
    public static List<Forma> Listar(){
        Forma[] formas = Forma.values();
        List<Forma> lista = new ArrayList<>();

        //Rellena el array con los nombres de los colores
        lista.addAll(Arrays.asList(formas));

        return lista;
    }


    //Devuelve un arreglo de cadena de caracteres donde se muestra la forma seguida del nombre
    public static String[] MostrarOpciones(List<Forma> lista){
        String[] formas = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            formas[i] = lista.get(i) + " " + lista.get(i).name();
        }

        return formas;
    }

}
