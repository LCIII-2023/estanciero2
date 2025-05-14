package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Jugador_Guardado")
@Data
public class JugadorGuardadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_jugador_guardado")
    private Integer idJugadorGuardado;


    @ManyToOne
    @JoinColumn(name="id_tipo_jugador",nullable=false)
    private TipoJugadorEntity tipoJugador;

    @Column(name="nombre")
    private String nombre;

    @Column(name="dinero")
    private Integer dinero;

    @Column(name="posicion")
    private String posicion;

    @Column(name="estado")
    private String estado;

    @Column(name="turno_estado")
    private Integer turnoEstado;

    @Column(name="color")
    private String color;

    @Column(name="forma")
    private String forma;
}
