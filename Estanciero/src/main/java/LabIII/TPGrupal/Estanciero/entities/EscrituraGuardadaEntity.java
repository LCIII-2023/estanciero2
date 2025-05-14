package LabIII.TPGrupal.Estanciero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Escritura_Guardada")
@Data

public class EscrituraGuardadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_escritura_guardada")
    private Integer idEscrituraGuardada;


    @ManyToOne
    @JoinColumn(name="id_escritura",nullable=false)
    private EscrituraEntity escritura;

    @Column(name="nivel")
    private Integer nivel;


}
