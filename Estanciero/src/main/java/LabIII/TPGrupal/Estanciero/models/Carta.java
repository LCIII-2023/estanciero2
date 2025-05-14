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
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartaTransaccion.class, name = "Transaccion"),
        @JsonSubTypes.Type(value = CartaMoverCantidad.class, name = "MoverCantidad"),
        @JsonSubTypes.Type(value = CartaAvanzarHasta.class, name = "AvanzarHasta"),
        @JsonSubTypes.Type(value = CartaRetrocederHasta.class, name = "RetrocederHasta"),
        @JsonSubTypes.Type(value = CartaCobrarJugadores.class, name = "CobrarJugadores"),
        @JsonSubTypes.Type(value = CartaMultaSuerte.class, name = "MultaSuerte"),
        @JsonSubTypes.Type(value = CartaMarchePreso.class, name = "MarchePreso"),
        @JsonSubTypes.Type(value = CartaHabeasCorpus.class, name = "HabeasCorpus"),
        @JsonSubTypes.Type(value = CartaMultaMejora.class, name = "MultaMejora")
})
@Getter
@Setter
public abstract class Carta implements iCarta {
    //Atributos
    protected int id;
    protected String descripcion;
    protected TipoCarta tipo;


    //Constructor
    public Carta(int id, String descripcion, TipoCarta tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public Carta() {
        this.id = 0;
        this.descripcion = null;
        this.tipo = null;
    }


    //Métodos abstractos
    public abstract boolean UsarCarta(Jugador j);

}
