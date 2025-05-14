package LabIII.TPGrupal.Estanciero.models.cells;

import LabIII.TPGrupal.Estanciero.console.*;
import LabIII.TPGrupal.Estanciero.controllers.*;
import LabIII.TPGrupal.Estanciero.dtos.*;
import LabIII.TPGrupal.Estanciero.entities.*;
import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cards.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import LabIII.TPGrupal.Estanciero.models.interfaces.*;
import LabIII.TPGrupal.Estanciero.models.players.*;
import LabIII.TPGrupal.Estanciero.repositories.*;
import LabIII.TPGrupal.Estanciero.services.*;
import LabIII.TPGrupal.Estanciero.services.impl.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class Propiedad extends Casilla {
    //Atributos
    protected int precio;
    protected int[] alquileres;
    protected int valorHipoteca;
    protected boolean hipotecado;
    protected Jugador dueño;


    //Constructor
    public Propiedad(int id, String nombre, TipoCasilla tipo, int precio, int[] alquileres, int valorHipoteca, boolean hipotecado, Jugador dueño) {
        super(id, nombre, tipo);
        this.precio = precio;
        this.alquileres = alquileres;
        this.valorHipoteca = valorHipoteca;
        this.hipotecado = hipotecado;
        this.dueño = dueño;
    }

    public Propiedad() {
        super();
        this.precio = 0;
        this.alquileres = new int[6];
        this.valorHipoteca = 0;
        this.hipotecado = false;
        this.dueño = null;
    }


    //Métodos
    @Override
    //Consulta la disponibilidad de la propiedad y permite la compra de la misma o impone el alquiler sobre el jugador
    public void Accion(Jugador j){
        Tablero partida = Tablero.getPartida();

        //Controla si la propiedad tiene dueño y ofrece comprarla si no es asi
        if(dueño == null){
            j.Comprar(this, this.getPrecio());
        }

        //Si tiene dueño y no es el jugador que cayo en ella, se descuenta el alquiler
        else if (this.dueño != j){
            partida.PagarAlquiler(j);
        }
    }


    //Calcula y devuelve el coste que se debe cobrar al jugador que caiga en la casilla
    public abstract int CalcularAlquiler();

}
