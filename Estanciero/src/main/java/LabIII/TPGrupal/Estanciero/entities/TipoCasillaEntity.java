package LabIII.TPGrupal.Estanciero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Tipo_Casilla")
@Data
public class TipoCasillaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tipo_casilla")
    private Integer idTipoCasilla;

    @Column(name="tipo_casilla")
    private String tipoCasilla;

}
