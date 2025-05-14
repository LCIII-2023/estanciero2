package LabIII.TPGrupal.Estanciero.models.cells.properties;

import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Compañia extends Propiedad {
    //Constructor
    public Compañia(int id, String nombre, TipoCasilla tipo, int precio, int[] alquileres, int valorHipoteca, boolean hipotecado, Jugador dueño) {
        super(id, nombre, tipo, precio, alquileres, valorHipoteca, hipotecado, dueño);
    }

    public Compañia() {
        super();
    }


    //Métodos
    @Override
    //Calcula y devuelve el coste que se debe cobrar al jugador que caiga en la casilla
    public int CalcularAlquiler(){
        Tablero partida = Tablero.getPartida();
        int contador = 0;

        //Calcula cuantas compañias posee el dueño
        for (Propiedad p : getDueño().ObtenerPropiedades()){
            if (p instanceof Compañia) {
                contador++;
            }
        }

        //Crea los dados y los tira para sacar


        //Calcula el coste en base a la la suma de la tirada de dados * lista alquileres -1 (por empezar en indice 0)
        int costeAlquiler = ((partida.getDado1().getValor() + partida.getDado2().getValor()) * alquileres[contador-1]);

        return costeAlquiler;
    }

}
