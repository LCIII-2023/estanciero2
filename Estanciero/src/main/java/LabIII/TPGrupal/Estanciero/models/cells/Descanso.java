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

import java.util.*;


@Getter
@Setter
public class Descanso extends Casilla {
    //Atributos
    private String descripcion;
    private List<Jugador> jugadores;


    //Constructor
    public Descanso(int id, String nombre, TipoCasilla tipo, String descripcion, List<Jugador> jugadores) {
        super(id, nombre, tipo);
        this.descripcion = descripcion;
        this.jugadores = jugadores;
    }

    public Descanso() {
        super();
        this.descripcion = null;
        this.jugadores = new ArrayList<>();
    }


    //Métodos
    @Override
    //Controla el ingreso y salidas de los jugadores en la casilla, para que puedan permanecer si lo desean
    public void Accion(Jugador j) {
        Tablero partida = Tablero.getPartida();

        //Comprueba si el jugador no está y lo agrega
        if(!jugadores.contains(j)) {
            AgregarJugador(j);
        }

        //Si le quedan turnos y se desea quedar, consulta la tirada de dados, y lo libero o lo deja quedarse (si sale doble se va igualmente)
        else if(jugadores.get(jugadores.indexOf(j)).getTurnoEstado() > 0 && ConsultarPermanencia(j)) {

            //Si los dados salen duplicados lo libera y mueve independientemente de su desicion
            partida.TirarDados();
            if (partida.getDado1().getValor() == partida.getDado2().getValor()) {
                consola.MostrarAviso("Se ve obligado a salir por dados repetidos");
                SoltarJugador(j);
                return;
            }

            DescontarTurno(j);
        }

        //Si no le quedan turnos o desea salir, se lo libera
        else {
            partida.TirarDados();
            SoltarJugador(j);
        }
    }


    //Agrega jugadores a la lista para que puedan permanecer
    public void AgregarJugador(Jugador j) {
        //Agrega al jugador a la lista e informa por consola
        jugadores.add(j);
        j.setTurnoEstado(2);
        j.setEstado(Estado.DESCANSO);

        consola.MostrarAviso("El Jugador " + j.getNombre() + " esta en DESCANSO");
    }


    //Elimina a los jugadores de la lista para que continuen moviendose por el tablero
    public void SoltarJugador(Jugador j) {
        Tablero partida = Tablero.getPartida();

        //
        jugadores.remove(j);
        j.setEstado(Estado.JUGANDO);
        j.setTurnoEstado(0);

        consola.MostrarAviso("El Jugador " + j.getNombre() + " salio de DESCANSO");

        int tirada = partida.getDado1().getValor() + partida.getDado2().getValor();

        partida.MoverJugador(j.getPosicion() + tirada, j);
        partida.getCasillas().get(j.getPosicion()).Accion(j);
    }


    //Descuenta un turno a un jugador especifico
    public void DescontarTurno(Jugador j) {
        int valor = jugadores.get(jugadores.indexOf(j)).getTurnoEstado();
        valor --;
        j.setTurnoEstado(valor);
    }


    //Consulta al jugador si desea permanecer o no en descanso (true = quedarse, false = salir)
    public boolean ConsultarPermanencia(Jugador j){
        //Suelta al jugadorVirtual con un 33% de probabilidad
        int opcion = new Random().nextInt(3);

        //Pregunta por menu al usuariosi se desea quedar o no
        if (j instanceof JugadorNormal){
            opcion = consola.ConsultarMenu("¿Desea permanecer en DESCANSO? (Le quedan " + j.getTurnoEstado() + " turnos)", new String[]{"Si", "No"});
        }

        //Desea quedarse en la casilla
        if (opcion == 1) {
            consola.MostrarMensaje("El jugador " + j.getNombre() + " eligio quedarse en DESCANSO");
            return true;
        }

        //Desea salir de la casilla
        return false;
    }
    

}
