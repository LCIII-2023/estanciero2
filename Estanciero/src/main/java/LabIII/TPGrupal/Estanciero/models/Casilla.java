package LabIII.TPGrupal.Estanciero.models;

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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Escritura.class, name = "Escritura"),
        @JsonSubTypes.Type(value = Ferrocarril.class, name = "Ferrocarril"),
        @JsonSubTypes.Type(value = Compañia.class, name = "Compania"),
        @JsonSubTypes.Type(value = Tributario.class, name = "Tributario"),
        @JsonSubTypes.Type(value = SuerteDestino.class, name = "SuerteDestino"),
        @JsonSubTypes.Type(value = Comisaria.class, name = "Comisaria"),
        @JsonSubTypes.Type(value = Descanso.class, name = "Descanso"),
        @JsonSubTypes.Type(value = MarchePreso.class, name = "MarchePreso"),
        @JsonSubTypes.Type(value = EstacionamientoLibre.class, name = "EstacionamientoLibre")
})
@Getter
@Setter
public abstract class Casilla implements iCasilla {
    //Atributos
    protected int id;
    protected String nombre;
    protected TipoCasilla tipo;


    //Constructor
    protected Casilla(int id, String nombre, TipoCasilla tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    protected Casilla() {
        this.id = 0;
        this.nombre = null;
        this.tipo = null;
    }


    //Métodos
    //Metodo Abstracto para las acciones especificas de cada una de las casillas
    public abstract void Accion(Jugador j);


    //ToDo: Quiza esto se puede optimizar y recorrer los jugadores, no las casillas al buscarlos
    public String MostrarPeones(int tamaño) {
        Tablero partida = Tablero.getPartida();
        StringBuilder peones = new StringBuilder();

        //Recorro la lista de jugadores, si la posicion coincide con la casilla los agrego
        for (Jugador j : partida.getJugadores()) {
            if (partida.getCasillas().get(j.getPosicion()) == this){
                peones.append(j.getPeon() + " ");
                tamaño -= 2;
            }
        }

        //Retorno todos los peones con el mismo espaciado a cada lado
        return " ".repeat(tamaño/2) + peones.toString() + " ".repeat(tamaño/2);
    }

}

