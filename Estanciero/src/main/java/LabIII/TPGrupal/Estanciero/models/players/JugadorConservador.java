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
public class JugadorConservador extends JugadorVirtual {
    //Constructor
    public JugadorConservador(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon, List<String> provinciasPrioritarias) {
        super(nombre, dinero, posicion, estado, turnoEstado, cartas, peon, provinciasPrioritarias);
    }

    public JugadorConservador() {
        super();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Calcula si el jugador deberia comprar la propiedad, resta el coste de la propiedad al jugador y se la asigna a su lista
    public boolean Comprar(Propiedad p, int precio) {
        //Comprueba que tenga dinero suficiente
        if(this.dinero >= precio && precio <= p.getPrecio()){
            if (p instanceof Escritura e){

                //Comprueba que la provincia sea de su interés o salga 0 (20%)
                if (this.provinciasPrioritarias != null && this.provinciasPrioritarias.contains(e.getProvincia()) || new Random().nextInt(5) == 0){
                    ConfirmarCompra(p, precio);

                    return true;
                }
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
    //Sube el nivel de todas las escrituras que sean posibles
    public boolean Mejorar() {
        double porcentajeAceptable = (double) (30 * dinero) / 100;
        boolean mejoraRealizada = false;

        //Recorre todas sus propiedades
        for (Propiedad p : this.ObtenerPropiedades()) {
            if (p instanceof Escritura e){

                //Mientras sea posible mejorar la propiedad, se mejora
                while(ValidarCompraMejora(e) == null && e.getCosteMejora() < porcentajeAceptable){
                    ConfirmarMejora(e, 1);
                    porcentajeAceptable = (double) (30 * dinero) / 100;

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

}
