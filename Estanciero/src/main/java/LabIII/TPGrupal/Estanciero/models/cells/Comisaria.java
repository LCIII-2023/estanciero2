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
public class Comisaria extends Casilla {
    //Atributos
    private String descripcion;
    private List<Jugador> jugadores;
    

   //Constructor
    public Comisaria(int id, String nombre, TipoCasilla tipo, String descripcion, List<Jugador> jugadores) {
        super(id, nombre, tipo);
        this.descripcion = descripcion;
        this.jugadores = jugadores;
    }

    public Comisaria() {
        super();
        this.descripcion = null;
        this.jugadores = new ArrayList<>();
    }


    //Métodos
    @Override
    //Maneja la entrada y salida de los jugadores en la comisaria
    public void Accion(Jugador j){
        Tablero partida = Tablero.getPartida();

        if(jugadores.contains(j)){
            consola.MostrarInformacion("Se encuentra en la COMISARIA.\n\n" +
                    "No se podra mover ni cobrar Alquiler a menos que:\n" +
                    "1. " + Color.BLANCO+ "Saque dobles en la tirada.\n" + Color.AZUL +
                    "2. " + Color.BLANCO+ "Pague una fianza de $1000.\n" + Color.AZUL +
                    "3. " + Color.BLANCO+ "Use una carta para salir.\n" + Color.AZUL +
                    "4. " + Color.BLANCO+ "Espere hasta que se cumpla 1 turno." + Color.AZUL);

            //Tirada de dados
            partida.TirarDados();

            //Controla si ya cumplio sus turnos en la comisaria, salieron dados iguales o desea salir
            if(jugadores.get(jugadores.indexOf(j)).getTurnoEstado() == 0 || partida.getDado1().getValor() == partida.getDado2().getValor() || !ConsultarPermanencia(j)){
                Liberar(j);
                partida.MoverJugador(j.getPosicion() + partida.getDado1().getValor() + partida.getDado2().getValor(), j);
                partida.getCasillas().get(j.getPosicion()).Accion(j);
            }

            //Si no se debe de liberar se descuenta un turno, se informa y se termina el turno
            else {
                DescontarTurno(j);
            }
        }

        //Si no esta encerrado, se informa que solo esta de paso
        else {
            consola.MostrarInformacion(this.descripcion);
        }
    }


    //Agrega a un jugador a la comisaria
    public void Encerrar(Jugador j){
        Tablero partida = Tablero.getPartida();

        //Guarda a los jugadores en el mapa, los mueve hasta la casilla y modifica el estado
        jugadores.add(j);
        j.setTurnoEstado(1);
        partida.MoverJugador(this.id, j);
        j.setEstado(Estado.COMISARIA);

        consola.MostrarAviso("Se encerro al jugador " + j.getNombre() + " en COMISARIA. No podra cobrar alquileres");

        //Consulta si desean salir de la comisaria al momento de ser encerrados, se los libera en caso de que si
        if (!ConsultarPermanencia(j)){
            Liberar(j);
        }
    }


    //Remueve a un jugador de la lista de jugadores encerrados
    public void Liberar(Jugador j){
        jugadores.remove(j);
        j.setEstado(Estado.JUGANDO);
        j.setTurnoEstado(0);

        consola.MostrarAviso("Se libero al jugador " + j.getNombre() + " de la COMISARIA. Puede moverse libremente");
    }


    //Descuenta un turno aun jugador especifico
    public void DescontarTurno(Jugador j) {
        int valor = jugadores.get(jugadores.indexOf(j)).getTurnoEstado();
        valor --;
        j.setTurnoEstado(valor);

        consola.MostrarAviso("Al jugador " + j.getNombre() + " le quedan " + (valor+1) + " turnos en COMISARIA");
    }


    //Consulta al jugador si desea pagar la fianza para salir de inmediato (true = quedarse, false = salir)
    public boolean ConsultarPermanencia(Jugador j){
        Tablero partida = Tablero.getPartida();

        //Valida que pueda pagar la fianza o usar una carta
        boolean validarPagar = (j.getDinero() > 1000);
        boolean validarCarta = (!j.getCartas().isEmpty());

        //Elije con un 33% de probabilidad pagar la fianza, usar una carta para salir (si la tiene) o quedarse
        int opcion = new Random().nextInt(3);

        //Si es un usuario le consulta que quiere hacer para salir de la comisaria
        if (j instanceof JugadorNormal){
            String opcionPagar = (validarPagar ? Color.BLANCO : Color.ROJO ) + "Pagar fianza ($1000)" + Color.BLANCO;
            String opcionCarta = (validarCarta ? Color.BLANCO : Color.ROJO) + "Usar una carta (" + j.getCartas().size() + " cartas disponibles)" + Color.BLANCO;

            String[] opciones = new String[] {opcionPagar, opcionCarta, "Esperar"};
            while (true){
                opcion = consola.ConsultarMenu("¿Como desea salir de la COMISARIA?", opciones);

                if ((opcion == 1 && !validarPagar) || opcion == 2 && !validarCarta){
                    consola.MostrarError("La opcion seleccionada no es posible.\n");
                    continue;
                }

                break;
            }

        }

        //Si salio/eligio pagar la fianza se le descuenta el dinero y se devuelve false
        if(opcion == 1 && validarPagar){
            consola.MostrarMensaje(j.getNombre() + " pago la fianza de $1000 para salir de COMISARIA");
            partida.CambiarBalanceJugador(-1000, j);
            return false;
        }

        else if(opcion == 2 && validarCarta){
            Carta carta = j.getCartas().get(0);
            consola.MostrarMensaje(j.getNombre() + " uso una carta para salir de COMISARIA");
            if (carta.UsarCarta(j)){
                Mazo mazo = (carta.getId() <=16 ? partida.getMazoSuerte() : partida.getMazoDestino());
                mazo.GuardarCarta(carta);
            }
        }

        //Si no salio/eligio pagar la fianza devuelve true
        return true;
    }
}
