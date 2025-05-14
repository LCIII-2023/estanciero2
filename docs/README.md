# TP Grupal: El Estanciero

## Integrantes: 
- 113857 De Maussion, Gabriel
- 113973 Luparia Mothe, Agustin
- 113895 Ortiz, Pablo
- 114320 Ferreyra, Fernando Gabriel
- 113174 Gregorutti, Stefano
- 114179 Castro, Leticia
- 114143 Gobetto, Nadia


## Clases

### 1. **Estanciero**
- Funciona como clase principal, se encarga consultar al usuario si desea cargar una partida guardada o iniciar una nueva, permite seleccionar la dificultad agregando jugadores virtuales e inicia el tablero.
- __Atributos__:
     - `partida` : Un objeto de clase `Tablero` que contiene la logica de juego que se lleva a cabo dentro de la partida. 
- __Métodos__:
     - `CrearTablero()` : Consulta si se desea iniciar una nueva partida, en cuyo caso se cargara un tablero en blanco, se agregan jugadores "virtuales" en base a la dificultad y se ordenan los jugadores, si no, se puede cargar una partida ya empezada desde la base de datos e iniciarla.
     - `NuevaPartida()` : Consulta la dificultad y crea un tablero cargando los datos de todas sus clases con una partida de 0.
     - `CargarPartida()` : Crea un tablero y rellena los atributos de las clases con los datos traidos de una partida iniciada de la base de datos.
     - `GuardarPartida()` : Guarda los datos de una partida actual en la base de datos.
     - `SeleccionarDificultad()` : Permite establecer el nivel de dificultad del juego, agregando jugadores virtuales con distintos perfiles en la partida.
     - `AgregarJugador()` : Agrega al jugador e instancia los distintos perfiles de jugadores virtuales para sumarlos a la lista de jugadores.
     - `OrdenarJugadores()` : Ordena la lista de jugadores en el Tablero en base a una tirada de dados inicial de mayor a menor y muestra dicho orden por consola.


### 2. **Tablero**
- Una clase que implementa el patron singleton y controla la mayor parte de la logica del juego, siendo responsable de manejar el dinero, pagos, movimiento, tirada de dados y finalizar la partida o hacer uso de las clases especificas que se encargan de estas tareas.
- __Atributos__:
     - `partida` : Un objeto `Tablero` que hace uso del patron singleton para referenciar a una unica instancia.
     - `jugadorActual` : Hace referencia al jugador de la lista `jugadores` al que le toca el turno.
     - `jugadores` : Una lista de objeto `Jugador` ordenada en base a los turnos.
     - `casillas` : Una lista de las 42 objetos `Casilla` en las que los jugadores pueden caer en el tablero.
     - `dado1` : Una instancia de la clase, para llamar a sus metodos y recuperar el valor del primer dado.
     - `dado2` : Una instancia de la clase, para llamar a sus metodos y recuperar el valor del segundo dado.
     - `mazoSuerte` : Una instancia de la clase `Mazo`, que contiene la lista de cartas de tipo suerte usables para los jugadores.
     - `mazoDestino` : Una instancia de la clase `Mazo`, que contiene la lista de cartas de tipo destino usables para los jugadores.
     - `cantidadGanadora` : Un valor entero que al alcanzar un jugador indicara el fin de la partida.
     - `resultado` : Una lista de objetos `Jugador` que estan fuera del juego para posteriormente mostrar el orden de puntuacion.
- __Métodos__: 
     - `GetPartida()` : Metodo que hace uso del singleton para que otras clases puedan acceder a la unica instancia de `tablero`.
     - `MostrarEstado()` : Muestra el estado actual del juego.
     - `Turno()` : Gestiona el turno de cada jugador, decidiendo si puede comprar, mejorar o levantar cartas entre otras.
     - `MoverJugador()` : Mueve el jugador por el tablero según el resultado del dado o cartas.
     - `PagarAlquiler()` : Gestiona el pago de alquileres cuando un jugador cae en una propiedad ajena.
     - `CambiarBalanceJugador()` : Verifica y actualiza el balance de los jugadores, sumando o restando montos.
     - `EliminarJugador()` : Remueve un jugador de la lista en caso de que este en bancarrota y lo agrega a la lista `resultado`.  
     - `TerminarPartida()` : Finaliza el juego y determina jugador ganador.   


### 3. **Mazo**
- Una clase dedicada a almacenar una lista de cartas y permitirle a la clase `Tablero` realizar acciones entre los objetos `Jugador` y `Carta` ademas de barajar la lista `cartas` al inicio de partida.
- __Atributos__:
     - `cartas` : Una lista de objetos de la clase `Carta`.
- __Métodos__:
     - `LevantarCartas()` : Devuelve una `Carta` ubicada al final del mazo, se realiza la accion que esta deba y se envia la carta al final del mazo.
     - `GuardarCarta()` : Agrega una `Carta` al inicio de la lista `cartas`.
     - `MezclarMazo()` : Mezcla los objetos tipo `Carta` de la lista "`cartas`".


### 4. **Carta**
- Clase abstracta encargada de almacenar los datos de una carta y mostrar o indicar las acciones que se deben realizar al levantarse.
- __Atributos__:
     - `id` : Un numero entero utilizado para identificar la carta al traerla y al guardarla en la base de datos.
     - `descripcion` : Cadena de caracteres a modo de mensaje con el efecto de la carta. 
- __Métodos__:
     - `UsarCarta()` : Un metodo abstracto encargado de realizar lo que sus clases hijas redefinan.


### 5. **Jugador**
- Clase para el usuario, los atributos ayudan a llevar registro de su progreso y los metodos le habilitan a realizar distintas acciones durante su turno.
- __Atributos__:
     - `nombre` : Identificacion del jugador/jugador virtual dentro del tablero.
     - `dinero` : Un valor entero que representa el capital del que dispone el jugador.
     - `posicion` : Un numero entero que indica la casilla sobre la que el jugador se encuentra parado en el tablero.
     - `estado` : Describe en que situacion se encuentra cada `Jugador`.    
     - `propiedades` : Una lista de enteros que hace referencia a las propiedades que el jugador posee de la lista de casillas (se almacena el indice de la casilla en la lista).
     - `cartas` : Son las cartas que puede guardar el `Jugador`.
- __Métodos__:
     - `Comprar()`: Permite al jugador comprar la propiedad de la casilla sobre la que se encuentre y agregarla a su lista de `propiedades`.
     - `Vender()`: Permite al jugador vender alguna de las propiedades que posea en su lista de `propiedades` cuando sea su turno.
     - `Mejorar()` : Permite la mejora de las escrituras que posea el jugador agregando chacras o cambiandolas por una estancia.
     - `validarMejora()` : Comprueba si el `Jugador` puede realizar las mejoras sobre una de sus `propiedades`.


### 6. **JugadorNormal** (hereda de **Jugador**)
- Es una clase que identifica al usuario. La mayoria de sus metodos requieren de la interaccion por consola para determinar sus resultados.
- __Métodos sobreescritos__
     - `Comprar()` : Compra propiedades en base a las preferencias del perfil.
     - `Vender()`: Vende propiedades si llegase a ser necesario.
     - `Mejorar()` : Permite la mejora de las escrituras que posea el jugador agregando chacras o cambiandolas por una estancia basandose en las preferencias del perfil.
     - `Hipotecar()` : Un metodo que permite darle la opcion al usuario de hipotecar si quisera las propiedades que posee.


### 7. **JugadorVirtual** (hereda de **Jugador**)
- Es una clase abstracta de la que derivan diferentes estrategias de juego (`PerfilAgresivo`, `PerfilConservador`, `PerfilEquilibrado`), dichas estrategias seran clases hijas que utilicen y redefinan los metodos en base al comportamiento que sea necesario.
- __Atributos__:
     - `provinciasPrioritarias` : Una lista de `String`s con los nombres de las provincias que el jugador virtual prioriza al comprar.
- __Métodos sobreescritos__
     - `Comprar()` : Compra propiedades en base a las preferencias del perfil.
     - `Vender()`: Vende propiedades si llegase a ser necesario.
     - `Mejorar()` : Permite la mejora de las escrituras que posea el jugador agregando chacras o cambiandolas por una estancia basandose en las preferencias del perfil.


### 8. **Casilla**
- Es una clase padre con atributos comunes entre los distintos tipos de casillas posibles en los que los jugadores pueden caer, el metodo `Accion()` sera redefinido para que se ejecute de maneras distintas dependiendo el tipo de casilla que sea y lo que esta deba hacer.
- __Atributos__:
     - `id` : Un numero entero (int) como identificador de la casilla.
     - `nombre` : Una cadena de caracteres para representar el nombre de la casilla.
- __Métodos__:
     - `Accion()` : Un metodo abstracto para permitir que las clases que lo utilicen sobreescriban su funcionamiento para las acciones que necesiten.
     

### 9. **Tributario** (hereda de **Casilla**)
- Clase que hereda de `Casilla` atributos y metodos para realizar una sustraccion o adicion de dinero de los jugadores que caigan en ella.
- __Atributos__:
     - `descripcion` : Una cadena de caracteres que describe si se debe cobrar o pagar al jugador segun la accion de la casilla.
     - `monto` : Un valor entero que almacena el monto que se le cobrara al jugador si cae en esa casilla.
- __Métodos__:
     - `Accion()` : Un metodo sobreescrito de `Casilla` encargado de sumar o restar dinero del jugador posicionado sobre la casilla.


### 10. **Descanso** (hereda de **Casilla**)
- Clase orientada a permitirle al jugador "negar" su movimiento por el tablero si este desea no moverse de la casilla.
- __Atributos__:
     - `descripcion` : Una cadena de caracteres que describe lo que realiza esta casilla, en este caso si el jugador desea quedarse por un turno en la misma. 
     - `jugadores` : Un mapa de objetos `Jugador` que tengan permitido permanecer en la casilla con el valor de turnos posibles en el que puedan quedarse en la misma.
- __Métodos__:
     - `Accion()` : Metodo sobreescrito de `Casilla` encargado de realizar el calculo de mantener o soltar a los jugadores.
     - `AgregarJugador()` : Agrega un `Jugador` al mapa de `jugadores`.
     - `SoltarJugador()` : Elimina a un `Jugador` del mapa de `jugadores`.
     - `DescontarTurno()` : Resta 1 al valor del mapa con la clave de `Jugador` pasado como parametro.
     - `Consultar()` : Le pregunta al `Jugador` si desea permanecer o salir de la casilla.


### 11. **Comisaría** (hereda de **Casilla**)
- Clase orientada a encerrar a los jugadores hasta que cumplan ciertos requisitos para poder ser liberados.
- __Atributos__:
     - `descripcion` : Una cadena de caracteres que describe el funcionamiento de la misma.
     - `jugadores` : Un mapa de objetos `Jugador` que se encuentran en la carcel.
- __Métodos__:
     - `Accion()` : Metodo sobreescrito de `Casilla` encargado de realizar los calculos y la permanencia de los juagdores dentro del estado de encarcelado.
     - `Encerrar()` : Agrega un `Jugador` al mapa `jugadores`.
     - `Liberar()` : Elimina al `Jugador` del mapa `jugadores`.
     - `DescontarTurno()` : Descuenta 1 del valor con la clave `Jugador` que se pasa por parametro.


### 12. **Propiedad** (hereda de **Casilla**)
- Clase padre para todas aquellos tipos de casillas que sean comprables e imponen un alquiler sobre los jugadores ajenos que caigan en ellas.
- __Atributos__:
     - `tipo` : Es un Enum de propiedades para acelerar la busqueda y filtro entre las variantes de propiedades (Escritura, Ferrocarril y Compañia).
     - `precio` : El importe que se debe abonar para que un jugador adquiera la propiedad de la casilla.
     - `alquileres` : Una lista de valores enteros que varia segun el nivel de la escritura o cantidad de propiedades iguales que posea el dueño de la casilla.
     - `valorHipoteca` : El monto que se le otorgara al jugador si desea hipotecar la propiedad.
     - `hipotecado` : Un booleano para comprobar el estado de la propiedad.
     - `dueño` : Referencia al `Jugador` al que pertenece la `Propiedad`.
- __Métodos__:
     - `Accion()` : Metodo abstracto redefinido por sus clases hijas que se encargue de ofrecer la posibilidad de `Comprar()` si esta disponible o de `CobrarAlquiler()` si ya tiene `dueño`.
     - `CalcularAlquiler()` : En base a los valores de alquiler se encarga de devolver un entero con el monto que se le debe cobrar al jugador que caiga en la casilla.
     - `CambiarHipoteca()` : Intercambia entre true y false el atributo `hipotecado` de la propiedad, sumando y restando el valor de la hipoteca.     


### 13. **Escritura** (Hereda de **Propiedad**)
- Especifica detalles de quellas propiedades "escrituras" comprables (22 en total) que pueden ser mejoradas agregándoles Chacras o Estancias.
- __Atributos__:
     - `provincia`: String con la ubicación de la propiedad.
     - `zona`: String con la zona específica dentro de la provincia.
     - `nivel`: valor entero que indica el nivel de desarrollo de la propiedad (número de Chacras/Estancias).
     - `costeMejora` : Un valor entero que describe el dinero que se le cobrara al `Jugador` si desea mejorar su `Escritura`.
- __Métodos sobreescritos__:
     - `CalcularAlquiler()` : Devuelve un numero entero con el monto que se deberia cobrar al jugador que caiga en la casilla teniendo en cuenta el nivel (cantidad de chacras/estancias) que esten en la casilla.


### 14. **Compañia** (Hereda de **Propiedad**)
- Sobreescritura para las 3 casillas que utilizan atributos y metodos de la clase padre y sobreescribe aquellos que sean necesarios para el calculo del alquiler.
- __Métodos sobreescritos__:
     - `CalcularAlquiler()` : Devuelve un numero entero con el monto que se deberia cobrar al jugador que caiga en la casilla teniendo en cuenta la cantidad de compañias que posea el dueño de la casilla haciendo uso  del atributo de `Alquileres` y una tirada de dados.


### 15. **Ferrocarril** (Hereda de **Propiedad**)
- Detalles para las 4 casillas que utilizan atributos y metodos de la clase padre y sobreescribe aquellos que sean necesarios para el calculo del alquiler.
- __Métodos sobreescritos__:
     - `CalcularAlquiler()` : Devuelve un numero entero con el monto que se deberia cobrar al jugador que caiga en la casilla teniendo en cuenta la cantidad de ferrocarriles que posea el dueño de la casilla haciendo uso  del atributo de `Alquileres`.


### 16. **Dado**
- Clase encargada de almacenar las tiradas del 1 al 6 para permitir el movimiento de los jugadores entre otras cosas.
- __Atributos__:
     - `valor` : Numero entero del 1 al 6 para el dado.
- __Métodos__:
     - `TirarDado()` : Devuelve un numero entero que varia del 1 al 6 y lo asigna al atributo `valor`.