package LabIII.TPGrupal.Estanciero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		consola.MostrarMensaje("Bienvenido al estanciero");

		while (true){
			//Creacion de la instancia del juego
			Estanciero estanciero = new Estanciero(Tablero.getPartida());

			//Creacion de la partida
			estanciero.CrearTablero();

			boolean guardar = estanciero.getPartida().IniciarPartida();

			if (guardar) {
				estanciero.GuardarPartida();
			}
			else {
				estanciero.getPartida().TerminarPartida();
			}

		}
	}

}
