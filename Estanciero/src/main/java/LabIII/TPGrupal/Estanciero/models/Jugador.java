package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.console.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import LabIII.TPGrupal.Estanciero.models.interfaces.*;
import LabIII.TPGrupal.Estanciero.models.players.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JugadorNormal.class, name = "JugadorNormal"),
        @JsonSubTypes.Type(value = JugadorAgresivo.class, name = "JugadorAgresivo"),
        @JsonSubTypes.Type(value = JugadorEquilibrado.class, name = "JugadorEquilibrado"),
        @JsonSubTypes.Type(value = JugadorConservador.class, name = "JugadorConservador"),
})

@Getter
@Setter
public abstract class Jugador implements iJugador {
    //Atributos
    protected String nombre;
    protected int dinero;
    protected int posicion;
    protected Estado estado;
    protected int turnoEstado;
    protected List<Carta> cartas;
    protected Peon peon;


    //Propiedades
    public List<Propiedad> ObtenerPropiedades() {
        Tablero partida = Tablero.getPartida();
        List<Propiedad> propiedades = new ArrayList<>();

        //Busca todas las casillas de tipo propiedad en el tablero que pertenezcan al jugador
        for (Casilla casilla : partida.getCasillas()) {
            if (casilla instanceof Propiedad p && p.getDueño() == this) {
                propiedades.add(p);
            }
        }

        return propiedades;
    }


    //Constructor
    protected Jugador(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.posicion = posicion;
        this.estado = estado;
        this.turnoEstado = turnoEstado;
        this.cartas = cartas;
        this.peon = peon;
    }

    protected Jugador() {
        this.nombre = "";
        this.dinero = 35000;
        this.posicion = 0;
        this.estado = Estado.JUGANDO;
        this.turnoEstado = 0;
        this.cartas = new ArrayList<>();
        this.peon = new Peon();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Controla el cliclo de acciones posibles para los jugadores, siendo estas la compra, venta y mejora de propiedades
    public abstract boolean Turno(Casilla c);


    @Override
    //Resta el coste de la propiedad al jugador y se la asigna a su lista
    public abstract boolean Comprar(Propiedad p, int precio);


    @Override
    //Agrega el valor de la propiedad al jugador y lo elimina de su lista
    public abstract boolean Vender();


    @Override
    //Aumenta o reduce el nivel de escrituras
    public abstract boolean Mejorar();


    @Override
    //Intercambia el estado de la hipoteca de las propiedades
    public abstract boolean Hipotecar();


    //-------------------------VALIDACIONES-------------------------//
    @Override
    //Devuelve true si el jugador puede comprar alguna propiedad en especifico
    public String ValidarCompra(Propiedad p){
        //Comprueba si el jugador tiene dinero suficiente para comprar la propiedad
        if (this.dinero < p.getPrecio()){
            return "Dinero insuficiente para comprar\n";
        }

        //Comprueba si la propiedad esta disponible
        if (p.getDueño() != null){
            return "La propiedad ya tiene dueño\n";
        }

        //Comprueba que el jugador no este en comisaria o en bancarrota
        if (this.estado.equals(Estado.COMISARIA) || this.estado.equals(Estado.BANCARROTA)){
            return "No puede comprar estando en la COMISARIA o en BANCARROTA\n";
        }

        //Si paso por todas las comprobaciones anteriores, retorna null
        return null;
    }


    @Override
    //Devuelve true si el jugador puede vender alguna de sus propiedades
    public String ValidarVenta(Propiedad p){
        //Comprueba que el dueño de la propiedad que se quiere vender sea del jugador que la quiere vender (por si acaso)
        if (p.getDueño() != this){
            return "No se puede vender una propiedad ajena\n";
        }

        //Comprueba que la escritura no este hipotecada
        if (p.isHipotecado()){
            return "No se puede vender una propiedad hipotecada\n";
        }

        //Comprueba si es una escritura y que el nivel de la escritura junto con todas aquellas de la misma provincia no sea mayor a 0
        if (p instanceof Escritura e){

            //Recorre las propiedades del jugador
            for (Propiedad propiedad : ObtenerPropiedades()){
                if (propiedad instanceof Escritura escritura){

                    //Si la provincia coincide con la que se quiere vender se verifica que el resto de la misma provincia esten en nivel 0 o retorna false
                    if (Objects.equals(escritura.getProvincia(), e.getProvincia()) && escritura.getNivel() > 0){
                        return "No se puede vender una escritura de una provincia con mejoras\n";
                    }
                }
            }
        }

        //Si paso por todas las comprobaciones anteriores entonces se puede vender la propiedad y retorna null
        return null;
    }


    @Override
    //Devuelve true si es posible comprar una mejora para una propiedad en especifico
    public String ValidarCompraMejora(Propiedad p){
        Tablero partida = Tablero.getPartida();
        List<Casilla> casillas = partida.getCasillas();

        //
        if (!(p instanceof Escritura e)){
            return p.getNombre() + " no es una escritura\n";
        }

        //Compruebo que la escritura que se quiere mejorar no este hipotecada
        if (e.isHipotecado()){
            return "No se puede mejorar una propiedad hipotecada\n";
        }

        //Comprueba si el jugador tiene dinero suficiente para la mejora
        if (this.dinero < e.getCosteMejora()){
            return "Dinero insuficiente\n";
        }

        //Comprueba si la Escritura e está dentro del rango de niveles posibles (0 al 5)
        if (e.getNivel() >= 5){
            return "La escritura ya esta mejorada al maximo\n";
        }

        //Comprueba si el dueño posee todas las escrituras de la provincia
        for(Casilla c : casillas){
            if (c instanceof Escritura escritura) {

                if(Objects.equals(escritura.getProvincia(), e.getProvincia()) && escritura.getDueño() != this){
                    return "No se puede mejorar hasta completar la provincia\n";
                }
            }
        }

        //Comprueba que el nivel de la escritura que quiere mejorar no sea mayor en 1 que las del resto de la provincia
        for(Casilla c : casillas){
            if (c instanceof Escritura escritura && Objects.equals(escritura.getProvincia(), e.getProvincia())) {
                //Si la diferencia entre los niveles de escrituras da -1, la escritura que se intenta mejorar no es valida para mejorar
                if((escritura.getNivel() - e.getNivel()) == -1){
                    return "El nivel de mejora no puede ser mayor en uno al del resto de provincias\n";
                }
            }
        }

        //Si paso por todas las validaciones, entonces si puede mejorar
        return null;
    }


    @Override
    //Devuelve true si es posible que el jugador venda o reduzca en 1 el nivel de una escritura
    public String ValidarVentaMejora(Propiedad p){
        Tablero partida = Tablero.getPartida();
        List<Casilla> casillas = partida.getCasillas();

        //
        if (!(p instanceof Escritura e)){
            return p.getNombre() + " no es una escritura\n";
        }

        //Compruebo que la escritura que se quiere modificar no este hipotecada
        if (e.isHipotecado()){
            return "No se pueden vender mejoras de una propiedad hipotecada\n";
        }

        //Comprueba si la Escritura e está dentro del rango de niveles posibles (1 al 5)
        if (e.getNivel() <= 0){
            return "La escritura no tiene mejoras que vender\n";
        }

        //Comprueba que el nivel de la escritura que quiere desmejorar no sea menor en 1 que las del resto de la provincia
        for(Casilla c : casillas){
            if (c instanceof Escritura escritura && Objects.equals(escritura.getProvincia(), e.getProvincia())) {
                //Si la diferencia entre los niveles de escrituras da +1, la escritura que se intenta desmejorar no es valida para desmejorar
                if((escritura.getNivel() - e.getNivel()) == 1){
                    return "El nivel de mejora no puede ser menor en uno al del resto de provincias\n";
                }
            }
        }

        //Si paso por todas las validaciones, entonces si puede desmejorar
        return null;
    }


    @Override
    //Comprueba si es posible hipotecar o remover la hipoteca sobre una propiedad
    public String ValidarHipoteca(Propiedad p){
        //Valida si tiene el dinero suficiente para pagar la hipoteca de una propiedad
        if(p.isHipotecado() && this.dinero < p.getValorHipoteca()){
            return "Dinero insuficiente para pagar hipoteca\n";
        }

        //Valida que no haya ninguna mejora sobre una escritura si se la quiere hipotecar
        if(p instanceof Escritura e && e.getNivel() > 0){
            return "No puede hipotecar una propiedad con mejoras\n";
        }

        //Si es posible realizar alterar el estado de la hipoteca devuelve true
        return null;
    }


    //-------------------------CONFIRMACIONES-------------------------//
    @Override
    //Realiza la compra de una propiedad
    public void ConfirmarCompra(Propiedad p, int precio){
        Tablero partida = Tablero.getPartida();

        partida.CambiarBalanceJugador(-precio, this);
        p.setDueño(this);

        consola.MostrarMensaje(this.nombre + " compro " + p.getNombre() + " por $" + precio);
    }


    @Override
    //Realiza la venta de una propiedad
    public void ConfirmarVenta(Propiedad p, int precio, Jugador nuevoDueño){
        Tablero partida = Tablero.getPartida();

        partida.CambiarBalanceJugador(precio, this);
        p.setDueño(nuevoDueño);

        consola.MostrarMensaje(this.nombre + " vendio " + p.getNombre() + " por $" + precio);
    }


    @Override
    //Modifica el nivel de una escritura
    public void ConfirmarMejora(Escritura e, int nivel){
        Tablero partida = Tablero.getPartida();

        partida.CambiarBalanceJugador(e.getCosteMejora() * -nivel, this);
        e.setNivel(e.getNivel() + nivel);

        consola.MostrarMensaje(String.format(this.nombre + " cambio " + e.getNombre() + " (%+d nivel). Ahora tiene " + e.MostrarNivel(), nivel));
    }


    @Override
    //Alterna el estado de la hipoteca de una propiedad
    public void ConfirmarHipoteca(Propiedad p){
        Tablero partida = Tablero.getPartida();

        partida.CambiarBalanceJugador(p.isHipotecado() ? -p.getValorHipoteca() : p.getValorHipoteca(), this);
        p.setHipotecado(!p.isHipotecado());

        consola.MostrarMensaje(this.nombre + (p.isHipotecado() ? " hipoteco " : " pago la hipoteca de ") + p.getNombre() + " por $" + p.getValorHipoteca());
    }

}