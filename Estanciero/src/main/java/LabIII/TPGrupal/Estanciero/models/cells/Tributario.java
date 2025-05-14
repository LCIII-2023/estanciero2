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
public class Tributario extends Casilla {
    //Atributos
    private String descripcion;
    private int monto;


    //Constructor
    public Tributario(int id, String nombre, TipoCasilla tipo, String descripcion, int monto) {
        super(id, nombre, tipo);
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public Tributario() {
        this.descripcion = null;
        this.monto = 0;
    }


    //Métodos
    //Suma o resta dinero a un jugador especifico
    public void Accion(Jugador j) {
        Tablero partida = Tablero.getPartida();

        //Muestra un mensaje por consola
        consola.MostrarInformacion(this.descripcion);

        //Actualiza el balance del jugador
        partida.CambiarBalanceJugador(monto, j);
    }

}
