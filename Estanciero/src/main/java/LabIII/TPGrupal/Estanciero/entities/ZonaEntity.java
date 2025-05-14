package LabIII.TPGrupal.Estanciero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Zona")
@Data
public class ZonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_zona")
    private Integer idZona;

    @Column(name="zona")
    private String zona;


}
