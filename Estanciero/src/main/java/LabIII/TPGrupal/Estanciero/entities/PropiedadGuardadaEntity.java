package LabIII.TPGrupal.Estanciero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Propiedad_Guardada")
@Data

public class PropiedadGuardadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_propiedad_guardada")
    private Integer idPropiedadGuardada;


    @ManyToOne
    @JoinColumn(name="id_propiedad",nullable=false)
    private PropiedadEntity propiedad;

    @ManyToOne
    @JoinColumn(name="id_judador_guardado",nullable=false)
    private JugadorGuardadoEntity jugadorGuardado;

    @Column(name="hipoteca")
    private boolean hipoteca; //en BD es tipo bit
}
