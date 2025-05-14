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
public class Mazo {
    //Atributos
    private List<Carta> cartas;


    //Constructor
    public Mazo(List<Carta> cartas){
        this.cartas = cartas;
    }

    public Mazo() {
        this.cartas = new ArrayList<>();
    }


    //Métodos
    //Remueve una carta del mazo y para ser usada
    public Carta LevantarCarta(){
        Carta carta = cartas.remove(cartas.size()-1);
        consola.MostrarMensaje("Se levanto una carta del mazo");

        return carta;
    }


    //Agrega una carta al final del mazo
    public void GuardarCarta(Carta carta){
        cartas.add(0, carta);
        consola.MostrarMensaje("Se guardo una carta al final del mazo");
    }


    //Mescla las cartas de forma aleatoria
    public void MezclarMazo(){
        consola.MostrarMensaje("Mezclando mazo...");
        Collections.shuffle(cartas);
    }
}
