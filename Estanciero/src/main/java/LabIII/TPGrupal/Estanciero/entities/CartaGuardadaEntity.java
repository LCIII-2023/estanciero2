package LabIII.TPGrupal.Estanciero.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Carta_Guardada")
@Data

public class CartaGuardadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_carta_guardada")
    private Integer idCartaGuardada;


    @ManyToOne
    @JoinColumn(name="id_carta",nullable=false)
    private CartaEntity carta;

    @ManyToOne
    @JoinColumn(name="id_judador_guardado",nullable=false)
    private JugadorGuardadoEntity jugadorGuardado;
}
