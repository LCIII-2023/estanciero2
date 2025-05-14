package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Tipo_Jugador")
@Data
public class TipoJugadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_tipo_jugador")
    private Integer idTipoJugador;

    @Column(name="tipo_jugador")
    private String tipoJugador;

}
