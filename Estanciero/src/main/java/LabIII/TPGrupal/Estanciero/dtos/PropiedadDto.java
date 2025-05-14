package LabIII.TPGrupal.Estanciero.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PropiedadDto {
    private int precio;
    private int[] alquileres;
    private int valorHipoteca;
    //Unicamente si viene de una partida guardada
    private boolean hipotecado;
    private JugadorDto dueño;
}
