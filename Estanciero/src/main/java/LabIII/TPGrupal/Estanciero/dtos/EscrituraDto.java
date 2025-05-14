package LabIII.TPGrupal.Estanciero.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EscrituraDto {
    private String provincia;
    private String zona;
    private int nivel;
    private int costeMejora;
}
