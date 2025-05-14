package LabIII.TPGrupal.Estanciero.models.interfaces;

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


public interface iJugador {
    //Metodos
    boolean Turno (Casilla c);

    //Principales
    boolean Comprar(Propiedad p, int precio);
    boolean Vender();
    boolean Mejorar();
    boolean Hipotecar();

    //Validaciones
    String ValidarCompra(Propiedad p);
    String ValidarVenta(Propiedad p);
    String ValidarCompraMejora(Propiedad p);
    String ValidarVentaMejora(Propiedad p);
    String ValidarHipoteca(Propiedad p);

    //Confirmaciones
    void ConfirmarCompra(Propiedad p, int precio);
    void ConfirmarVenta(Propiedad p, int precio, Jugador nuevoJugador);
    void ConfirmarMejora(Escritura e, int nivel);
    void ConfirmarHipoteca(Propiedad p);

}
