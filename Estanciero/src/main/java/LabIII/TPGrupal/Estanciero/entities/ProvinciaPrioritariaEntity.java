package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Provincia_Prioritaria")
@Data
public class ProvinciaPrioritariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_provincia_prioritaria")
    private Integer idProvinciaPrioritaria;


    @ManyToOne
    @JoinColumn(name="id_provincia",nullable=false)
    private ProvinciaEntity provincia;


    @ManyToOne
    @JoinColumn(name="id_tipo_jugador",nullable=false)
    private TipoJugadorEntity tipoJugador;


}
