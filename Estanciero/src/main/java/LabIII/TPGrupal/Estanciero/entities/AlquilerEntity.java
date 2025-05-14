package LabIII.TPGrupal.Estanciero.entities;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Alquiler")
@Data
public class AlquilerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_alquiler")
    private Integer idAlquiler;


    @Column(name="alquiler_1")
    private Integer alquiler1;

    @Column(name="alquiler_2")
    private Integer alquiler2;

    @Column(name="alquiler_3")
    private Integer alquiler3;

    @Column(name="alquiler_4")
    private Integer alquiler4;

    @Column(name="alquiler_5")
    private Integer alquiler5;

    @Column(name="alquiler_6")
    private Integer alquiler6;


    //Esto es horrible, lo se... Y a estas alturas no me importa
    public int[] getAlquileres() {
        List<Integer> valores = new ArrayList<>();
        if (alquiler1 != null) {
            valores.add(alquiler1);
        }
        if (alquiler2 != null) {
            valores.add(alquiler2);
        }
        if (alquiler3 != null) {
            valores.add(alquiler3);
        }
        if (alquiler4 != null) {
            valores.add(alquiler4);
        }
        if (alquiler5 != null) {
            valores.add(alquiler5);
        }
        if (alquiler6 != null) {
            valores.add(alquiler6);
        }

        return valores.stream().mapToInt(Integer::intValue).toArray();
    }
}
