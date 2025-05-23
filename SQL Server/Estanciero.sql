CREATE DATABASE [Estanciero]
USE [Estanciero]


--Controles comunes
DROP TABLE ESCRITURA_GUARDADA
-----------------------------------
DROP TABLE CARTA_GUARDADA
DROP TABLE PROPIEDAD_GUARDADA
DROP TABLE ALQUILER
DROP TABLE ESCRITURA
-----------------------------------
DROP TABLE JUGADOR_GUARDADO
DROP TABLE CARTA
DROP TABLE PROVINCIA_PRIORITARIA
DROP TABLE TRIBUTARIO
DROP TABLE PROPIEDAD
-----------------------------------
DROP TABLE TIPO_JUGADOR
DROP TABLE TIPO_CARTA
DROP TABLE TIPO_PROPIEDAD
DROP TABLE PROVINCIA
DROP TABLE ZONA
DROP TABLE CASILLA
-----------------------------------
set dateformat dmy




--=================================Tablas=================================--
-- N#1
CREATE TABLE CASILLA
(id_casilla int,
nombre varchar(100)
CONSTRAINT pk_id_casilla PRIMARY KEY (id_casilla))

CREATE TABLE ZONA
(id_zona int,
zona varchar(50)
CONSTRAINT pk_id_zona PRIMARY KEY (id_zona))

CREATE TABLE PROVINCIA
(id_provincia int,
provincia varchar(30),
coste_mejora int
CONSTRAINT pk_id_provincia PRIMARY KEY (id_provincia))

CREATE TABLE TIPO_PROPIEDAD
(id_tipo_propiedad int,
tipo_propiedad varchar(50),	--(Escritura, Ferrocarril, Compañia)
CONSTRAINT pk_id_tipo_propiedad PRIMARY KEY (id_tipo_propiedad))

CREATE TABLE TIPO_CARTA
(id_tipo_carta int,
tipo_carta varchar(30)	--(Transaccion, MoverCantidad, AvanzarHasta, RetrocederHasta, CobrarJugadores, etc)
CONSTRAINT pk_id_tipo_carta PRIMARY KEY (id_tipo_carta))

CREATE TABLE TIPO_JUGADOR
(id_tipo_jugador int,
tipo_jugador varchar(30),	--(Usuario, Conservador, Equilibrado, Agresivo)
CONSTRAINT pk_id_tipo_jugador PRIMARY KEY (id_tipo_jugador))


-- N#2
CREATE TABLE PROPIEDAD
(id_propiedad int,
id_casilla int,
id_tipo_propiedad int,
precio int,
valor_hipoteca int,
CONSTRAINT pk_id_propiedad PRIMARY KEY (id_propiedad),
CONSTRAINT fk_propiedad_x_casilla FOREIGN KEY (id_casilla)
REFERENCES CASILLA (id_casilla),
CONSTRAINT fk_propiedad_x_tipo_propiedad FOREIGN KEY (id_tipo_propiedad)
REFERENCES TIPO_PROPIEDAD (id_tipo_propiedad))

CREATE TABLE TRIBUTARIO
(id_tributario int,
id_casilla int,
descripcion varchar(100),
monto int
CONSTRAINT pk_id_tributario PRIMARY KEY (id_tributario),
CONSTRAINT fk_tributario_x_casilla FOREIGN KEY (id_casilla)
REFERENCES CASILLA (id_casilla))

CREATE TABLE PROVINCIA_PRIORITARIA
(id_provincia_prioritaria int,
id_provincia int,
id_tipo_jugador int
CONSTRAINT pk_id_provincia_prioritaria PRIMARY KEY (id_provincia_prioritaria),
CONSTRAINT fk_provincia_prioritaria_x_provincia FOREIGN KEY (id_provincia)
REFERENCES PROVINCIA (id_provincia),
CONSTRAINT fk_provincia_prioritaria_x_tipo_jugador FOREIGN KEY (id_tipo_jugador)
REFERENCES TIPO_JUGADOR (id_tipo_jugador))

CREATE TABLE CARTA
(id_carta int,
id_tipo_carta int,
descripcion varchar(150),
valor int
CONSTRAINT pk_id_carta PRIMARY KEY (id_carta),
CONSTRAINT fk_carta_x_tipo_carta FOREIGN KEY (id_tipo_carta)
REFERENCES TIPO_CARTA (id_tipo_carta))

CREATE TABLE JUGADOR_GUARDADO	--Tabla para partida empezada
(id_jugador_guardado int,
id_tipo_jugador int,
nombre varchar(50),
dinero int,
posicion int,
estado varchar(50)	--(JUGANDO, BANCARROTA, COMISARIA, DESCANSO)
CONSTRAINT pk_id_jugador_guardado PRIMARY KEY (id_jugador_guardado),
CONSTRAINT fk_jugador_guardado_x_tipo_jugador FOREIGN KEY (id_tipo_jugador)
REFERENCES TIPO_JUGADOR (id_tipo_jugador))


-- N#3
CREATE TABLE ESCRITURA
(id_escritura int,
id_propiedad int,
id_provincia int,
id_zona int
CONSTRAINT pk_id_escritura PRIMARY KEY (id_escritura),
CONSTRAINT fk_escritura_x_propiedad FOREIGN KEY (id_propiedad)
REFERENCES PROPIEDAD (id_propiedad),
CONSTRAINT fk_escritura_x_provincia FOREIGN KEY (id_provincia)
REFERENCES PROVINCIA (id_provincia),
CONSTRAINT fk_escritura_x_zona FOREIGN KEY (id_zona)
REFERENCES ZONA (id_zona))

CREATE TABLE ALQUILER
(id_alquiler int,
id_propiedad int,
alquiler_1 int,
alquiler_2 int,
alquiler_3 int,
alquiler_4 int,
alquiler_5 int,
alquiler_6 int
CONSTRAINT pk_id_alquiler PRIMARY KEY (id_alquiler),
CONSTRAINT fk_alquiler_x_propiedad FOREIGN KEY (id_propiedad)
REFERENCES PROPIEDAD (id_propiedad))

CREATE TABLE PROPIEDAD_GUARDADA	--Tabla para partida empezada
(id_propiedad_guardada int,
id_propiedad int,
id_jugador_guardado int,
hipotecada bit,		--(0 : False), (1 : True) 
CONSTRAINT pk_id_propiedad_guardada PRIMARY KEY (id_propiedad_guardada),
CONSTRAINT fk_propiedad_guardada_x_propiedad FOREIGN KEY (id_propiedad)
REFERENCES PROPIEDAD (id_propiedad),
CONSTRAINT fk_propiedad_guardada_x_jugador_guardado FOREIGN KEY (id_jugador_guardado)
REFERENCES JUGADOR_GUARDADO (id_jugador_guardado))

CREATE TABLE CARTA_GUARDADA	--Tabla para partida empezada
(id_carta_guardada int,
id_carta int,
id_jugador_guardado int
CONSTRAINT pk_id_carta_guardada PRIMARY KEY (id_carta),
CONSTRAINT fk_carta_guardada_x_carta FOREIGN KEY (id_carta)
REFERENCES CARTA (id_carta),
CONSTRAINT fk_carta_guardada_x_jugador_guardado FOREIGN KEY (id_jugador_guardado)
REFERENCES JUGADOR_GUARDADO (id_jugador_guardado))


--N#4
CREATE TABLE ESCRITURA_GUARDADA	--Tabla para partida empezada
(id_escritura_guardada int,
id_escritura int,
nivel int
CONSTRAINT pk_id_escritura_guardada PRIMARY KEY (id_escritura_guardada),
CONSTRAINT fk_escritura_guardada_x_escritura FOREIGN KEY (id_escritura)
REFERENCES ESCRITURA (id_escritura))
--=================================Tablas=================================--





--=================================Inserts=================================--
--CASILLA (id_casilla, nombre)
INSERT INTO CASILLA (id_casilla, nombre) VALUES
(0, 'SALIDA'),
(1, 'FORMOSA ZONA SUR'),
(2, 'FORMOSA ZONA CENTRO'),
(3, 'FORMOSA ZONA NORTE'),
(4, 'IMPUESTO A LOS REDITOS'),
(5, 'RIO NEGRO ZONA SUR'),
(6, 'RIO NEGRO ZONA NORTE'),
(7, 'PREMIO GANADERO'),
(8, 'COMPAÑIA PETROLERA'),
(9, 'SALTA ZONA SUR'),
(10, 'DESTINO'),
(11, 'SALTA ZONA CENTRO'),
(12, 'FERROCARRIL GENERAL BELGRANO'),
(13, 'SALTA ZONA NORTE'),
(14, 'COMISARIA'),
(15, 'SUERTE'),
(16, 'BODEGA'),
(17, 'MENDOZA ZONA SUR'),
(18, 'FERROCARRIL GENERAL S. MARTIN'),
(19, 'MENDOZA ZONA CENTRO'),
(20, 'MENDOZA ZONA NORTE'),
(21, 'DESCANSO'),
(22, 'FERROCARRIL GENERAL B. MITRE'),
(23, 'SANTA FE ZONA SUR'),
(24, 'SANTA FE ZONA CENTRO'),
(25, 'DESTINO'),
(26, 'SANTA FE ZONA NORTE'),
(27, 'FERROCARRIL GENERAL URQUIZA'),
(28, 'FREE PARKING'),
(29, 'TUCUMAN ZONA SUR'),
(30, 'TUCUMAN ZONA NORTE'),
(31, 'INGENIO'),
(32, 'CORDOBA ZONA SUR'),
(33, 'CORDOBA ZONA CENTRO'),
(34, 'CORDOBA ZONA NORTE'),
(35, 'MARCHE PRESO'),
(36, 'SUERTE'),
(37, 'BS.AS ZONA SUR'),
(38, 'DESTINO'),
(39, 'BS.AS ZONA CENTRO'),
(40, 'BS.AS ZONA NORTE'),
(41, 'IMPUESTO A LAS VENTAS');


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


--TIPO_PROPIEDAD (id_tipo_propiedad, tipo_propiedad)
INSERT INTO TIPO_PROPIEDAD (id_tipo_propiedad, tipo_propiedad) VALUES
(1, 'ESCRITURA'),
(2, 'FERROCARRIL'),
(3, 'COMPAÑIA');


--TIPO_CARTA (id_tipo_carta, tipo_carta)
INSERT INTO TIPO_CARTA (id_tipo_carta, tipo_carta) VALUES
(1, 'TRANSACCION'),
(2, 'MOVER CANTIDAD'),
(3, 'AVANZAR HASTA'),
(4, 'RETROCEDER HASTA'),
(5, 'COBRAR JUGADORES'),
(6, 'MULTA SUERTE'),
(7, 'MARCHE PRESO'),
(8, 'HABEAS CORPUS');


--TIPO_JUGADOR (id_tipo_jugador, tipo_jugador)
INSERT INTO TIPO_JUGADOR (id_tipo_jugador, tipo_jugador) VALUES
(1, 'USUARIO'),
(2, 'CONSERVADOR'),
(3, 'EQUILIBRADO'),
(4, 'AGRESIVO');


--PROPIEDAD (id_propiedad, id_casilla, id_tipo_propiedad, precio, valor_hipoteca)
INSERT INTO PROPIEDAD (id_propiedad, id_casilla, id_tipo_propiedad, precio, valor_hipoteca) VALUES
(1, 1, 1, 1000, 500),
(2, 2, 1, 1000, 500),
(3, 3, 1, 1200, 600),
(4, 5, 1, 2000, 1000),
(5, 6, 1, 2200, 1100),
(6, 8, 3, 3800, 1900),
(7, 9, 1, 2600, 1300),
(8, 11, 1, 2600, 1300),
(9, 12, 2, 3600, 1900),
(10, 13, 1, 3000, 1500),
(11, 16, 3, 3600, 1900),
(12, 17, 1, 3400, 1700),
(13, 18, 2, 3600, 1800),
(14, 19, 1, 3400, 1700),
(15, 20, 1, 3800, 1900),
(16, 22, 2, 3600, 1800),
(17, 23, 1, 4200, 2100),
(18, 24, 1, 4200, 2100),
(19, 26, 1, 4600, 2300),
(20, 27, 2, 3600, 1700),
(21, 29, 1, 5000, 2500),
(22, 30, 1, 5400, 2700),
(23, 31, 3, 3800, 1900),
(24, 32, 1, 6000, 3000),
(25, 33, 1, 6000, 3000),
(26, 34, 1, 6400, 3200),
(27, 37, 1, 7000, 3500),
(28, 39, 1, 7000, 3500),
(29, 40, 1, 7400, 3700);


--TRIBUTARIO (id_tributario, id_casilla, descripcion, monto)
INSERT INTO TRIBUTARIO (id_tributario, id_casilla, descripcion, monto) VALUES
(1, 0, 'AL PASAR POR AQUI COBRE DEL BANCO 5000', 5000),
(2, 4, 'IMPUESTO A LOS REDITOS. PAGUE 5000', 5000),
(3, 7, 'PREMIO GANADERO. COBRE 2500', 2500),
(4, 41, 'IMPUESTO A LAS VENTAS. PAGUE 2000', 2000);


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
(0, 3, 'Marche preso directamente', 35),
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
(12, 1, 'Sus propiedades tienen que ser reparadas. Pague al banco 500 por cada chacra y 2500 por cada estancia', -500),
(13, 1, 'Por compra de semilla pague al banco 800 por cada chacra. 4000 por cada estancia', -800),
(14, 3, 'Siga hasta Santa Fe, Zona Norte. Si pasa por la salida cobre 5000', 26),
(15, 8, 'Habeas corpus concedido. Con esta tarjeta sale usted gratuitamente de la comisaria. Conservela o vendala', null),
--CARTAS DESTINO
(16, 1, '5% de interes sobre cedulas hipotecarias. Cobre 500', 500),
(17, 8, 'Con esta tarjeta sale usted de la comisaria. Conservela hasta utilizarla o venderla', null),
(18, 7, 'Marche preso directamente', 35),
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
INSERT INTO ALQUILER (id_alquiler, id_propiedad, alquiler_1, alquiler_2, alquiler_3, alquiler_4, alquiler_5, alquiler_6) VALUES
--Escrituras
(1, 1, 40, 200, 600, 1700, 3000, 4750),
(2, 2, 40, 200, 600, 1700, 3000, 4750),
(3, 3, 80, 400, 800, 3400, 6000, 9500),
(4, 4, 110, 570, 1700, 5150, 7600, 9500),
(5, 5, 150, 750, 2000, 5700, 8500, 11500),
(6, 7, 200, 1000, 2800, 8500, 12000, 14200),
(7, 8, 200, 1000, 2800, 8500, 12000, 14200),
(8, 10, 230, 1150, 3400, 9500, 13000, 17000),
(9, 12, 250, 1350, 3800, 10500, 14200, 18000),
(10, 14, 250, 1350, 3800, 10500, 14200, 18000),
(11, 15, 300, 1500, 4200, 11500, 15000, 19000),
(12, 17, 350, 1700, 4750, 13000, 16000, 20000),
(13, 18, 350, 1700, 4750, 13000, 16000, 20000),
(14, 19, 400, 2000, 5750, 14000, 17000, 21000),
(15, 21, 400, 2200, 6000, 15000, 18000, 21000),
(16, 22, 450, 2400, 6800, 16000, 19500, 23000),
(17, 24, 500, 2500, 6500, 17000, 21000, 24000),
(18, 25, 450, 2400, 6800, 16000, 19500, 23000),
(19, 26, 550, 2850, 8500, 19000, 23000, 27000),
(20, 27, 650, 3300, 9500, 22000, 25000, 30000),
(21, 28, 650, 3300, 9500, 22000, 25000, 30000),
(22, 29, 1000, 4000, 12000, 26000, 31000, 36000),
--Ferrocarriles
(23, 9, 500, 1000, 2000, 4000, null, null),
(24, 13, 500, 1000, 2000, 4000, null, null),
(25, 16, 500, 1000, 2000, 4000, null, null),
(26, 20, 500, 1000, 2000, 4000, null, null),
--Compañias
(27, 6, 100, 200, 300, null, null, null),
(28, 11, 100, 200, 300, null, null, null),
(29, 23, 100, 200, 300, null, null, null);


--PROPIEDAD_GUARDADA (SOLO POR SP)
--INSERT INTO PROPIEDAD_GUARDADA (id_propiedad_guardada, id_propiedad, id_jugador_guardado, hipotecada) VALUES


--CARTA_GUARDADA (SOLO POR SP)
--INSERT INTO CARTA_GUARDADA (id_carta_guardada, id_carta, id_jugador_guardada) VALUES


--ESCRITURA_GUARDADA (SOLO POR SP)
--INSERT INTO ESCRITURA_GUARDADA (id_escritura_guardada, id_escritura, nivel) VALUES

--=================================Inserts=================================--





--===================================SPs===================================--













--===================================SPs===================================--