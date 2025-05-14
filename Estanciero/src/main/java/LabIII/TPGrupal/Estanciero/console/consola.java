package LabIII.TPGrupal.Estanciero.console;

import LabIII.TPGrupal.Estanciero.models.*;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.*;
import LabIII.TPGrupal.Estanciero.models.enums.*;

import java.util.*;


public class consola {
    //Metodos
    //-------------------------MUESTRA DE MENSAJES-------------------------//
    //Muestra un mensaje bajo el nombre de "JUEGO" en color verde
    public static void MostrarMensaje(String mensaje){
        System.out.println(Color.VERDE + "\nJUEGO: "+ Color.BLANCO + mensaje);
    }


    //Muestra un mensaje en azul sobre acciones realizadas por la partida
    public static void MostrarInformacion(String mensaje) {
        System.out.println("\n" + Color.AZUL + mensaje.toUpperCase() + Color.BLANCO);
    }


    //Muestra un mensaje en amarillo sobre avisos dentro de la partida
    public static void MostrarAviso(String mensaje){
        System.out.println(Color.AMARILLO + "\nAVISO: " + Color.BLANCO + mensaje);
    }


    //Muestra un mensaje en rojo sobre errores en las entradas por consola
    public static void MostrarError(String mensaje) {
        System.out.print(Color.ROJO + "\nError...\n"+ mensaje + Color.BLANCO);
    }


    //-------------------------MUESTRA DE DATOS-------------------------//
    //Muestra los datos de un jugador (Nombre, dinero, posicion y propiedades)
    public static void MostrarJugador(Jugador j){
        System.out.println("\n[ " + Color.NEGRITA  + j.getNombre() + Color.BLANCO + " - " + j.getPeon() + " ]");

        System.out.println(Color.VIOLETA + "Dinero: $" + j.getDinero() + Color.BLANCO);
        System.out.println(Color.VIOLETA + "Posicion: " + j.getPosicion() + Color.BLANCO);

        System.out.println(Color.VIOLETA + "Cartas: " + j.getCartas().size() + Color.BLANCO);

        System.out.println(Color.VIOLETA + "Propiedades (" + j.ObtenerPropiedades().size() + "): " + Color.BLANCO);
        for (Propiedad p : j.ObtenerPropiedades()) {
            System.out.println(Color.CIAN + "  -" + p.getNombre() + " | Alquiler $" + p.CalcularAlquiler() + (p instanceof Escritura e ? " (" + e.MostrarNivel() + ")" : "") + Color.BLANCO);
        }
    }


    //Muestra los datos relevantes de una casilla en especifico
    public static void MostrarCasilla(Casilla c){
        System.out.printf("%n+----------------------------------------+%n");
        System.out.println("|" + Color.CIAN + CentrarTexto(c.getNombre(), 40) + Color.BLANCO + "|");
        System.out.printf("%-20s  %20s %n", "|", "|");

        //Muestra el precio y dueño si es una propiedad
        if(c instanceof Propiedad p){
            System.out.println("|" + Color.VIOLETA + CentrarTexto("Precio: $" + p.getPrecio(), 40) + Color.BLANCO + "|");
            if(p.getDueño() != null){
                System.out.println("|" + Color.VIOLETA + CentrarTexto("Dueño: " + p.getDueño().getNombre(), 40) + Color.BLANCO + "|");
                System.out.println("|" + Color.VIOLETA + CentrarTexto("Alquiler: $" + p.CalcularAlquiler(), 40) + Color.BLANCO + "|");
            }
            else {
                System.out.println("|" + Color.VIOLETA + CentrarTexto("Dueño: " + "-", 40) + Color.BLANCO + "|");
            }

            //Muestra el nivel si es una escritura
            if (p instanceof Escritura e) {
                System.out.println("|" + Color.VIOLETA + CentrarTexto("Nivel: " + e.MostrarNivel(), 40) + Color.BLANCO + "|");
            }
        }

        System.out.printf("%-20s" + Color.VIOLETA + "%02d" + Color.BLANCO + "%20s %n", "|", c.getId(), "|");
        System.out.printf("+----------------------------------------+%n");
    }


    //Muestra informacion general del tablero y cada jugador
    public static void MostrarTablero(List<Casilla> casillas){
        consola.MostrarMensaje("Mostrando lista de casilla completas:");

        //Muestra todas las casillas con su ID, peones de cada jugador y el color indicando a quien le pertenece
        for (int f = 0; f < casillas.size(); f += 7) {
            //Instancia de los valores del string y las distintas filas
            List<Object> valores = new ArrayList<>();
            String filaSuperiorInferior = "";
            String filaId = "";
            String filaPeones = "";

            //Calcula el limite superior de las casillas a mostrar en esta fila (En caso de que la cantidad max no sea multiplo de 7 (No es nuestro caso igualmente))
            int limite = Math.min(f + 7, casillas.size());

            //Agrego los datos de cada columna en la fila que estoy recorriendo (f = fila de casilla / j = columna de casilla)
            for (int c = f; c < limite; c++) {
                Color colorCasilla = ((casillas.get(c) instanceof Propiedad p ? (p.getDueño() != null ? p.getDueño().getPeon().getColor() : Color.GRIS) : Color.NEGRITA));
                String barra = (colorCasilla+"|"+Color.BLANCO);

                filaSuperiorInferior += (colorCasilla + "+----------------+ " + Color.BLANCO);
                filaId += (barra + "       %02d       " + barra + " ");
                filaPeones += (barra + "%16s" + barra + " ");
            }

            //Agrego un salto de linea al final de cada fila
            filaSuperiorInferior += "%n";
            filaId += "%n";
            filaPeones += "%n";

            //Agrega todos los valores de las IDs
            for (int c = f; c < limite; c++) {
                valores.add(casillas.get(c).getId());
            }

            //Agrega todos los peones
            for (int c = f; c < limite; c++) {
                valores.add(casillas.get(c).MostrarPeones(16));
            }

            // Formatear e imprimir la salida
            System.out.printf(
                    filaSuperiorInferior + filaId + filaPeones + filaSuperiorInferior + "%n",
                    valores.toArray()
            );
        }

    }


    //
    public static void MostrarResumenJugadores(List<Jugador> jugadores){
        consola.MostrarMensaje("Mostrando datos generales de la partida:");

        //Muestra los datos de los jugadores
        System.out.println("+------------------------------------------------------------------------------------------------+");
        System.out.printf("| " +
                Color.NEGRITA + CentrarTexto("Nombre", 30) + Color.BLANCO + " | " +
                Color.NEGRITA + CentrarTexto("Dinero", 10) + Color.BLANCO + " | " +
                Color.NEGRITA + CentrarTexto("Posicion", 8) + Color.BLANCO + " | " +
                Color.NEGRITA + CentrarTexto("Propiedades", 11) + Color.BLANCO + " | " +
                Color.NEGRITA + CentrarTexto("Cartas", 10) + Color.BLANCO + " | " +
                Color.NEGRITA + CentrarTexto("Estado", 10) + Color.BLANCO + " |%n");
        System.out.println("+--------------------------------+------------+----------+-------------+------------+------------+");
        for (Jugador j : jugadores){
            Color color = j.getPeon().getColor();
            System.out.printf("|"+color+" %-30s"+Color.BLANCO +" | $%-9d | %02d%6s | %-11d | %-10d | %-10s |%n", j.getNombre(), j.getDinero(), j.getPosicion(), "", j.ObtenerPropiedades().size(), j.getCartas().size(), j.getEstado());
        }
        System.out.println("+------------------------------------------------------------------------------------------------+");
    }


    //Muestra por consola toda la informacion sobre como jugar al juego
    public static void MostrarReglas(){
        consola.MostrarMensaje("Mostrando reglas del juego:");

        consola.MostrarInformacion("Introduccion:");
        System.out.println("En este juego los jugadores compiten entre sí para adquirir propiedades estratégicas y construir sobre ellas para " +
                "\naumentar su valor y generar ingresos. El juego se desarrolla a lo largo de varias rondas, y el jugador " +
                "\nque logre acumular la mayor cantidad de riqueza al final del juego es declarado el ganador.\n"
        );

        consola.MostrarInformacion("Tablero:");
        System.out.println("El juego cuenta con un tablero de 42 casillas y 32 cartas. Las casillas estan numeradas del 0 al 41 " +
                "\ndonde los jugadores pueden caer. Dependiendo el tipo de casilla se le forzara o habilitaran distintas" +
                "\nopciones a realizar. Las cartas se dividen en 16 de \"Suerte\" y 16 de \"Destino\", donde cada carta tiene" +
                "\nun efecto distinto sobre el jugador que levante la carta.\n"
        );

        consola.MostrarInformacion("Jugadores:");
        System.out.println("Ademas del USUARIO se puede contar con hasta 3 tipos de jugadores virtuales, cada uno con sus cualidades" +
                "\nunicas:\n" +
                "\n -Jugador Agresivo: Busca maximizar el retorno de inversion rapidamente, incluso a costa de correr" +
                "\n  mayores riesgos. (Provincias prioritarias: Tucuman, Córdoba y Buenos Aires).\n" +

                "\n -Jugador Equilibrado: Busca un equilibrio entre la acumulacion de propiedades y la construcción" +
                "\n  de mejoras. (Provincias prioritarias: Mendoza, Santa Fe y Tucuman).\n" +

                "\n -Jugador Conservador: Tiende a ser cauteloso y prioriza la acumulación de propiedades de bajo costo." +
                "\n  (Provincias prioritarias: Formosa, Río Negro y Salta).\n"
        );

        consola.MostrarInformacion("Casillas:");
        System.out.println("Hay 8 tipos de casillas en las que los jugadores pueden caer:\n" +

                "\n -Propiedades: Abarcan la mayor cantidad del tablero, y son todas aquellas que los jugadores pueden" +
                "\n  adquirir gastando el dinero que posean para que cuando otro jugador caiga sobre ellas estos deban " +
                "\n  pagar alquiler al dueño. A su vez se sub-dividen en 3 tipos:\n" +

                "\n   -Escrituras: Son aquellas con una provincia y una zona, se pueden construir mejoras para aumentar " +
                "\n    el coste de alquiler.\n" +

                "\n   -Ferrocarriles: Existen 4 de ellos en el tablero, repartidos uniformemente, el coste de alquiler " +
                "\n    aumenta con la cantidad de Ferrocarriles que posea el dueño.\n" +

                "\n   -Compañias: Existen 3 en todo el tablero, el coste de alquiler varia segun la cantidad que posea " +
                "\n    el dueño y el valor de la ultima tirada de dados.\n" +

                "\n -Comisaria: Previene el movimiento de los jugadores encerrados, aquellos en este estado no podran cobrar" +
                "\n  el alquiler de sus propiedades hasta pagar la fianza o esperar unos turnos para ser liberados.\n" +

                "\n -Descanso: Permite a los jugadores que caigan permanecer alli por una cantidad de turnos mientras" +
                "\n  siguen cobrando los alquileres de sus propiedades.\n" +

                "\n -Estacionamiento Libre: No tiene ningun efecto en especial, y no hace nada sobre los jugadores.\n" +

                "\n -Marche Preso: Envia a los jugadores a la COMISARIA y los encierra.\n" +

                "\n -Suerte y Destino: Estas casillas obligan a un jugador a tomar una carta del mazo que indiquen y " +
                "\n  realizar lo que la carta diga.\n" +

                "\n -Premio e Impuesto: Estas casillas afectan directamente a la economia del jugador, sumando o restando" +
                "\n  dinero segun lo que se indique en la casilla.\n"
        );

        consola.MostrarInformacion("Compra, venta, mejora e hipoteca:");
        System.out.println("Los jugadores a lo largo de la partida iran teniendo la posibilidad de interactuar con sus propiedades" +
                "\ncon la finalidad de sacarles provecho y generar ingresos para ganar, estas opciones son:\n" +

                "\n -Compra: Le permite al jugador adueñarse de una propiedad para agregarla a su lista a cambio de su dinero" +
                "\n  y empezar a cobrar alquiler al resto de jugadores que caigan.\n" +

                "\n -Venta: Permite despojarse de una propiedad, ya sea vendiendosela al banco u a otro jugador por un" +
                "\n  monto negociable (El otro jugador puede aceptar o rechazar la oferta). Tambien hay que tener en cuenta" +
                "\n  que no se permite la venta de propiedades que posean mejoras, si se las quiere vender, se tendran que" +
                "\n  vender todas las mejoras de dicha propiedad y aquellas que dependen de la posesion de esta.\n" +

                "\n -Mejora: Las escrituras que posea el jugador se pueden aumentar de \"NIVEL\" a cambio de dinero " +
                "\n  una vez que este posea todas las escrituras de una misma provincia, el nivel varia de 0 a 5 y determina" +
                "\n  el coste de alquiler (A mayor nivel mayor alquiler). Cabe destacar que el nivel de una escritura no" +
                "\n  puede ser mayor en 1 al del resto de escrituras de la misma provincia.\n" +

                "\n -Hipoteca: Los jugadores pueden hipotecar sus propiedades con el banco, esto les otorga una suma de" +
                "\n  dinero si se encuentran en una necesidad financiera. Aquellas propiedades hipotecadas seguiran" +
                "\n  perteneciendo a su dueño, pero no se podra interactuar con ellas ni cobrar el alquiler. Para recuperar " +
                "\n  la propiedad se debera pagar la hipoteca el monto que se indique. (Al igual que al vender, no se pueden" +
                "\n  hipotecar propiedades con mejoras).\n"
        );
    }


    //-------------------------CONSULTAS POR CONSOLA-------------------------//
    //Envia un mensaje ingresado por parametro a la consola bajo el nombre de JUEGO y espera una respuesta por teclado para devolver
    public static String ConsultarCadena(String mensaje){
        Scanner input = new Scanner(System.in);

        System.out.print(Color.VERDE + "\nJUEGO: " + Color.BLANCO + mensaje + "\nRespuesta: ");
        String respuesta = input.nextLine(); //Se que esto es redundante y podria ir directo en el return... Igual me gusta asi

        return respuesta;
    }


    //Consulta por consola la entrada de un numero y valida que se encuentre en un rango minimo y maximo
    public static int ConsultarNumero(String mensaje, int min, int max){
        Scanner input = new Scanner(System.in);

        System.out.print(Color.VERDE + "\nJUEGO: " + Color.BLANCO + mensaje + "\nRespuesta: ");
        int respuesta = ValidarEntrada(input.nextLine(), min, max); //Se que esto es redundante y podria ir directo en el return... Igual me gusta asi (again)

        return respuesta;
    }


    //Muestra un menu en azul con distintas opciones y valida el ingreso por consola
    public static int ConsultarMenu(String consulta, String[] opciones) {
        Scanner input = new Scanner(System.in);
        String entrada;

        System.out.println("\n" + Color.AZUL + consulta + Color.BLANCO);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println((i+1) + ". " + opciones[i]);
        }
        System.out.print("\nRespuesta: ");
        entrada = input.nextLine();

        return ValidarEntrada(entrada, 1, opciones.length);
    }


    //-------------------------EXTRAS-------------------------//
    //Agrega un delay ingresado por parametros en milisegundos
    public static void AgregarRetraso(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (Exception e) {}
    }


    //-------------------------AUXILIARES-------------------------//
    //Valida el ingreso por consola dentro de un rango "min" a "max"
    private static int ValidarEntrada(String entrada, int min, int max) {
        Scanner input = new Scanner(System.in);
        int respuesta;

        //Intenta convertir la entrada a un int, si es posible valida que este dentro del rango solicitado
        try{
            respuesta = Integer.parseInt(entrada);

            //Valida que el numero este dentro del rango aceptado o envia un mensaje de error y pide ingresar un valor nuevamente
            if (respuesta < min || respuesta > max) {
                MostrarError("Ingrese un numero del " + min + " al " + max + ": ");
                respuesta = ValidarEntrada(input.nextLine(), min, max);
            }
        }

        //Si la entrada no es un numero informa del error y solicita ingresarla nuevamente
        catch (Exception e){
            MostrarError("La respuesta \"" + entrada + "\" no es aceptable. Ingrese un numero del " + min + " al " + max + ": ");
            respuesta = ValidarEntrada(input.nextLine(), min, max);
        }

        //Si la entrada es correcta la devuelve como un numero entero
        return respuesta;
    }


    //Devuelve un String centrado en un espacio de
    private static String CentrarTexto(String texto, int tamaño){
        StringBuilder resultado = new StringBuilder();

        int espaciosIzquierda = (tamaño - texto.length()) / 2;
        int espaciosDerecha = tamaño - texto.length() - espaciosIzquierda;

        for (int i = 0; i < espaciosIzquierda; i++) {
            resultado.append(" ");
        }

        resultado.append(texto);

        for (int i = 0; i < espaciosDerecha; i++) {
            resultado.append(" ");
        }

        return resultado.toString();
    }

}
