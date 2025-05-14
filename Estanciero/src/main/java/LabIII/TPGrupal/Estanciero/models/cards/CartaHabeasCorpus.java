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
public class CartaHabeasCorpus extends Carta {
    //Constructor
    public CartaHabeasCorpus(int id, String descripcion, TipoCarta tipo) {
        super(id, descripcion, tipo);
    }

    public CartaHabeasCorpus() {
        super();
    }


    //Métodos
    @Override
    //
    public boolean UsarCarta(Jugador j) {
        Tablero partida = Tablero.getPartida();
        Comisaria comisaria = (Comisaria) partida.getCasillas().get(14);

        consola.MostrarInformacion(this.descripcion);

        //Compruebo si el jugador ya esta encerrado
        if (comisaria.getJugadores().contains(j)){
            //Libero al jugador de la lista de jugadores en comisaria
            comisaria.Liberar(j);
            j.getCartas().remove(this);
        }

        //Si el jugador no esta encerrado, le doy la carta al jugador
        else {
            j.getCartas().add(this);
            consola.MostrarAviso("Se guardo la carta al jugador " + j.getNombre());
            return false;
        }

        return true;
    }
}
