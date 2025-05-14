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
public abstract class JugadorVirtual extends Jugador {
    //Atributos
    protected List<String> provinciasPrioritarias;


    //Constructor
    protected JugadorVirtual(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon, List<String> provinciasPrioritarias) {
        super(nombre, dinero, posicion, estado, turnoEstado, cartas, peon);
        this.provinciasPrioritarias = provinciasPrioritarias;
    }

    protected JugadorVirtual() {
        super();
        this.provinciasPrioritarias = new ArrayList<>();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Se encarga de ofrecerle a los jugadores virtuales la logica para comprar, vender y mejorar propiedades
    public boolean Turno(Casilla c){
        boolean puedeComprar = false;
        boolean puedeVender = false;
        boolean puedeMejorar = false;

        //Valida que sea posible comprar la casilla sobre la que esta parado
        if (c instanceof Propiedad p && ValidarCompra(p) == null){
            puedeComprar = true;
        }

        //Valida que hayan propiedades para vender o mejorar
        if (!ObtenerPropiedades().isEmpty()){

            //Valida que sea posible vender al menos una propiedad
            for (Propiedad p : ObtenerPropiedades()){
                if (ValidarVenta(p) == null){
                    puedeVender = true;
                    break;
                }
            }

            //Valida que sea posible mejorar al menos una escritura
            for (Propiedad p : ObtenerPropiedades()){
                if (p instanceof Escritura e && ValidarCompraMejora(e) == null){
                    puedeMejorar = true;
                    break;
                }
            }
        }

        //Acciones posibles de los bots
        if (puedeComprar){
            Propiedad p = (Propiedad) c;
            this.Comprar(p, p.getPrecio());
        }
        if (puedeVender){
            //Todo: Hay que implementar la venta de propiedades x parte de los bots???
        }
        if (puedeMejorar){
            this.Mejorar();
        }

        //Cuando termino de realizar todas las acciones retorna true (los bots no pueden retornar false para terminar la partida)
        return true;
    }

}
