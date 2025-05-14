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
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Getter
@Setter
public class Estanciero {
    //Atributos
    private Tablero partida;


    //Constructor
    public Estanciero(Tablero partida) {
        this.partida = partida;
    }

    public Estanciero() {
        this.partida = Tablero.getPartida();
    }

    //Métodos
    //Consulta si se desea empezar una nueva partida o cargar una de base de datos
    public void CrearTablero(){

        while (true){
            int opcion = consola.ConsultarMenu("¿Desea iniciar una nueva partida o cargar una existente?", new String[] {"Nueva Partida", "Cargar Partida", "Informacion del juego", "Salir"});
            if(opcion == 1){
                NuevaPartida();
                return;
            }

            else if(opcion == 2){
                CargarPartida();
                return;
            }

            else if(opcion == 3){
                consola.MostrarReglas();
            }

            else{
                consola.MostrarAviso("Cerrando el juego...");
                consola.MostrarMensaje("Autores: Grupo 4 (Aguilas Doradas)\n\n" + Color.AMARILLO +
                        "                                    /T /I                          \n" +
                        "                                   / |/ | .-~/                     \n" +
                        "                               T\\ Y  I  |/  /  _                  \n" +
                        "              /T               | \\I  |  I  Y.-~/                  \n" +
                        "             I l   /I       T\\ |  |  l  |  T  /                   \n" +
                        "          T\\ |  \\ Y l  /T   | \\I  l   \\ `  l Y                 \n" +
                        "      __  | \\l   \\l  \\I l __l  l   \\   `  _. |                 \n" +
                        "      \\ ~-l  `\\   `\\  \\  \\\\ ~\\  \\   `. .-~   |             \n" +
                        "       \\   ~-. \"-.  `  \\  ^._ ^. \"-.  /  \\   |                \n" +
                        "     .--~-._  ~-  `  _  ~-_.-\"-.\" ._ /._ .\" ./                  \n" +
                        "      >--.  ~-.   ._  ~>-\"    \"\\\\   7   7   ]                  \n" +
                        "     ^.___~\"--._    ~-{  .-~ .  `\\ Y . /    |                    \n" +
                        "      <__ ~\"-.  ~       /_/   \\   \\I  Y   : |                   \n" +
                        "        ^-.__           ~(_/   \\   >._:   | l______               \n" +
                        "            ^--.,___.-~\"  /_/   !  `-.~\"--l_ /     ~\"-.         \n" +
                        "                   (_/ .  ~(   /'     \"~\"--,Y   -=b-. _)         \n" +
                        "                    (_/ .  \\  :           / l      c\"~o \\       \n" +
                        "                     \\ /    `.    .     .^   \\_.-~\"~--.  )      \n" +
                        "                      (_/ .   `  /     /       !       )/          \n" +
                        "                       / / _.   '.   .':      /        '           \n" +
                        "                       ~(_/ .   /    _  `  .-<_                    \n" +
                        "                         /_/ . ' .-~\" `.  / \\  \\          ,z=.  \n" +
                        "                         ~( /   '  :   | K   \"-.~-.______//       \n" +
                        "                           \"-,.    l   I/ \\_    __{--->._(==.    \n" +
                        "                            //(     \\  <    ~\"~\"     //         \n" +
                        "                           /' /\\     \\  \\     ,v=.  ((          \n" +
                        "                         .^. / /\\     \"  }__ //===-  `           \n" +
                        "                        / / ' '  \"-.,__ {---(==-                  \n" +
                        "                      .^ '       :  T  ~\"   ll                    \n" +
                        "                     / .  .  . : | :!        \\\\                  \n" +
                        "                    (_/  /   | | j-\"          ~^                  \n" +
                        "                      ~-<_(_.^-~\"                                 \n" + Color.BLANCO +
                        "\nIntegrantes:\n" +
                        "- 113857 De Maussion, Gabriel\n" +
                        "- 113973 Luparia Mothe, Agustin\n" +
                        "- 113895 Ortiz, Pablo\n" +
                        "- 114320 Ferreyra, Fernando Gabriel\n" +
                        "- 113174 Gregorutti, Stefano\n" +
                        "- 114179 Castro, Leticia\n" +
                        "- 114143 Gobetto, Nadia");
                System.exit(0);
            }
        }
    }


    //
    public void NuevaPartida(){
        partida = Tablero.getPartida();
        RestTemplate peticion = new RestTemplate();

        //Creacion de las casillas
        consola.MostrarAviso("Buscando casillas...\n");

        List<Casilla> casillas = Arrays.asList(Objects.requireNonNull(peticion.getForObject("http://localhost:8080/Estanciero/Casilla/GetAll", Casilla[].class)));
        partida.setCasillas(casillas);

        consola.MostrarAviso("Casillas creadas");

        //Creacion de las cartas
        consola.MostrarAviso("Buscando cartas...");

        List<Carta> cartas = Arrays.asList(Objects.requireNonNull(peticion.getForObject("http://localhost:8080/Estanciero/Carta/GetAll", Carta[].class)));
        partida.getMazoSuerte().setCartas(new ArrayList<>(cartas.subList(0, 16)));
        partida.getMazoDestino().setCartas(new ArrayList<>(cartas.subList(16, 32)));
        partida.getMazoSuerte().MezclarMazo();
        partida.getMazoDestino().MezclarMazo();

        consola.MostrarAviso("Cartas creadas");

        //Consulta como se desea que finalice el juego
        int opcion = consola.ConsultarMenu("¿Como desea que finalice el juego?", new String[] {"Al llegar a una cantidad de dinero", "Cuando no queden mas jugadores"});

        //Si se desea que se termine con "cantidadGanadora", se la almacena
        if (opcion == 1){
            int cantidad = consola.ConsultarNumero("Ingrese la cantidad de dinero con la que quiere que finalice el juego", 40000, 1000000);

            partida.setCantidadGanadora(cantidad);
        }

        //Agrega a los jugadores al llamar al metodo para consultar la dificultad
        partida.setJugadores(SeleccionarDificultad());
    }


    //
    public void CargarPartida(){
        //ToDo
        String[] demo = {"Partida de Gabriel - $54000 (DIFICIL)", "Partida de Usuario X - $654321 (FACIL)", "Partida de Usuario Y - $1000 (NORMAL)", "Cancelar"}; //Fixme: Esto se consulta de la base de datos en realidad

        List<String> partidas = Arrays.asList(demo);


        int opcion = consola.ConsultarMenu("¿Que partida desea cargar?", partidas.toArray(new String[0]));

        //Fixme: Uso una nueva partida de todas formas (Solo para testeo de funcionamiento)
        System.out.println("Debug: Se cargara una nueva partida de todas formas (base de datos no implementada)");
        NuevaPartida();
    }


    //
    public void GuardarPartida(){
        consola.MostrarAviso("Guardando partida...");
        //Todo

    }


    //Consulta por consola el nivel de dificultad y carga en la partida los jugadores virtuales en base a la respuesta
    public List<Jugador> SeleccionarDificultad(){
        List<Jugador> jugadores = new ArrayList<>();
        String[] lista = new String[0];
        List<Color> colores = Color.Listar();
        List<Forma> formas = Forma.Listar();

        //Consulta la dificultad en la que se quiere jugar una nueva partida
        int opcion = consola.ConsultarMenu("Seleccione la dificultad:", new String[] {"Facil", "Normal", "Dificil"});

        if(opcion == 1){
            lista = new String[] {"Equilibrado", "Conservador"};
        }

        else if(opcion == 2){
            lista = new String[] {"Agresivo", "Equilibrado", "Conservador"};
        }

        else if (opcion == 3){
            lista = new String[] {"Agresivo", "Equilibrado", "Equilibrado", "Conservador"};
        }

        consola.MostrarAviso("Agregando jugadores...");

        //Agrega a todos los jugadores a un mapa
        jugadores.add(AgregarJugador("Usuario"));
        for (int i = 0; i < lista.length; i++){
            jugadores.add(AgregarJugador(lista[i]));
        }

        //Creo un peon para cada jugador
        consola.MostrarAviso("Creando peones para cada jugador...");
        for (Jugador j : jugadores){
            int color = new Random().nextInt(colores.size());
            int forma = new Random().nextInt(Forma.values().length);

            if (j instanceof JugadorNormal){
                color = consola.ConsultarMenu("¿Que color desea tener para su peon?", Color.MostrarOpciones(colores)) - 1;
                forma = consola.ConsultarMenu("¿Que forma desea tener para su peon?", Forma.MostrarOpciones(formas)) - 1;
            }
            j.setPeon(new Peon(colores.get(color), formas.get(forma)));
            colores.remove(color);
        }

        return OrdenarJugadores(jugadores);
    }


    //Crea y devuelve un jugador en base al tipo de perfil que se le diga por parametro, en caso de un jugador virtual, le asigna un nombre aleatorio
    public Jugador AgregarJugador(String tipo){
        RestTemplate peticion = new RestTemplate();
//        FixMe: Eliminar estas listas de provincias, vienen desde DB
//        List<String> provinciaAgresivo = Arrays.asList("TUCUMAN", "CORDOBA", "BUENOS AIRES");
//        List<String> provinciaEquilibrado = Arrays.asList("MENDOZA", "SANTA FE", "TUCUMAN");
//        List<String> provinciaConservador = Arrays.asList("FORMOSA", "RIO NEGRO", "SALTA");

        List<String> provinciasPrioritarias = peticion.getForObject("http://localhost:8080/Estanciero/Jugador/GetProvinciasP/" + tipo, List.class);

        String[] nombres = new String[] {"Juan", "Luis", "Carlos", "Maria", "Matias", "Javier", "Laura", "Ana", "Gabriel", "Camila"};
        String[] apellidos = new String[] {"Garcia", "Martinez", "Rodriguez", "Fernandez", "Gomez", "Perez", "Sanchez", "Torres", "Ramirez", "Lopez"};
        Jugador nuevoJugador;

        switch(tipo){
            case "Conservador" -> {
                nuevoJugador = new JugadorConservador(
                        nombres[new Random().nextInt(10)] + " " + apellidos[new Random().nextInt(10)] + "(" + tipo + ")",
                        35000,
                        0,
                        Estado.JUGANDO,
                        0,
                        new ArrayList<>(),
                        new Peon(),
                        provinciasPrioritarias
                );
            }

            case "Equilibrado" -> {
                nuevoJugador = new JugadorEquilibrado(
                        nombres[new Random().nextInt(10)] + " " + apellidos[new Random().nextInt(10)] + "(" + tipo + ")",
                        35000,
                        0,
                        Estado.JUGANDO,
                        0,
                        new ArrayList<>(),
                        new Peon(),
                        provinciasPrioritarias
                );
            }

            case "Agresivo" -> {
                nuevoJugador = new JugadorAgresivo(
                        nombres[new Random().nextInt(10)] + " " + apellidos[new Random().nextInt(10)] + "(" + tipo + ")",
                        35000,
                        0,
                        Estado.JUGANDO,
                        0,
                        new ArrayList<>(),
                        new Peon(),
                        provinciasPrioritarias
                );
            }

            default -> {
                nuevoJugador = new JugadorNormal();
                String nombre = consola.ConsultarCadena("Ingrese su nombre de Usuario");
                nuevoJugador.setNombre(nombre);
            }
        }

        return nuevoJugador;
    }


    //Realiza una tirada de dados y ordena a los jugadores por el numero de la tirada de mayor a menor
    public List<Jugador> OrdenarJugadores(List<Jugador> jugadores){

        consola.MostrarAviso("Ordenando jugadores...");

        List<Jugador> resultado= new ArrayList<>();
        List<Integer> tiradas=new ArrayList<>();

        //Realiza las tiradas por cada jugador
        for (int i = 0; i < jugadores.size(); i++){
            int tirada = partida.getDado1().TirarDado() + partida.getDado2().TirarDado();
            tiradas.add(tirada);
            consola.MostrarMensaje("El jugador " + jugadores.get(i).getNombre() + " saco: " + tirada);
        }

        while (!jugadores.isEmpty()){
            int mayor = 0;
            for(int j = 0; j < jugadores.size(); j++){
                if (tiradas.get(j) > tiradas.get(mayor) ){
                    mayor = j;
                }
            }

            resultado.add(jugadores.remove(mayor));
            tiradas.remove(mayor);
        }

        //Muestra el orden de los jugadores
        consola.MostrarAviso("\nOrden de turnos:");
        for(int i = 0; i < resultado.size(); i++){
            System.out.println((i+1) + ". " + resultado.get(i).getNombre());
        }

        return resultado;
    }


    //FixMe: Este metodo se usa para probar el funcionamiento del programa sin tener conectada la base de datos, se eliminara mas adelante
    private void SimularCarga(){
//
//        //Casillas ordenadas
//        partida.getCasillas().add(new Tributario(0, "SALIDA", "AL PASAR POR AQUI COBRE DEL BANCO 5000", 5000));
//        partida.getCasillas().add(new Escritura(1, "FORMOSA ZONA SUR", 1000, new int[] {40, 200, 600, 1700, 3000, 4750}, 500, false, null, "FORMOSA", "SUR", 0, 1000));
//        partida.getCasillas().add(new Escritura(2, "FORMOSA ZONA CENTRO", 1000, new int[] {40, 200, 600, 1700, 3000, 4750}, 500, false, null, "FORMOSA", "CENTRO", 0, 1000));
//        partida.getCasillas().add(new Escritura(3, "FORMOSA ZONA NORTE", 1200, new int[] {80, 400, 800, 3400, 6000, 9500}, 600, false, null, "FORMOSA", "NORTE", 0, 1000));
//        partida.getCasillas().add(new Tributario(4, "IMPUESTO A LOS REDITOS", "IMPUESTO A LOS REDITOS. PAGUE 5000", -5000));
//        partida.getCasillas().add(new Escritura(5, "RIO NEGRO ZONA SUR", 2000, new int[] {110, 570, 1700, 5150, 7600, 9500}, 1000, false, null, "RIO NEGRO", "SUR", 0, 1000));
//        partida.getCasillas().add(new Escritura(6, "RIO NEGRO ZONA NORTE", 2200, new int[] {150, 750, 2000, 5700, 8500, 11500}, 1100, false, null, "RIO NEGRO", "NORTE", 0, 1000));
//        partida.getCasillas().add(new Tributario(7, "PREMIO GANADERO", "PREMIO GANADERO. COBRE 2500", 2500));
//        partida.getCasillas().add(new Compañia(8, "COMPAÑIA PETROLERA", 3800, new int[] {100, 200, 300, 0, 0, 0}, 1900, false, null));
//        partida.getCasillas().add(new Escritura(9, "SALTA ZONA SUR", 2600, new int[] {200, 1000, 2800, 8500, 12000, 14200}, 1300, false, null, "SALTA", "SUR", 0, 1500));
//        partida.getCasillas().add(new SuerteDestino(10, "DESTINO", "Levante una carta del mazo de destino"));
//        partida.getCasillas().add(new Escritura(11, "SALTA ZONA CENTRO", 2600, new int[] {200, 1000, 2800, 8500, 12000, 14200}, 1300, false, null, "SALTA", "CENTRO", 0, 1500));
//        partida.getCasillas().add(new Ferrocarril(12, "FERROCARRIL GENERAL BELGRANO", 3600, new int[] {100, 200, 300, 400, 0, 0}, 1900, false, null));
//        partida.getCasillas().add(new Escritura(13, "SALTA ZONA NORTE", 3000, new int[] {230, 1150, 3400, 9500, 13000, 17000}, 1500, false, null, "SALTA", "NORTE", 0, 1500));
//        partida.getCasillas().add(new Comisaria(14, "COMISARIA", "Usted esta de visita", new HashMap<>()));
//        partida.getCasillas().add(new SuerteDestino(15, "SUERTE", "Levante una carta del mazo de suerte"));
//        partida.getCasillas().add(new Compañia(16, "BODEGA", 3800, new int[] {100, 200, 300, 0, 0, 0}, 1900, false, null));
//        partida.getCasillas().add(new Escritura(17, "MENDOZA ZONA SUR", 3400, new int[] {250, 1350, 3800, 10500, 14200, 18000}, 1700, false, null, "MENDOZA", "SUR", 0, 2000));
//        partida.getCasillas().add(new Ferrocarril(18, "FERROCARRIL GENERAL SAN MARTIN", 3600, new int[] {100, 200, 300, 400, 0, 0}, 1800, false, null));
//        partida.getCasillas().add(new Escritura(19, "MENDOZA ZONA CENTRO", 3400, new int[] {250, 1350, 3800, 10500, 14200, 18000}, 1700, false, null, "MENDOZA", "CENTRO", 0, 2000));
//        partida.getCasillas().add(new Escritura(20, "MENDOZA ZONA NORTE", 3800, new int[] {300, 1500, 4200, 11500, 15000, 19000}, 1900, false, null, "MENDOZA", "NORTE", 0, 2000));
//        partida.getCasillas().add(new Descanso(21, "DESCANSO", "Puede elegir permanecer en la casilla", new HashMap<>()));
//        partida.getCasillas().add(new Ferrocarril(22, "FERROCARRIL GENERAL B. MITRE", 3600, new int[] {100, 200, 300, 400, 0, 0}, 1800, false, null));
//        partida.getCasillas().add(new Escritura(23, "SANTA FE ZONA SUR", 4200, new int[] {350, 1700, 4750, 13000, 16000, 20000}, 2100, false, null, "SANTA FE", "SUR", 0, 2500));
//        partida.getCasillas().add(new Escritura(24, "SANTA FE ZONA CENTRO", 4200, new int[] {350, 1700, 4750, 13000, 16000, 20000}, 2100, false, null, "SANTA FE", "CENTRO", 0, 2500));
//        partida.getCasillas().add(new SuerteDestino(25, "DESTINO", "Levante una carta del mazo de destino"));
//        partida.getCasillas().add(new Escritura(26, "SANTA FE ZONA NORTE", 4600, new int[] {400, 2000, 5750, 14000, 17000, 21000}, 2300, false, null, "SANTA FE", "NORTE", 0, 2500));
//        partida.getCasillas().add(new Ferrocarril(27, "FERROCARRIL GENERAL URQUIZA", 3600, new int[] {100, 200, 300, 400, 0, 0}, 1700, false, null));
//        partida.getCasillas().add(new EstacionamientoLibre(28, "Estacionamiento libre", "Esta casilla no hace nada en especial"));
//        partida.getCasillas().add(new Escritura(29, "TUCUMAN ZONA SUR", 5000, new int[] {400, 2200, 6000, 15000, 18000, 21000}, 2500, false, null, "TUCUMAN", "SUR", 0, 3000));
//        partida.getCasillas().add(new Escritura(30, "TUCUMAN ZONA NORTE", 5400, new int[] {450, 2400, 6800, 16000, 19500, 23000}, 2700, false, null, "TUCUMAN", "NORTE", 0, 3000));
//        partida.getCasillas().add(new Compañia(31, "INGENIO", 3800, new int[] {100, 200, 300, 0, 0, 0}, 1900, false, null));
//        partida.getCasillas().add(new Escritura(32, "CORDOBA ZONA SUR", 6000, new int[] {500, 2500, 6500, 17000, 21000, 24000}, 3000, false, null, "CORDOBA", "SUR", 0, 3000));
//        partida.getCasillas().add(new Escritura(33, "CORDOBA ZONA CENTRO", 6000, new int[] {450, 2400, 6800, 16000, 19500, 23000}, 3000, false, null, "CORDOBA", "CENTRO", 0, 3000));
//        partida.getCasillas().add(new Escritura(34, "CORDOBA ZONA NORTE", 6400, new int[] {550, 2850, 8500, 19000, 23000, 27000}, 3200, false, null, "CORDOBA", "NORTE", 0, 3000));
//        partida.getCasillas().add(new MarchePreso(35, "Marche preso", "Marche preso directamente"));
//        partida.getCasillas().add(new SuerteDestino(36, "SUERTE", "Levante una carta del mazo de suerte"));
//        partida.getCasillas().add(new Escritura(37, "BUENOS AIRES ZONA SUR", 7000, new int[] {650, 3300, 9500, 22000, 25000, 30000}, 3500, false, null, "BUENOS AIRES", "SUR", 0, 4000));
//        partida.getCasillas().add(new SuerteDestino(38, "DESTINO", "Levante una carta del mazo de destino"));
//        partida.getCasillas().add(new Escritura(39, "BUENOS AIRES ZONA CENTRO", 7000, new int[] {650, 3300, 9500, 22000, 25000, 30000}, 3500, false, null, "BUENOS AIRES", "CENTRO", 0, 4000));
//        partida.getCasillas().add(new Escritura(40, "BUENOS AIRES ZONA NORTE", 7400, new int[] {1000, 4000, 12000, 26000, 31000, 36000}, 3700, false, null, "BUENOS AIRES", "NORTE", 0, 4000));
//        partida.getCasillas().add(new Tributario(41, "IMPUESTO A LAS VENTAS", "IMPUESTO A LAS VENTAS. PAGUE 2000", -2000));


        //Cartas
//        partida.getMazoSuerte().GuardarCarta(new CartaAvanzarHasta(1, "Siga hasta la tercer casilla", 3));
//        partida.getMazoSuerte().GuardarCarta(new CartaCobrarJugadores(2, "Es su cumpleaños. Cobre 200 de cada uno de sus jugadores", 200));
//        partida.getMazoSuerte().GuardarCarta(new CartaHabeasCorpus(3, "Habeas Corpus, puede usar esta carta para salir de comisaria o conservarla"));
//        partida.getMazoSuerte().GuardarCarta(new CartaMarchePreso(4, "Marche preso directamente"));
//        partida.getMazoSuerte().GuardarCarta(new CartaMoverCantidad(5, "Vuelva 3 pasos atrás", -3));
//        partida.getMazoSuerte().GuardarCarta(new CartaMultaSuerte(6, "Pague 200 de multa o levante una tarjeta de suerte", -200));
//        partida.getMazoSuerte().GuardarCarta(new CartaRetrocederHasta(7, "Vuelve atras hasta la casilla 0", 0));
//        partida.getMazoSuerte().GuardarCarta(new CartaTransaccion(8, "Ha ganado un concurso agricola. Cobre 2000", 2000));
//
//        partida.getMazoDestino().GuardarCarta(new CartaAvanzarHasta(17, "Siga hasta la tercer casilla", 3));
//        partida.getMazoDestino().GuardarCarta(new CartaCobrarJugadores(18, "Es su cumpleaños. Cobre 200 de cada uno de sus jugadores", 200));
//        partida.getMazoDestino().GuardarCarta(new CartaHabeasCorpus(19, "Habeas Corpus, puede usar esta carta para salir de comisaria o conservarla"));
//        partida.getMazoDestino().GuardarCarta(new CartaMarchePreso(20, "Marche preso directamente"));
//        partida.getMazoDestino().GuardarCarta(new CartaMoverCantidad(21, "Vuelva 3 pasos atrás", -3));
//        partida.getMazoDestino().GuardarCarta(new CartaMultaSuerte(22, "Pague 200 de multa o levante una tarjeta de suerte", -200));
//        partida.getMazoDestino().GuardarCarta(new CartaRetrocederHasta(23, "Vuelve atras hasta la casilla 0", 0));
//        partida.getMazoDestino().GuardarCarta(new CartaTransaccion(24, "Ha ganado un concurso agricola. Cobre 2000", 2000));

        partida.getMazoSuerte().MezclarMazo();
        partida.getMazoDestino().MezclarMazo();
    }

}
