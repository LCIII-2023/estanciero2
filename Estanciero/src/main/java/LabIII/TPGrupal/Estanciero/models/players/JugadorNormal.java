package LabIII.TPGrupal.Estanciero.models.players;

import LabIII.TPGrupal.Estanciero.console.*;
import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
public class JugadorNormal extends Jugador {
    //Constructor
    public JugadorNormal(String nombre, int dinero, int posicion, Estado estado, int turnoEstado, List<Carta> cartas, Peon peon) {
        super(nombre, dinero, posicion, estado, turnoEstado, cartas, peon);
    }

    public JugadorNormal() {
        super();
    }


    //Métodos
    //-------------------------PRINCIPALES-------------------------//
    @Override
    //Maneja las posibles acciones que puede realizar el Usuario, preguntando por consola la siguiente accion y llamando al metodo correspondiente
    public boolean Turno(Casilla c){
        Tablero partida = Tablero.getPartida();

        while (true){
            //Instancia los condicionales
            boolean puedeComprar = (c instanceof Propiedad p && ValidarCompra(p) == null);
            boolean puedeVender = false;
            boolean puedeMejorar = false;
            boolean puedeHipotecar = false;

            //Valida que hayan propiedades para vender o mejorar
            if (!ObtenerPropiedades().isEmpty()){

                //Valida que sea posible vender al menos una propiedad
                for (Propiedad p : ObtenerPropiedades()){
                    if (ValidarVenta(p) == null){
                        puedeVender = true;
                        break;
                    }
                }

                //Valida que sea posible mejorar al menos una escritura
                for (Propiedad p : ObtenerPropiedades()){
                    if (p instanceof Escritura e && (ValidarCompraMejora(e) == null || ValidarVentaMejora(e) == null)){
                        puedeMejorar = true;
                        break;
                    }
                }

                //Valida que se pueda hipotecar al menos una propiedad
                for (Propiedad p : ObtenerPropiedades()){
                    if (ValidarHipoteca(p) == null){
                        puedeHipotecar = true;
                        break;
                    }
                }
            }

            //Consulta al usuario que desea realizar
            String[] opciones = {
                    (puedeComprar ? Color.BLANCO : Color.ROJO) + "Comprar propiedad" + Color.BLANCO,
                    (puedeVender ? Color.BLANCO : Color.ROJO) + "Vender propiedades" + Color.BLANCO,
                    (puedeMejorar ? Color.BLANCO : Color.ROJO) + "Comprar/Vender mejoras" + Color.BLANCO,
                    (puedeHipotecar ? Color.BLANCO : Color.ROJO) + "Hipotecar/Deshipotecar Propiedad" + Color.BLANCO,
                    "Terminar turno",
                    "Ver Tablero",
                    "Salir",
                    "Debug: Encerrar a todos",
                    "Debug: Descanso a todos",
                    "Debug: Adquirir todo"
            };
            int opcion = consola.ConsultarMenu("¿Que desea realizar?", opciones);

            //Si eligio comprar y puede hacerlo entonces llamo al metodo iniciar la compra, si no informa porque no se puede hacer la compra
            if (opcion == 1){
                if (c instanceof Propiedad p){
                    String errorCompra = ValidarCompra(p);
                    if (errorCompra == null){
                        Comprar(p, p.getPrecio());
                    }
                    else {
                        consola.MostrarError(errorCompra);
                    }
                }
                else {
                    consola.MostrarError("Solo puede comprar propiedades\n");
                }
            }

            //Si eligio vender y puede hacerlo entonces llamo al metodo iniciar la venta
            else if (opcion == 2){
                if (puedeVender){
                    Vender();
                    continue;
                }
                consola.MostrarError("No posee propiedades disponibles para vender\n");
            }

            //Si eligio mejorar y puede hacerlo entonces llamo al metodo iniciar la mejora
            else if (opcion == 3){
                if (puedeMejorar){
                    Mejorar();
                    continue;
                }
                consola.MostrarError("No posee propiedades en las que pueda comprar o vender mejoras\n");
            }

            //Si eligio comprar y puede hacerlo entonces llamo al metodo para hipotecar
            else if (opcion == 4){
                if (puedeHipotecar){
                    Hipotecar();
                    continue;
                }
                consola.MostrarError("No posee propiedades disponibles para hipotecar o pagar la hipoteca\n");
            }

            //Corto el ciclo while y termino el turno del usuario
            else if (opcion == 5) {
                break;
            }

            //Muestro por consola un vistazo general del tablero con informacion relevante del resto de los jugadores
            else if (opcion == 6) {
                partida.MostrarTablero();
            }

            //Devuelvo false para informar a los metodos y clases "padres" que se desea salir del juego
            else if (opcion == 7) {
                return false;
            }

            //FixMe: Estas opciones son temporales para realizar pruebas, se eliminaran mas adelante
            else if (opcion == 8){
                Comisaria comisaria = (Comisaria) partida.getCasillas().get(14);
                for (Jugador j : partida.getJugadores()){
                    comisaria.Encerrar(j);
                }
            }
            else if (opcion == 9){
                Descanso descanso = (Descanso) partida.getCasillas().get(21);
                for (Jugador j : partida.getJugadores()){
                    descanso.Accion(j);
                    j.setPosicion(21);
                }
            }
            else if (opcion == 10){
                for (Casilla casilla : partida.getCasillas()){
                    if (casilla instanceof Propiedad p){
                        p.setDueño(this);
                    }
                }
            }
        }
        return true;
    }


    @Override
    //Resta el coste de la propiedad al jugador y se la asigna a su lista
    public boolean Comprar(Propiedad p, int precio) {
        //Compueba si tiene el dinero suficiente para comprar la propiedad
        if(precio <= this.dinero) {

            //Consulta si desea comprar la propiedad
            int opcion = consola.ConsultarMenu("¿Desea comprar " + p.getNombre() + " por $" + precio + "?", new String[]{"Si", "No"});
            if (opcion == 1){
                ConfirmarCompra(p, precio);

                return true;
            }
        }

        return false;
    }


    @Override
    //Agrega el valor de la propiedad al jugador y lo elimina de su lista
    public boolean Vender() {
        String[] opciones = new String[ObtenerPropiedades().size() + 1];

        //Carga la lista de opciones con las propiedades para vender
        for (int i = 0; i < ObtenerPropiedades().size(); i++) {
            opciones[i] = (ValidarVenta(ObtenerPropiedades().get(i)) == null ? Color.BLANCO : Color.ROJO) + ObtenerPropiedades().get(i).getNombre() + Color.BLANCO;
        }
        opciones[ObtenerPropiedades().size()] = "Cancelar";

        //
        while (true) {
            //Consulta la propiedad y guarda el indice de la seleccionada
            int indice = consola.ConsultarMenu("¿Que propiedad desea vender?", opciones) - 1;

            if (indice == ObtenerPropiedades().size()){
                break;
            }

            String error = ValidarVenta(ObtenerPropiedades().get(indice));

            //
            if (error == null){
                //obtengo el comprador de la propiedad, si es null se vende al banco
                Jugador comprador = ObtenerComprador();
                Propiedad p = ObtenerPropiedades().get(indice);

                //Consulto el precio al que se quiere vender la propiedad
                int precio = p.getPrecio();
                if (comprador != null) {
                    precio = consola.ConsultarNumero("¿Por cuanto desea vender " + p.getNombre() + "? (Precio base: $" + p.getPrecio() + ")", 0, (p.getPrecio() * 10));
                }

                //Consulta confirmacion por la venta de la propiedad
                int opcion = consola.ConsultarMenu("¿Desea vender " + p.getNombre() + " por $" + precio + "?", new String[]{"Si", "No"});
                if (opcion == 1) {

                    //Si el comprador es el banco o el comprador compro la propiedad entonces, se confirma la venta
                    if (comprador == null || comprador.Comprar(p, precio)) {
                        ConfirmarVenta(p, precio, comprador);

                        return true;
                    }

                    //Informa de que el jugador a quien se queria vender rechazo la venta
                    consola.MostrarAviso("El jugador " + comprador.getPeon().getColor() + comprador.getNombre() + Color.BLANCO + " rechazo la compra de " + p.getNombre() + " por $" + precio);
                }
            }

            //
            else {
                consola.MostrarError(error);
            }
        }

        return false;
    }


    @Override
    //Sube el nivel de la escritura si es posible
    public boolean Mejorar() {
        String[] opciones = new String[ObtenerPropiedades().size() + 1];

        //Consulta sobre que desea realizar sobre la escritura
        int opcion = consola.ConsultarMenu("¿Qué desea realizar sobre la escritura?", new String[]{"Construir mejoras", "Vender mejoras", "Cancelar"});

        //Si no se eligio "Cancelar" se continua consultando que propiedad quiere cambiar
        if (opcion != 3) {

            //Carga la lista de propiedades para consultar
            for (int i = 0; i < ObtenerPropiedades().size(); i++) {
                if (ObtenerPropiedades().get(i) instanceof Escritura e){
                    String[] validaciones = new String[] {ValidarCompraMejora(e), ValidarVentaMejora(e)};

                    opciones[i] = (validaciones[opcion-1] == null ? Color.BLANCO : Color.ROJO) + e.getNombre() + " - Nivel: " + e.MostrarNivel() + Color.BLANCO;
                    continue;
                }
                opciones[i] = (Color.ROJO + ObtenerPropiedades().get(i).getNombre() + Color.BLANCO);
            }
            opciones[ObtenerPropiedades().size()] = "Cancelar";

            //Continua consultando hasta que se seleccione cancelar o se mejora una escritura
            while (true) {
                //Consulta el indice de la propiedad que se desea mejorar
                int indice = consola.ConsultarMenu("¿Que escritura desea modificar?", opciones) - 1;

                //
                if (indice == ObtenerPropiedades().size()){
                    break;
                }

                //
                String error = ((opcion == 1) ? ValidarCompraMejora(ObtenerPropiedades().get(indice)) : ValidarVentaMejora(ObtenerPropiedades().get(indice)));

                //
                if (error == null){
                    Escritura e = (Escritura) ObtenerPropiedades().get(indice);

                    //Consulta confirmacion por la venta de la propiedad
                    int confirmacion = consola.ConsultarMenu("¿Desea " + ((opcion == 1) ? "mejorar " : "vender la mejora de ") + e.getNombre() + " por $" + e.getCosteMejora() + "?", new String[]{"Si", "No"});
                    if (confirmacion == 1) {
                        ConfirmarMejora(e, ((opcion == 1) ? 1 : -1));

                        return true;
                    }
                }

                //Si no se puede modificar la propiedad seleccionada, se informa y consulta nuevamente
                else {
                    consola.MostrarError(error);
                }
            }
        }

        return false;
    }


    @Override
    public boolean Hipotecar() {
        //Carga la lista de propiedades para consultar que propiedad desea Hipotecar o Deshipotecar
        String[] opciones = new String[ObtenerPropiedades().size() + 1];
        for (int i = 0; i < ObtenerPropiedades().size(); i++) {
            Propiedad p = ObtenerPropiedades().get(i);

            //Obtenemos el estado de la propiedad
            String estado = p.isHipotecado() ? "( Hipotecada )" : Color.GRIS + "( No Hipotecada )";

            //Agregamos el nombre y el estado a la lista de opciones
            opciones[i] = (ValidarHipoteca(p) == null ? Color.BLANCO : Color.ROJO) + p.getNombre() + " - " + estado + Color.BLANCO;
        }
        opciones[ObtenerPropiedades().size()] = "Cancelar";

        //Consultamos el indice de la propiedad
        int indice = consola.ConsultarMenu("¿Que escritura desea Hipotecar/Deshipotecar?", opciones) - 1;

        //Compueba si puede Hipotecar o Deshipotecar la propiedad elegida
        if(indice != ObtenerPropiedades().size() && (ValidarHipoteca(ObtenerPropiedades().get(indice)) == null)) {
            Propiedad p = ObtenerPropiedades().get(indice);

            //Consulta si se desea realizar el cambio de estado sobre la hipoteca
            int opcion = consola.ConsultarMenu("¿Esta seguro que desea"+ (p.isHipotecado() ? " pagar la hipoteca de " : " hipotecar ") + p.getNombre() + " por $" + p.getValorHipoteca() + "?", new String[]{"Si", "No"});
            if (opcion == 1){
                ConfirmarHipoteca(p);

                return true;
            }
        }

        return false;
    }


    //-------------------------AUXILIARES-------------------------//
    //Devuelve un objeto Jugador que representa a quien se le ofrece la posibilidad de comprar la propiedad que se intenta vender
    public Jugador ObtenerComprador(){
        Tablero partida = Tablero.getPartida();

        //Consulta si se desea vender la propiedad al banco o a un jugador
        int opcion = consola.ConsultarMenu("¿A quien desea venderle la propiedad?", new String[]{"Al Banco", "A un jugador"});

        //Si se selecciona vender al banco, se retorna null
        if (opcion == 1){
            return null;
        }

        //Carga una lista de candidatos a comprar la propiedad, excluyendo al dueño (Marcado en rojo)
        String[] opcionesJugadores = new String[partida.getJugadores().size()];
        for (int i = 0; i < partida.getJugadores().size(); i++){
            if (partida.getJugadores().get(i) == this){
                opcionesJugadores[i] = Color.ROJO + partida.getJugadores().get(i).getNombre() + Color.BLANCO;
            }
            else {
                opcionesJugadores[i] = partida.getJugadores().get(i).getNombre();
            }
        }

        //Consulta a quien le desea vender la propiedad evitando que se seleccione a si mismo
        int respuesta = consola.ConsultarMenu("¿A quien desea vender la propiedad?", opcionesJugadores) - 1;
        while (partida.getJugadores().get(respuesta) == this){
            consola.MostrarError("No puede venderse la propiedad a si mismo\n");
            respuesta = consola.ConsultarMenu("¿A quien desea vender la propiedad?", opcionesJugadores) - 1;
        }

        //Returna el jugador a quien se quiere vender o "null" si se quiere vender al Banco
        return partida.getJugadores().get(respuesta);
    }

}
