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
public class EstacionamientoLibre extends Casilla {
    //Atributos
    private String descripcion;


    //Constructor
    public EstacionamientoLibre(int id, String nombre, TipoCasilla tipo, String descripcion) {
        super(id, nombre, tipo);
        this.descripcion = descripcion;
    }

    public EstacionamientoLibre() {
        super();
        this.descripcion = null;
    }


    //Metodos
    @Override
    //
    public void Accion(Jugador j) {
        consola.MostrarInformacion(this.descripcion);
    }
}
