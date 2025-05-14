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
public class CartaCobrarJugadores extends Carta {
    //Atributos
    private int valor;


    //Constructor
    public CartaCobrarJugadores(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaCobrarJugadores() {
        super();
        this.valor = 0;
    }


    //Métodos
    @Override
    //Resta un monto de todos los jugadores y se los da a uno en especifico
    public boolean UsarCarta(Jugador j){
        Tablero partida = Tablero.getPartida();
        int total = 0;

        consola.MostrarInformacion(this.descripcion);

        //descuenta el monto a todos los jugadores y los suma al total
        for (Jugador jugador: partida.getJugadores()) {
            if (jugador != j){
                partida.CambiarBalanceJugador(-valor, jugador);
                total += valor;
            }

        }

        //Agrega el total de dinero descontado del resto de jugadores
        partida.CambiarBalanceJugador(total, j);

        return true;
    }
}
