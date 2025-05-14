package LabIII.TPGrupal.Estanciero.models;

import LabIII.TPGrupal.Estanciero.console.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class Tablero {
    //Atributos
    private static Tablero partida;
    private Jugador jugadorActual;
    private List<Jugador> jugadores;
    private List<Casilla> casillas;
    private Dado dado1;
    private Dado dado2;
    private Mazo mazoSuerte;
    private Mazo mazoDestino;
    private int cantidadGanadora;
    private List<Jugador> resultado;


    //Constructor
    private Tablero(Jugador jugadorActual, List<Jugador> jugadores, List<Casilla> casillas, Dado dado1, Dado dado2, Mazo mazoSuerte, Mazo mazoDestino, int cantidadGanadora, List<Jugador> resultado) {
        this.jugadorActual = jugadorActual;
        this.jugadores = jugadores;
        this.casillas = casillas;
        this.dado1 = dado1;
        this.dado2 = dado2;
        this.mazoSuerte = mazoSuerte;
        this.mazoDestino = mazoDestino;
        this.cantidadGanadora = cantidadGanadora;
        this.resultado = resultado;
    }

    private Tablero() {
        this.jugadorActual = null;
        this.jugadores = new ArrayList<>();
        this.casillas = new ArrayList<>();
        this.dado1 = new Dado();
        this.dado2 = new Dado();
        this.mazoSuerte = new Mazo();
        this.mazoDestino = new Mazo();
        this.cantidadGanadora = -1;
        this.resultado = new ArrayList<>();
    }


    //Métodos
    //Singleton
    public static Tablero getPartida() {
        if (partida == null) {
            partida = new Tablero();
        }
        return partida;
    }


    //
    public boolean IniciarPartida(){
        boolean continuar = true;
        boolean guardarYSalir = false;

        consola.MostrarAviso("Iniciando partida...");

        while(jugadores.size() > 1 && !guardarYSalir){

            for (int i = 0; i < jugadores.size(); i++){
                jugadorActual = jugadores.get(i);

                //Muestra de informacion
                consola.MostrarInformacion("---------------[Turno de " + jugadorActual.getNombre() + "]---------------");

                //Retraso para mejor visibilidad de los turnos
                consola.AgregarRetraso(1500);

                consola.MostrarJugador(jugadorActual);

                //Control de turnos
                continuar = GestionarTurno(jugadorActual);

                //Si no desea continuar consulto como desea salir del juego
                if(!continuar){
                    int opcion = consola.ConsultarMenu("¿Desea guardar la partida antes de salir?", new String[]{"Si", "No"});
                    if (opcion == 1){
                        guardarYSalir = true;
                        consola.MostrarAviso("Terminando la ronda antes de guardar y salir...");
                        continue;
                    }

                    return false;
                }
            }

            consola.MostrarInformacion("-------------------------------------------------------------------");
            consola.MostrarAviso("Fin de la ronda...");
        }

        return guardarYSalir;
    }


    //Administra e informa sobre las acciones automaticas que se realizan en el turno especifico de un jugador pasado por parametros
    public boolean GestionarTurno(Jugador jugador){
        boolean continuar;
        int cantidadRepetidos = 0;
        int turnos = 1;

        do {
            //Logica de movimiento para el jugador
            if (jugador.getEstado() == Estado.JUGANDO){
                //Tirada de dados
                int tirada = TirarDados();

                //Logica para repetir tirada si los dados son iguales
                if (dado1.getValor() == dado2.getValor()) {
                    consola.MostrarAviso("Dados iguales. Puede tirar nuevamente");
                    turnos++;
                    cantidadRepetidos++;
                }

                //Movimiento de jugador
                MoverJugador(jugador.getPosicion() + tirada, jugador);
            }

            //Accion de la casilla y del jugador
            Casilla casillaActual = partida.getCasillas().get(jugador.getPosicion());
            casillaActual.Accion(jugador);
            continuar = jugador.Turno(casillaActual);

            //Si los dados se repiten 3 veces, se lo encierra en la COMISARIA
            if (cantidadRepetidos == 3){
                consola.MostrarAviso("Dados repetidos muchas veces. " + Color.NEGRITA + "Marche preso directamente" + Color.BLANCO);
                Comisaria comisaria = (Comisaria) casillas.get(14);
                comisaria.Encerrar(jugador);
            }

            //Muestra de informacion y descuenta el turno
            consola.MostrarJugador(jugador);
            turnos--;

        } while (turnos > 0 && jugador.getEstado() != Estado.COMISARIA);

        consola.MostrarAviso("Terminando Turno...");
        return continuar;
    }


    //
    public int TirarDados(){
        consola.MostrarAviso("Tirando dados...");
        int tirada = dado1.TirarDado() + dado2.TirarDado();

        consola.MostrarMensaje("Resultado = " + dado1.getValor() + " + " + dado2.getValor() + " (" + tirada + ")");

        return tirada;
    }


    //Modifica la posicion de un jugador para colocarlo en una posicion especifica
    public void MoverJugador(int posicion, Jugador jugador){
        //Corrige la posicion para que este dentro del rango de casillas (0 a casillas.size() (0 a 41))
        int nuevaPosicion = (posicion % this.casillas.size() + this.casillas.size()) % this.casillas.size();

        //Comprueba si debe cobrar por pasar por la salida
        if(nuevaPosicion != posicion && posicion > 0 && nuevaPosicion < jugador.getPosicion() && nuevaPosicion != 0){
            this.casillas.get(0).Accion(jugador);
        }

        //Informa la posicion a donde se movera al jugador,lo coloca en dicha casilla y muestra la casilla por consola
        consola.MostrarMensaje("Moviendo a " + jugador.getNombre() + " a la casilla " + nuevaPosicion);
        jugador.setPosicion(nuevaPosicion);
        consola.MostrarCasilla(partida.getCasillas().get(jugador.getPosicion()));
    }


    //Controla el pago de alquileres para los jugadores que caen en una casilla, descontando su dinero
    public void PagarAlquiler(Jugador j){
        //Obtengo la propiedad sobre la que se tiene que cobrar
        Propiedad p = (Propiedad) casillas.get(j.getPosicion());

        //Avisa que no se cobra alquiler si la propiedad esta hipotecada
        if(p.isHipotecado()){
            consola.MostrarAviso("La propiedad esta hipotecada. No paga alquiler");
        }

        //Avisa que no se cobra el alquiler si el jugador esta en comisaria
        else if (p.getDueño().getEstado() == Estado.COMISARIA){
            consola.MostrarAviso("El dueño de la propiedad esta en COMISARIA. No paga alquiler");
        }

        //Si no se cumplen los requisitos anteriores, se cobra el alquiler al jugador y se le paga al dueño
        else {
            //Guardo el coste del alquiler
            int costeAlquiler = p.CalcularAlquiler();

            //Descuenta el monto del alquiler y pago al dueño de la propiedad
            consola.MostrarAviso("Descontando dinero de alquiler...");
            CambiarBalanceJugador(-costeAlquiler, j);
            CambiarBalanceJugador(costeAlquiler, p.getDueño());
        }
    }


    //Suma o resta un monto de dinero a un jugador
    public void CambiarBalanceJugador(int valor, Jugador jugador){
        jugador.setDinero(jugador.getDinero() + valor);
        consola.MostrarAviso(String.format("%+d a " + jugador.getNombre(), valor));

        //Si el jugador se quedo sin dinero se lo elimina del juego
        if (jugador.getDinero() < 0){
            EliminarJugador(jugador);
        }

        //Si hay un cantidad ganadora y el jugador la alcanzo, se termina la partida
        if (cantidadGanadora != -1 && jugador.getDinero() >= cantidadGanadora){
            TerminarPartida();
        }
    }


    //Remueve a un jugador de la lista de jugadores, cambia su estado a bancarrota, lo agrega a la lista de resultado, vende todas sus propiedades y guarda sus cartas
    public void EliminarJugador(Jugador j){
        consola.MostrarAviso(Color.ROJO + "El jugador " + j.getNombre() + " esta eliminado" + Color.BLANCO);
        //Elimina al jugador de la lista de turnos
        jugadores.remove(j);

        //Cambia el estado a bancarrota y lo añade a la lista de resultados en la posición 0
        j.setEstado(Estado.BANCARROTA);
        resultado.add(0, j);

        //Pone el dueño de todas las propiedades como null y las elimina de su lista
        List<Propiedad> propiedades = j.ObtenerPropiedades();
        for (Propiedad propiedad : propiedades) {
            propiedad.setDueño(null);

            //Elimina todas las mejoras de las escrituras
            if(propiedad instanceof Escritura e){
                e.setNivel(0);
            }
        }
        j.ObtenerPropiedades().clear();

        //Elimina todas las cartas que tenga y las devuelve a su respectivo mazo
        List<Carta> cartas = j.getCartas();
        for (Carta carta : cartas) {
            if (carta.getId() < 16){
                this.mazoSuerte.GuardarCarta(carta);
            }
            else {
                this.mazoDestino.GuardarCarta(carta);
            }
        }
        j.getCartas().clear();
    }


    //Muestra el estado de la partida por consola
    public void MostrarTablero(){
        //Muestra el tablero y un resumen de los jugadores
        consola.MostrarTablero(casillas);
        consola.MostrarResumenJugadores(jugadores);

        //Consulta si se desea ver informacion sobre una casilla en especifico
        int opcion = consola.ConsultarMenu("¿Desea ver una casilla en especifico?", new String[]{"Si", "No"});

        //Si se quiere ver sobre una casilla en especifico consulta cual/es desea ver hasta tocar "Cancelar"
        if (opcion == 1){
            while (true){
                //Carga el listado de casillas para que seleccione
                String[] opciones = new String[casillas.size() + 1];
                for (int i = 0; i < casillas.size(); i++) {
                    opciones[i] = casillas.get(i).getNombre();
                }
                opciones[casillas.size()] = "Cancelar";

                //consulta cual desea ver
                int indice = consola.ConsultarMenu("¿Que casilla desea ver?", opciones);

                //si no eligio cancelar muestra la casilla seleccionada
                if (indice != opciones.length) {
                    consola.MostrarCasilla(casillas.get(indice-1));
                    continue;
                }

                break;
            }
        }
    }


    //Finaliza el ciclo de juego y ordena a los jugadores por "puntuacion"
    public void TerminarPartida(){
        //Informa del fin de la partida
        consola.MostrarAviso("Terminando partida...");

        //Recorre la lista de jugadores y los agrega por orden
        while (!jugadores.isEmpty()){

            //Agrega al jugador con menor dinero a la lista de resultado y lo remueve de la lista de jugadores
            int menor = 0;
            for(int i = 0; i < jugadores.size(); i++){
                if (jugadores.get(i).getDinero() < jugadores.get(menor).getDinero() ){
                    menor = i;
                }
            }

            //Lo agrego a resultado y lo remuevo de jugadores
            this.resultado.add(0, jugadores.remove(menor));
        }

        //Mostrar "tablero de resultados"
        consola.MostrarInformacion("Ganador: ");
        consola.MostrarJugador(resultado.get(0));
        consola.MostrarResumenJugadores(resultado);

        consola.MostrarMensaje("Gracias por jugar ♥");
        //Borro los datos de la partida
        partida = null;
    }

}
