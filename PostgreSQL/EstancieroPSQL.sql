-- Database: Estanciero

-- DROP DATABASE IF EXISTS "Estanciero";

CREATE DATABASE "Estanciero"
    WITH
    OWNER = "Developer"
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Spain.1252'
    LC_CTYPE = 'Spanish_Spain.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

--Controles comunes
DROP TABLE ESCRITURA_GUARDADA;
-----------------------------------
DROP TABLE CARTA_GUARDADA;
DROP TABLE PROPIEDAD_GUARDADA;
DROP TABLE ESCRITURA;
-----------------------------------
DROP TABLE JUGADOR_GUARDADO;
DROP TABLE CARTA;
DROP TABLE PROVINCIA_PRIORITARIA;
DROP TABLE TRIBUTARIO;
DROP TABLE PROPIEDAD;
DROP TABLE CASILLA;
-----------------------------------
DROP TABLE ALQUILER;
DROP TABLE TIPO_JUGADOR;
DROP TABLE TIPO_CARTA;
DROP TABLE TIPO_CASILLA;
DROP TABLE PROVINCIA;
DROP TABLE ZONA;
-----------------------------------



--=================================Tablas=================================--
-- N#1
CREATE TABLE ZONA (
    id_zona INT PRIMARY KEY,
    zona VARCHAR(50)
);

CREATE TABLE PROVINCIA (
    id_provincia INT PRIMARY KEY,
    provincia VARCHAR(30),
    coste_mejora INT
);

CREATE TABLE TIPO_CASILLA (
    id_tipo_casilla INT PRIMARY KEY,
    tipo_casilla VARCHAR(50)
);

CREATE TABLE TIPO_CARTA (
    id_tipo_carta INT PRIMARY KEY,
    tipo_carta VARCHAR(30) --(Transaccion, MoverCantidad, AvanzarHasta, RetrocederHasta, CobrarJugadores, etc)
);

CREATE TABLE TIPO_JUGADOR (
    id_tipo_jugador INT PRIMARY KEY,
    tipo_jugador VARCHAR(30) --(Usuario, Conservador, Equilibrado, Agresivo)
);

CREATE TABLE ALQUILER (
    id_alquiler INT PRIMARY KEY,
    alquiler_1 INT,
    alquiler_2 INT,
    alquiler_3 INT,
    alquiler_4 INT,
    alquiler_5 INT,
    alquiler_6 INT,
);

-- N#2
CREATE TABLE CASILLA (
    id_casilla INT PRIMARY KEY,
    nombre VARCHAR(100),
	id_tipo_casilla INT,
	FOREIGN KEY (id_tipo_casilla) REFERENCES TIPO_CASILLA (id_tipo_casilla)
);

CREATE TABLE PROPIEDAD (
    id_propiedad INT PRIMARY KEY,
    id_casilla INT,
	id_alquiler INT,
    precio INT,
    valor_hipoteca INT,
    FOREIGN KEY (id_casilla) REFERENCES CASILLA (id_casilla),
	FOREIGN KEY (id_alquiler) REFERENCES ALQUILER (id_alquiler)
);

CREATE TABLE TRIBUTARIO (
    id_tributario INT PRIMARY KEY,
    id_casilla INT,
    descripcion VARCHAR(100),
    monto INT,
    FOREIGN KEY (id_casilla) REFERENCES CASILLA (id_casilla)
);

CREATE TABLE PROVINCIA_PRIORITARIA (
    id_provincia_prioritaria INT PRIMARY KEY,
    id_provincia INT,
    id_tipo_jugador INT,
    FOREIGN KEY (id_provincia) REFERENCES PROVINCIA (id_provincia),
    FOREIGN KEY (id_tipo_jugador) REFERENCES TIPO_JUGADOR (id_tipo_jugador)
);

CREATE TABLE CARTA (
    id_carta INT PRIMARY KEY,
    id_tipo_carta INT,
    descripcion VARCHAR(150),
    valor INT,
    FOREIGN KEY (id_tipo_carta) REFERENCES TIPO_CARTA (id_tipo_carta)
);

CREATE TABLE JUGADOR_GUARDADO (
    id_jugador_guardado INT PRIMARY KEY,
    id_tipo_jugador INT,
    nombre VARCHAR(50),
    dinero INT,
    posicion INT,
    estado VARCHAR(50), --(JUGANDO, BANCARROTA, COMISARIA, DESCANSO)
	turnoEstado INT,
	color VARCHAR(50),
	forma VARCHAR(50),
    FOREIGN KEY (id_tipo_jugador) REFERENCES TIPO_JUGADOR (id_tipo_jugador)
);

-- N#3
CREATE TABLE ESCRITURA (
    id_escritura INT PRIMARY KEY,
    id_propiedad INT,
    id_provincia INT,
    id_zona INT,
    FOREIGN KEY (id_propiedad) REFERENCES PROPIEDAD (id_propiedad),
    FOREIGN KEY (id_provincia) REFERENCES PROVINCIA (id_provincia),
    FOREIGN KEY (id_zona) REFERENCES ZONA (id_zona)
);


CREATE TABLE PROPIEDAD_GUARDADA (
    id_propiedad_guardada INT PRIMARY KEY,
    id_propiedad INT,
    id_jugador_guardado INT,
    hipotecada BOOLEAN,
    FOREIGN KEY (id_propiedad) REFERENCES PROPIEDAD (id_propiedad),
    FOREIGN KEY (id_jugador_guardado) REFERENCES JUGADOR_GUARDADO (id_jugador_guardado)
);

CREATE TABLE CARTA_GUARDADA (
    id_carta_guardada INT PRIMARY KEY,
    id_carta INT,
    id_jugador_guardado INT,
    FOREIGN KEY (id_carta) REFERENCES CARTA (id_carta),
    FOREIGN KEY (id_jugador_guardado) REFERENCES JUGADOR_GUARDADO (id_jugador_guardado)
);

--N#4
CREATE TABLE ESCRITURA_GUARDADA (
    id_escritura_guardada INT PRIMARY KEY,
    id_escritura INT,
    nivel INT,
    FOREIGN KEY (id_escritura) REFERENCES ESCRITURA (id_escritura)
);
--=================================Tablas=================================--



--=================================Inserts=================================--
--ZONA (id_zona, zona)
INSERT INTO ZONA (id_zona, zona) VALUES
(1, 'ZONA SUR'),
(2, 'ZONA CENTRO'),
(3, 'ZONA NORTE');


--PROVINCIA (id, provincia, coste_mejora)
INSERT INTO PROVINCIA (id_provincia, provincia, coste_mejora) VALUES
(1, 'FORMOSA', 1000),
(2, 'RIO NEGRO', 1000),
(3, 'SALTA', 1500),
(4, 'MENDOZA', 2000),
(5, 'SANTA FE', 2500),
(6, 'TUCUMAN', 3000),
(7, 'CORDOBA', 3000),
(8, 'BUENOS AIRES', 4000);


--TIPO_CASILLA (id_tipo_casilla, tipo_casilla)
INSERT INTO TIPO_CASILLA (id_tipo_casilla, tipo_casilla) VALUES
(1, 'Escritura'),
(2, 'Ferrocarril'),
(3, 'Compañia'),
(4, 'SuerteDestino'),
(5, 'Tributario'),
(6, 'Comisaria'),
(7, 'Descanso'),
(8, 'MarchePreso'),
(9, 'EstacionamientoLibre');


--TIPO_CARTA (id_tipo_carta, tipo_carta)
INSERT INTO TIPO_CARTA (id_tipo_carta, tipo_carta) VALUES
(1, 'Transaccion'),
(2, 'MoverCantidad'),
(3, 'AvanzarHasta'),
(4, 'RetrocederHasta'),
(5, 'CobrarJugadores'),
(6, 'MultaSuerte'),
(7, 'MarchePreso'),
(8, 'HabeasCorpus'),
(9, 'MultaMejora');


--TIPO_JUGADOR (id_tipo_jugador, tipo_jugador)
INSERT INTO TIPO_JUGADOR (id_tipo_jugador, tipo_jugador) VALUES
(1, 'USUARIO'),
(2, 'CONSERVADOR'),
(3, 'EQUILIBRADO'),
(4, 'AGRESIVO');


--CASILLA (id_casilla, nombre)
INSERT INTO CASILLA (id_casilla, nombre, id_tipo_casilla) VALUES
(0, 'SALIDA', 5),
(1, 'FORMOSA ZONA SUR', 1),
(2, 'FORMOSA ZONA CENTRO', 1),
(3, 'FORMOSA ZONA NORTE', 1),
(4, 'IMPUESTO A LOS REDITOS', 5),
(5, 'RIO NEGRO ZONA SUR', 1),
(6, 'RIO NEGRO ZONA NORTE', 1),
(7, 'PREMIO GANADERO', 5),
(8, 'COMPAÑIA PETROLERA', 3),
(9, 'SALTA ZONA SUR', 1),
(10, 'DESTINO', 4),
(11, 'SALTA ZONA CENTRO', 1),
(12, 'FERROCARRIL GENERAL BELGRANO', 2),
(13, 'SALTA ZONA NORTE', 1),
(14, 'COMISARIA', 6),
(15, 'SUERTE', 4),
(16, 'BODEGA', 3),
(17, 'MENDOZA ZONA SUR', 1),
(18, 'FERROCARRIL GENERAL S. MARTIN', 2),
(19, 'MENDOZA ZONA CENTRO', 1),
(20, 'MENDOZA ZONA NORTE', 1),
(21, 'DESCANSO', 7),
(22, 'FERROCARRIL GENERAL B. MITRE', 2),
(23, 'SANTA FE ZONA SUR', 1),
(24, 'SANTA FE ZONA CENTRO', 1),
(25, 'DESTINO', 4),
(26, 'SANTA FE ZONA NORTE', 1),
(27, 'FERROCARRIL GENERAL URQUIZA', 2),
(28, 'FREE PARKING', 9),
(29, 'TUCUMAN ZONA SUR', 1),
(30, 'TUCUMAN ZONA NORTE', 1),
(31, 'INGENIO', 3),
(32, 'CORDOBA ZONA SUR', 1),
(33, 'CORDOBA ZONA CENTRO', 1),
(34, 'CORDOBA ZONA NORTE', 1),
(35, 'MARCHE PRESO', 8),
(36, 'SUERTE', 4),
(37, 'BS.AS ZONA SUR', 1),
(38, 'DESTINO', 4),
(39, 'BS.AS ZONA CENTRO', 1),
(40, 'BS.AS ZONA NORTE', 1),
(41, 'IMPUESTO A LAS VENTAS', 5);


--PROPIEDAD (id_propiedad, id_casilla, id_tipo_propiedad, precio, valor_hipoteca)
INSERT INTO PROPIEDAD (id_propiedad, id_casilla, id_alquiler, precio, valor_hipoteca) VALUES
(1, 1, 1, 1000, 500),
(2, 2, 1, 1000, 500),
(3, 3, 2, 1200, 600),
(4, 5, 3, 2000, 1000),
(5, 6, 4, 2200, 1100),
(6, 8, 18, 3800, 1900),
(7, 9, 5, 2600, 1300),
(8, 11, 5, 2600, 1300),
(9, 12, 17, 3600, 1900),
(10, 13, 6, 3000, 1500),
(11, 16, 18, 3600, 1900),
(12, 17, 7, 3400, 1700),
(13, 18, 17, 3600, 1800),
(14, 19, 7, 3400, 1700),
(15, 20, 8, 3800, 1900),
(16, 22, 17, 3600, 1800),
(17, 23, 9, 4200, 2100),
(18, 24, 9, 4200, 2100),
(19, 26, 10, 4600, 2300),
(20, 27, 17, 3600, 1700),
(21, 29, 11, 5000, 2500),
(22, 30, 12, 5400, 2700),
(23, 31, 18, 3800, 1900),
(24, 32, 13, 6000, 3000),
(25, 33, 12, 6000, 3000),
(26, 34, 14, 6400, 3200),
(27, 37, 15, 7000, 3500),
(28, 39, 15, 7000, 3500),
(29, 40, 16, 7400, 3700);


--TRIBUTARIO (id_tributario, id_casilla, descripcion, monto)
INSERT INTO TRIBUTARIO (id_tributario, id_casilla, descripcion, monto) VALUES
(1, 0, 'AL PASAR POR AQUI COBRE DEL BANCO 5000', 5000),
(2, 4, 'IMPUESTO A LOS REDITOS. PAGUE 5000', -5000),
(3, 7, 'PREMIO GANADERO. COBRE 2500', 2500),
(4, 41, 'IMPUESTO A LAS VENTAS. PAGUE 2000', -2000);


--PROVINCIA_PRIORITARIA (id_provincia_prioritaria, id_provincia, id_tipo_jugador)
INSERT INTO PROVINCIA_PRIORITARIA (id_provincia_prioritaria, id_provincia, id_tipo_jugador) VALUES
(1, 1, 2),
(2, 2, 2),
(3, 3, 2),
(4, 4, 3),
(5, 5, 3),
(6, 6, 3),
(7, 6, 4),
(8, 7, 4),
(9, 8, 4);


--CARTA (id_carta, id_tipo_carta, descripcion, valor)
INSERT INTO CARTA (id_carta, id_tipo_carta, descripcion, valor) VALUES
--CARTAS SUERTE
(0, 7, 'Marche preso directamente', null),
(1, 1, 'Ha ganado la grande. Cobre 10000', 10000),
(2, 3, 'Haga un paseo hasta la bodega. Si pasa por la salida cobre 5000', 16),
(3, 3, 'Siga hasta Buenos Aires, Zona Norte', 40),
(4, 3, 'Siga hasta Salta, Zona norte. Si pasa por la salida Cobre 5000', 13),
(5, 1, 'Multa por exceso de velocidad. Pague 300', -300),
(6, 1, 'Gano en las carreras. Cobre 3000', 3000),
(7, 1, 'Cobre 1000 por intereses bancarios', 1000),
(8, 3, 'Siga hasta la salida', 0),
(9, 1, 'Pague 3000 por gastos colegiales', -3000),
(10, 2, 'Vuelva 3 pasos atrás', -3),
(11, 1, 'Multa caminera. Pague 400', -400),
(12, 9, 'Sus propiedades tienen que ser reparadas. Pague al banco 500 por cada chacra y 2500 por cada estancia', -500),
(13, 9, 'Por compra de semilla pague al banco 800 por cada chacra. 4000 por cada estancia', -800),
(14, 3, 'Siga hasta Santa Fe, Zona Norte. Si pasa por la salida cobre 5000', 26),
(15, 8, 'Habeas corpus concedido. Con esta tarjeta sale usted gratuitamente de la comisaria. Conservela o vendala', null),
--CARTAS DESTINO
(16, 1, '5% de interes sobre cedulas hipotecarias. Cobre 500', 500),
(17, 8, 'Con esta tarjeta sale usted de la comisaria. Conservela hasta utilizarla o venderla', null),
(18, 7, 'Marche preso directamente', null),
(19, 1, 'Devolución de impuestos. Cobre 400', 400),
(20, 1, 'Pague su poliza de seguro con 1000', -1000),
(21, 1, 'Ha ganado un concurso agricola. Cobre 2000', 2000),
(22, 1, 'Error en los calculos del banco. Cobre 4000', 4000),
(23, 1, 'Gastos de farmacia. Pague 1000', -1000),
(24, 1, 'Ha obtenido un segundo premio de belleza. Cobre 200', 200),
(25, 5, 'Es su cumpleaños. Cobre 200 de cada uno de sus jugadores', 200),
(26, 1, 'Ha ganado un concurso agricola. Cobre 2000', 2000),
(27, 1, 'Hereda 2000', 2000),
(28, 1, 'Por venta de acciones. Cobre 1000', 1000),
(29, 3, 'Siga hasta la salida', 0),
(30, 4, 'Vuelve atras hasta Formosa, zona sur', 1),
(31, 6, 'Pague 200 de multa o levante una tarjeta de suerte', -200);

--JUGADOR_GUARDADO (SOLO POR SP)
--INSERT INTO JUGADOR_GUARDADO (id_jugador_guardado, id_tipo_jugador, nombre, dinero, posicion, estado) VALUES


--ESCRITURA (id_escritura, id_propiedad, id_provincia, id_zona)
INSERT INTO ESCRITURA (id_escritura, id_propiedad, id_provincia, id_zona) VALUES
(1, 1, 1, 1),
(2, 2, 1, 2),
(3, 3, 1, 3),
(4, 4, 2, 1),
(5, 5, 2, 3),
(6, 7, 3, 1),
(7, 8, 3, 2),
(8, 10, 3, 3),
(9, 12, 4, 1),
(10, 14, 4, 2),
(11, 15, 4, 3),
(12, 17, 5, 1),
(13, 18, 5, 2),
(14, 19, 5, 3),
(15, 21, 6, 1),
(16, 22, 6, 3),
(17, 24, 7, 1),
(18, 25, 7, 2),
(19, 26, 7, 3),
(20, 27, 8, 1),
(21, 28, 8, 2),
(22, 29, 8, 3);


--ALQUILER (id_alquiler, id_propiedad, alquiler_1, alquiler_2, alquiler_3, alquiler_4, alquiler_5, alquiler_6)
INSERT INTO ALQUILER (id_alquiler, alquiler_1, alquiler_2, alquiler_3, alquiler_4, alquiler_5, alquiler_6) VALUES
--Escrituras
(1, 40, 200, 600, 1700, 3000, 4750),
(2, 80, 400, 800, 3400, 6000, 9500),
(3, 110, 570, 1700, 5150, 7600, 9500),
(4, 150, 750, 2000, 5700, 8500, 11500),
(5, 200, 1000, 2800, 8500, 12000, 14200),
(6, 230, 1150, 3400, 9500, 13000, 17000),
(7, 250, 1350, 3800, 10500, 14200, 18000),
(8, 300, 1500, 4200, 11500, 15000, 19000),
(9, 350, 1700, 4750, 13000, 16000, 20000),
(10, 400, 2000, 5750, 14000, 17000, 21000),
(11, 400, 2200, 6000, 15000, 18000, 21000),
(12, 450, 2400, 6800, 16000, 19500, 23000),
(13, 500, 2500, 6500, 17000, 21000, 24000),
(14, 550, 2850, 8500, 19000, 23000, 27000),
(15, 650, 3300, 9500, 22000, 25000, 30000),
(16, 1000, 4000, 12000, 26000, 31000, 36000),
--Ferrocarriles
(17, 9, 500, 1000, 2000, 4000, null, null),
--Compañias
(18, 6, 100, 200, 300, null, null, null),
--=================================Inserts=================================--

