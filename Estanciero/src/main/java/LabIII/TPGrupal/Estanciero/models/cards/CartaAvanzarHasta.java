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
public class CartaAvanzarHasta extends Carta {
    //Atributos
    private int valor;


    //Constructores
    public CartaAvanzarHasta(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaAvanzarHasta() {
        super();
        this.valor = 0;
    }


    //Métodos
    @Override
    //Mueve a un jugador hasta una posicion y paga $5000 si debe pasar por la salida al intentar avanzar
    public boolean UsarCarta(Jugador j){
        Tablero partida = Tablero.getPartida();

        consola.MostrarInformacion(this.descripcion);

        //Mueve al jugador hasta la posicion y realiza la accion de la casilla en la que cae
        partida.MoverJugador(valor, j);
        partida.getCasillas().get(j.getPosicion()).Accion(j);

        return true;
    }

}
