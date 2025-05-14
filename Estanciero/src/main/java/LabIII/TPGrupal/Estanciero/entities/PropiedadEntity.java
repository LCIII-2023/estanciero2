package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Propiedad")
@Data
public class PropiedadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_propiedad")
    private Integer idPropiedad;


    @ManyToOne
    @JoinColumn(name="id_casilla",nullable=false)
    private CasillaEntity casilla;

    @ManyToOne
    @JoinColumn(name="id_alquiler",nullable=false)
    private AlquilerEntity alquiler;

    @Column(name="precio")
    private Integer precio;

    @Column(name="valor_hipoteca")
    private Integer valorHipoteca;

}
