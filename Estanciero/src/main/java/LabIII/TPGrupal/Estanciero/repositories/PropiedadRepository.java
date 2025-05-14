package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.PropiedadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PropiedadRepository extends JpaRepository<PropiedadEntity, Integer> {

    Optional<PropiedadEntity> findByCasilla_IdCasilla(Integer idCasilla);
}
