package LabIII.TPGrupal.Estanciero.models.cells.properties;

import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Ferrocarril extends Propiedad {
    //Constructor
    public Ferrocarril(int id, String nombre, TipoCasilla tipo, int precio, int[] alquileres, int valorHipoteca, boolean hipotecado, Jugador dueño) {
        super(id, nombre, tipo, precio, alquileres, valorHipoteca, hipotecado, dueño);
    }

    public Ferrocarril() {
        super();
    }


    //Métodos
    @Override
    //Calcula y devuelve el coste que se debe cobrar al jugador que caiga en la casilla
    public int CalcularAlquiler(){
        int contador = 0;

        //Calcula cuantos ferrocarriles posee el dueño
        for (Propiedad p : getDueño().ObtenerPropiedades()){
            if (p instanceof Ferrocarril) {
                contador++;
            }
        }

        //Calcula el coste en base a la lista alquileres -1 (por empezar en indice 0)
        int costeAlquiler = alquileres[contador-1];

        return costeAlquiler;
    }



}
