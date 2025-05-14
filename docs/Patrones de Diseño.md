## Patrones de Diseño

### **Introducción**
Los patrones de diseño son soluciones generales reutilizables para problemas comunes en el diseño de software. Estos patrones son una forma de documentar las mejores prácticas y ayudar a los diseñadores y desarrolladores a crear sistemas más flexibles, reutilizables y mantenibles. Se clasifican principalmente en tres categorías: Patrones Creacionales, Patrones Estructurales y Patrones de Comportamiento.

### Patrones de Diseños que vamos a utilizar:

### 1. **Strategy**
- El patrón Strategy define una familia de algoritmos, encapsula cada uno de ellos y los hace intercambiables. Permite que el algoritmo varíe independientemente de los clientes que lo usan.

- __Aplicación__:
     El patrón Strategy en la clase `Jugador` y sus subclases `JugadorNormal` y `JugadorVirtual` que pueden tener diferentes comportamientos para las acciones `Comprar()`, `Vender()` y `Mejorar()`, dependiendo de la estrategia que se les asigne. 


### 2. **Template Method**
- El patrón Template Method define el esqueleto de un algoritmo en una operación, diferiendo algunos pasos a las subclases. Permite que las subclases redefinan ciertos pasos de un algoritmo sin cambiar la estructura del mismo.

- __Aplicación__:
     El método `Acción()` en la clase `Casilla` puede ser implementado como un Template Method. Las subclases `Tributario`, `Descanso`, `Comisaría` y `Propiedad` redefinirán este método para realizar acciones específicas.


### 3. **Factory**
- Define una interfaz para crear un objeto, pero permite que las subclases alteren el tipo de objetos que se crearán. Es un patrón de diseño creacional que usa métodos para manejar la creación de objetos.

- __Aplicación__:
    El patrón Factory puede ser usado para la creación de diferentes tipos de `Casilla`y `Carta`. Esto encapsula la lógica de instanciación y facilita la expansión futura.   


### 4. **Singleton**
- Asegura que una clase tenga solo una instancia y proporciona un punto de acceso global a esa instancia. Es un patrón de diseño creacional que se usa cuando necesitas exactamente una instancia de una clase para controlar el acceso a un recurso compartido.

- __Aplicación__:
     El patrón Singleton se puede usar para la clase `Tablero`, asegurando que solo haya una instancia del tablero en todo el juego.
