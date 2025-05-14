package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Provincia")
@Data
public class ProvinciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_provincia")
    private Integer idProvincia;

    @Column(name="provincia")
    private String provincia;

    @Column(name="coste_mejora")
    private Integer costeMejora;

}
