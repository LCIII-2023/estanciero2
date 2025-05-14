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
public class CartaTransaccion extends Carta {
    //Atributos
    private int valor;


    //Constructor
    public CartaTransaccion(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaTransaccion() {
        this.valor = 0;
    }


    //Métodos
    @Override
    //Suma dinero al jugador pasado por parametros segun lo indicado en valor (que puede ser positivo o negativo)
    public boolean UsarCarta(Jugador j) {
        Tablero partida = Tablero.getPartida();

        consola.MostrarInformacion(this.descripcion);

        partida.CambiarBalanceJugador(valor, j);

        return true;
    }
}
