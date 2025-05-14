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
public class JugadorAgresivo extends JugadorVirtual {
    //Constructor
    public JugadorAgresivo(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon, List<String> provinciasPrioritarias) {
        super(nombre, dinero, posicion, estado, turnoEstado, cartas, peon, provinciasPrioritarias);
    }

    public JugadorAgresivo() {
        super();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Calcula si el jugador deberia comprar la propiedad, resta el coste de la propiedad al jugador y se la asigna a su lista
    public boolean Comprar(Propiedad p, int precio) {
        //Comprueba que tenga dinero suficiente y que el precio no supere el 200% del precio original
        if(this.dinero >= precio && precio <= (p.getPrecio()*2)){

            //Comprueba si ya tiene todas las provincias de la zona de preferencia o si otro jugador compró alguna de ellas
            if (ProvinciasCompletas() || ProvinciasRobadas()) {
                ConfirmarCompra(p, precio);

                return true;
            }

            //Comprueba que la propiedad sea una escritura de sus provincias prioritarias
            else if (p instanceof Escritura e){
                if (this.provinciasPrioritarias.contains(e.getProvincia())){
                    ConfirmarCompra(p, precio);

                    return true;
                }
            }

            //Comprueba si la propiedad es un ferrocarril o una compañia
            else if (p instanceof Ferrocarril || p instanceof Compañia){
                ConfirmarCompra(p, precio);

                return true;
            }
        }

        //Si no se cumplen las condiciones anteriores, retorna false, indicando que no se compro
        return false;
    }


    @Override
    //ToDo?
    public boolean Vender() {
        return false;
    }


    @Override
    //Sube el nivel de todas las escrituras que sean posibles
    public boolean Mejorar() {
        boolean mejoraRealizada = false;

        //Recorre todas sus propiedades
        for (Propiedad p : this.ObtenerPropiedades()) {
            if (p instanceof Escritura e){

                //Mientras sea posible mejorar la propiedad, se mejora
                while(ValidarCompraMejora(e) == null){
                    ConfirmarMejora(e, 1);

                    mejoraRealizada = true;
                }
            }
        }

        //Si se realizo una mejora, devuelve true, caso contrario, devuelve false
        return mejoraRealizada;
    }


    @Override
    //ToDo?
    public boolean Hipotecar(){
        return false;
    }


    //-------------------------AUXILIARES-------------------------//
    //Revisa si otro jugador compro al menos una propiedad de sus provincias prioritarias
    private boolean ProvinciasRobadas() {
        Tablero partida = Tablero.getPartida();

        //Recorre la lista de casillas donde coincidan aquellas escrituras de su lista de prioridad
        for (Casilla c : partida.getCasillas()) {
            if (c instanceof Escritura e && provinciasPrioritarias.contains(e.getProvincia())){

                //Si la propiedad tiene dueño y no es el, devuelve true
                if (e.getDueño() != null && e.getDueño() != this){
                    return true;
                }
            }
        }

        //Si no hay otro dueño ademas de el en sus provincias de prioridad, devuelve false
        return false;
    }


    //Revisa si ya adquirio todas las propiedades de sus provincias prioritarias
    private boolean ProvinciasCompletas(){
        Tablero partida = Tablero.getPartida();

        //Recorre la lista de casillas, si en alguna de sus provincias prioritarias el no es el dueño, entonces retorna false
        for (Casilla c : partida.getCasillas()) {
            if (c instanceof Escritura e && provinciasPrioritarias.contains(e.getProvincia()) && e.getDueño() != this){
                return false;
            }
        }

        //Si el es el dueño es todas sus provincias prioritarias, devuelve true
        return true;
    }

}
