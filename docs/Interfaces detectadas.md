# TP Grupal: El Estanciero

## Introduccion

  - Las interfaces son una abstracción que permiten definir un contrato sobre el que se puede estar seguro de que, las clases que las implementen, lo van a cumplir.
  En POO se dice que las interfaces son funcionalidades (o comportamientos) y las clases representen implementaciones.
  Las interfaces son un concepto más teórico que real. No se pueden crear instancias de interfaces.
  Es decir las interfaces son similares a las clases abstractas pero a diferencia de éstas solo pueden contener definiciones de métodos sin implementación.
  Por último si dos clases implementan la misma interface, ambas clases son intercambiables.


## Interfaces detectadas 

  - En el Software que estamos desarrollando, la emulacion de "El estanciero" contiene varias clases que se rigen por un comportamiento similar, dicha cualidad nos permite implementar interfaces para proporcionar una estructura modular y adaptable que facilita la extensión y mantenimiento del código


  ### Interfaz Jugador 

  - Esta interfaz `iJugador` es una representacion de las personas o jugadores reales que participen en el juego de mesa y pretende definir las acciones que seran posibles para todos los jugadores sean usuarios o jugadores virtuales dentro del programa, la Interfaz es implementada por una clase abstracta (`Jugador`) que sera la base para `JugadorNormal` y `JugadorVirtual`.

    - `Comprar()`: Permite al `Jugador` adquirir una `Propiedad` bajo coste de su propio `dinero`.

    - `Vender()`: Habilita la eliminacion de una `Propiedad` que el jugador posea por una suma de `dinero`.

    - `Mejorar()`: El `Jugador` podra "construir" (subir de `nivel`) aquellas `Escrituras` que formen parte de sus `propiedades` a coste de `dinero`.

 
  ### Interfaz Casilla

  - La interfaz `iCasilla` refiere a las distintas casillas posibles en las que un jugador se puede posar sobre el tablero y establece un solo metodo obligatorio para las clases que la implementen directamente o por herencia a travez de la clase abstracta `Casilla` que a su vez define atributos comunes entre las clases que la extiendan (hereden).

    - `Accion()`: Realiza distintos comportamientos cuando los `Jugadores` se posen sobre dicha `Casilla`, variando al tratarse de casillas de tipo: `Tributario`, `Descanso`, `Propiedad`, etc. Definiendo la dinamica principal del juego.


  ### Interfaz Carta

  - `iCarta` pretende representar las cartas y las acciones que se ven obligador a realizar los `Jugadores` en el juego, la interfaz es implementada por la clase abstracta `Carta` de la que a su vez derivan varias clases hijas que heredan el metodo de esta interfaz.

    - `UsarCarta()`: Permite realizar distintas acciones sobre los `Jugadores` que levanten la `Carta` dependiendo el origen y objetivo de esta misma, pudiendo ser mover al jugador, premiar o cobrar impuestos, salir de comisaria, etc.