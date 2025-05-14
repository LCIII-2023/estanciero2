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
public class CartaMarchePreso extends Carta {
    //Constructor
    public CartaMarchePreso(int id, String descripcion, TipoCarta tipo) {
        super(id, descripcion, tipo);
    }

    public CartaMarchePreso() {
        super();
    }


    //Métodos
    @Override
    //
    public boolean UsarCarta(Jugador j){
        Tablero partida = Tablero.getPartida();

        consola.MostrarInformacion(this.descripcion);

        //Llama al metodo "encerrar" de la casilla de comisaria
        Comisaria comisaria = (Comisaria) partida.getCasillas().get(14);
        comisaria.Encerrar(j);

        return true;
    }
}
