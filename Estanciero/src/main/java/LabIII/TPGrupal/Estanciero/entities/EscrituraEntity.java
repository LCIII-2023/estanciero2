package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Escritura")
@Data
public class EscrituraEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_escritura")
    private Integer idEscritura;


    @ManyToOne
    @JoinColumn(name="id_propiedad",nullable=false)
    private PropiedadEntity propiedad;

    @ManyToOne
    @JoinColumn(name="id_provincia",nullable=false)
    private ProvinciaEntity provincia;

    @ManyToOne
    @JoinColumn(name="id_zona",nullable=false)
    private ZonaEntity zona;
}
