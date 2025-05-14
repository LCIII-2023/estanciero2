package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Tributario")
@Data
public class TributarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tributario")
    private Integer idTributario;


    @ManyToOne
    @JoinColumn(name="id_casilla",nullable=false)
    private CasillaEntity casilla;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="monto")
    private Integer monto;

}
