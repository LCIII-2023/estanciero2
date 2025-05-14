package LabIII.TPGrupal.Estanciero.models;

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
public class Dado {
    //Atributos
    private int valor;


    //Constructor
    public Dado(int valor) {
        this.valor = valor;
    }

    public Dado() {
        this.valor = 0; //Este constructor no hace falta, se queda por buena practica
    }


    //Métodos
    //Almacena y devuelve un numero aleatorio del 1 al 6
    public int TirarDado(){
        this.valor = new Random().nextInt(6)+1;

        return this.valor;
    }

}
