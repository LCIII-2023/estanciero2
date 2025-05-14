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

import java.util.*;


@Getter
@Setter
public class CartaMultaSuerte extends Carta {
    //Atributos
    private int valor;


    //Constructor
    public CartaMultaSuerte(int id, String descripcion, TipoCarta tipo, int valor) {
        super(id, descripcion, tipo);
        this.valor = valor;
    }

    public CartaMultaSuerte() {
        this.valor = 0;
    }


    //Métodos
    @Override
    public boolean UsarCarta(Jugador j) {
        Tablero partida = Tablero.getPartida();
        int opcion;

        consola.MostrarInformacion(this.descripcion);

        //Consulta al jugadorNormal que desea realizar
        if(j instanceof JugadorNormal){
            opcion = consola.ConsultarMenu("Que desea realizar", new String[]{"Pagar Multa", "Levantar Carta"});
        }

        //Decide con un 50/50 si paga multa o levanta carta
        else{
            opcion = new Random().nextInt(2)+1;
        }

        if(opcion == 1) {
            partida.CambiarBalanceJugador(this.valor, j);
        }
        else{
            Carta carta = partida.getMazoSuerte().LevantarCarta();
            if (carta.UsarCarta(j)){
                partida.getMazoSuerte().GuardarCarta(carta);
            }
        }

        return true;
    }
}
