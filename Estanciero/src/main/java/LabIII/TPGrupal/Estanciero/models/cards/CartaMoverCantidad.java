package LabIII.TPGrupal.Estanciero.models.cards;

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
public class CartaMoverCantidad extends Carta {
    //Atributos
    private int valor;


    //Constructor
    public CartaMoverCantidad(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaMoverCantidad() {
        this.valor = 0;
    }


    //Métodos
    @Override
    public boolean UsarCarta(Jugador j) {
        Tablero partida = Tablero.getPartida();

        consola.MostrarInformacion(this.descripcion);

        //Obtengo la nueva posicion
        int posicion = j.getPosicion() + valor;

        //Mueve al jugador a la posicion asignada y realiza la accion de la casilla en la que cae
        partida.MoverJugador(posicion, j);
        partida.getCasillas().get(j.getPosicion()).Accion(j);

        return true;
    }
}
