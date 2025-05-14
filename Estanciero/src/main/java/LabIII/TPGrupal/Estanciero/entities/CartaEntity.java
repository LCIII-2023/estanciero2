package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Carta")
@Data
public class CartaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_carta")
    private Integer idCarta;


    @ManyToOne
    @JoinColumn(name="id_tipo_carta",nullable=false)
    private TipoCartaEntity tipoCarta;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="valor")
    private Integer valor;


}
