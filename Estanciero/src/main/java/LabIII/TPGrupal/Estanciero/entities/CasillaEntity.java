package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Casilla")
@Data
public class CasillaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //cambiar preguntarle al profe
    @Column(name = "id_casilla")
    private Integer idCasilla;

    @Column(name="nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name="id_tipo_casilla",nullable=false)
    private TipoCasillaEntity tipoCasilla;


}
