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
public class CartaMultaMejora extends Carta {
    //Atributos
    private int valor;


    //Constructor
    public CartaMultaMejora(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaMultaMejora() {
        this.valor = 0;
    }


    //Métodos
    @Override
    public boolean UsarCarta(Jugador j) {
        Tablero partida = Tablero.getPartida();

        consola.MostrarInformacion(this.descripcion);

        //Obtengo la cantidad de mejoras que tenga compradas
        int totalMejoras = 0;

        for (Propiedad p : j.ObtenerPropiedades()) {
            if(p instanceof Escritura e){
                totalMejoras += e.getNivel();
            }
        }

        //Modifica el dinero del jugador
        partida.CambiarBalanceJugador((totalMejoras * this.valor), j);

        return true;
    }
}
