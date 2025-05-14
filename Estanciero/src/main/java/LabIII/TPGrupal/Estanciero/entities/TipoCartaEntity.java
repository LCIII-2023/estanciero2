package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Tipo_Carta")
@Data
public class TipoCartaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tipo_carta")
    private Integer idTipoCarta;

    @Column(name="tipo_carta")
    private String tipoCarta;

}
