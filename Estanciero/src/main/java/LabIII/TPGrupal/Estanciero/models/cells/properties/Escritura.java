package LabIII.TPGrupal.Estanciero.models.cells.properties;

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
public class Escritura extends Propiedad {
    //Atributos
    private String provincia;
    private String zona;
    private int nivel;
    private int costeMejora;


    //Constructor
    public Escritura(int id, String nombre, TipoCasilla tipo, int precio, int[] alquileres, int valorHipoteca, boolean hipotecado, Jugador dueño, String provincia, String zona, int nivel, int costeMejora) {
        super(id, nombre, tipo, precio, alquileres, valorHipoteca, hipotecado, dueño);
        this.provincia = provincia;
        this.zona = zona;
        this.nivel = nivel;
        this.costeMejora = costeMejora;
    }

    public Escritura() {
        super();
        this.provincia = null;
        this.zona = null;
        this.nivel = 0;
        this.costeMejora = 0;
    }


    //Métodos
    @Override
    //Calcula y devuelve el coste que se debe cobrar al jugador que caiga en la casilla
    public int CalcularAlquiler(){
        Tablero partida = Tablero.getPartida();

        //Calcula el coste en base a la lista alquileres x el nivel (0 = Campo, 1 = 1Chacra, 5 = 1Estancia)
        int costeAlquiler = alquileres[nivel];

        //Revisa si el jugador posee todas las escrituras de la provincia, si es asi el coste x campo se duplica (solo el campo)
        if(nivel ==0){
            boolean provinciaCompleta = true;
            for (Casilla c : partida.getCasillas()){
                if (c instanceof Escritura e && e.getProvincia().equals(this.provincia)) {
                    if(!dueño.equals(e.getDueño())){
                        provinciaCompleta = false;
                        break;
                    }
                }
            }

            if(provinciaCompleta){
                costeAlquiler = costeAlquiler * 2;
            }
        }

        return costeAlquiler;
    }


    //Muestra como cadena de caracteres el nivel de la escritura, especificando si es campo, chacra o estancia
    public String MostrarNivel(){
        String[] niveles = new String[]{"1 Campo", "1 Chacra", "2 Chacras", "3 Chacras", "4 Chacras", "1 Estancia"};

        return niveles[nivel];
    }
}
