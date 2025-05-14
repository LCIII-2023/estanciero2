package LabIII.TPGrupal.Estanciero.models.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Color {
    //Enumeracion de colores en ANSI
    ROJO("\u001B[31m"),         //Errores
    VERDE("\u001B[32m"),        //Mensajes      -->  Cosas que requieren interaccion de jugador
    AZUL("\u001B[34m"),         //Acciones      -->  Descripciones de cartas, casillas, etc.
    AMARILLO("\u001B[33m"),     //Avisos        -->  Cosas que se realizan automaticamente
    CIAN("\u001B[36m"),         //Informacion   -->  Nombres de Casillas
    VIOLETA("\u001B[35m"),      //Datos         -->  Informacion del jugador
    GRIS("\u001B[37m"),         //Deshabilitado
    BLANCO("\u001B[0m"),        //Por defecto
    NEGRITA("\u001B[1m");


    //Atributos
    private final String color;


    //Constructor
    Color(String color) {
        this.color = color;
    }


    //Metodos
    @Override
    //Override del metodo toString
    public String toString() {
        return color;
    }


    //Devuelve una lista de String que contiene cada color seguido del nombre del mismo
    public static List<Color> Listar(){
        Color[] colores = Arrays.copyOf(Color.values(), 5); //Solo agrego los primeros 5 colores
        List<Color> lista = new ArrayList<>();

        //Rellena el array con los nombres de los colores
        lista.addAll(Arrays.asList(colores));

        return lista;
    }


    //Devuelve un arreglo de caracteres donde muestra el nombre del color en dicho color
    public static String[] MostrarOpciones(List<Color> lista){
        String[] colores = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            colores[i] = lista.get(i) + lista.get(i).name() + Color.BLANCO;
        }

        return colores;
    }

}
