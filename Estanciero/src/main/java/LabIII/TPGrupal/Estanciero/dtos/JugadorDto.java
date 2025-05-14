package LabIII.TPGrupal.Estanciero.dtos;

import LabIII.TPGrupal.Estanciero.models.enums.Estado;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class JugadorDto {
    private int id;
    private String tipoJugador;
    private String nombre;
    private int dinero;
    private int posicion;
    private Estado estado;
}
