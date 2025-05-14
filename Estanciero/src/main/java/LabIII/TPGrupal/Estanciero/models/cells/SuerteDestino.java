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
public class SuerteDestino extends Casilla {
    //Atributos
    private String descripcion;


    //Constructor
    public SuerteDestino(int id, String nombre, TipoCasilla tipo, String descripcion) {
        super(id, nombre, tipo);
        this.descripcion = descripcion;
    }

    public SuerteDestino() {
        this.descripcion = null;
    }


    //Métodos
    @Override
    //Realiza una accion sobre el jugador con la carta obtenida del mazo, luego de usarla la guarda si retorna "true"
    public void Accion(Jugador j) {
        Tablero partida = Tablero.getPartida();
        Mazo mazo;

        if (this.nombre == "SUERTE"){
            mazo = partida.getMazoSuerte();
        }
        else{
            mazo = partida.getMazoDestino();
        }

        consola.MostrarInformacion(this.descripcion);
        Carta carta = mazo.LevantarCarta();


        if (carta.UsarCarta(j)){
            mazo.GuardarCarta(carta);
        }
    }
}
