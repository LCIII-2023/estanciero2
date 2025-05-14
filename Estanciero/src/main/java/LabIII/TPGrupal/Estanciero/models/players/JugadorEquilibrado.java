package LabIII.TPGrupal.Estanciero.models.players;

import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class JugadorEquilibrado extends JugadorVirtual {
    //Constructor
    public JugadorEquilibrado(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon, List<String> provinciasPrioritarias) {
        super(nombre, dinero, posicion, estado, turnoEstado, cartas, peon, provinciasPrioritarias);
    }

    public JugadorEquilibrado() {
        super();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Calcula si el jugador deberia comprar la propiedad, resta el coste de la propiedad al jugador y se la asigna a su lista
    public boolean Comprar(Propiedad p, int precio) {
        //Comprueba que tenga dinero suficiente y el precio no sobrepase el base de la propiedad
        if(this.dinero >= precio && precio <= p.getPrecio()){

            //Comprueba si la propiedad es una escritura
            if (p instanceof Escritura e){

                //Comprueba que la provincia sea de su interés o salga 0 (33%)
                if (this.provinciasPrioritarias != null && this.provinciasPrioritarias.contains(e.getProvincia()) || new Random().nextInt(3) == 0){
                    ConfirmarCompra(p, precio);

                    return true;
                }
            }

            //Comprueba si la propiedad es un ferrocarril
            else if (p instanceof Ferrocarril){
                ConfirmarCompra(p, precio);

                return true;
            }
        }

        return false;
    }


    @Override
    //ToDo?
    public boolean Vender() {
        return false;
    }


    @Override
    //Sube el nivel de la escritura si es posible
    public boolean Mejorar() {
        double porcentajeAceptable = (double) (50 * dinero) / 100;
        boolean mejoraRealizada = false;

        //Recorre todas sus propiedades
        for (Propiedad p : this.ObtenerPropiedades()) {
            if (p instanceof Escritura e){

                //Mientras sea posible mejorar la propiedad
                while(ValidarCompraMejora(e) == null && (e.getCosteMejora() < porcentajeAceptable || PorcentajeVendidas(75))){
                    ConfirmarMejora(e, 1);

                    mejoraRealizada = true;
                }
            }
        }

        return mejoraRealizada;
    }


    @Override
    //ToDo?
    public boolean Hipotecar(){
        return false;
    }


    //-------------------------AUXILIARES-------------------------//
    //Devuelve true si se han comprado más del porcentaje ingresado por parametros
    public boolean PorcentajeVendidas(int porcentaje){
        Tablero partida = Tablero.getPartida();
        int cantidadPropiedades = 0;
        int cantidadVendidas = 0;

        //Recorre la lista de casillas para calcular la cantidad de propiedades y la cantidad de propiedades vendidas
        for (Casilla c : partida.getCasillas()){
            if (c instanceof Propiedad p){
                cantidadPropiedades++;
                if (p.getDueño() != null){
                    cantidadVendidas++;
                }
            }
        }

        //Por regla de 3 devuelve true si cantidad vendidas es mayor porcentaje ingresado
        if (((cantidadVendidas * 100) / cantidadPropiedades) > porcentaje){
            return true;
        }

        return false;
    }

}
