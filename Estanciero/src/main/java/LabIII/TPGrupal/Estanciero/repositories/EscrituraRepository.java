package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.EscrituraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscrituraRepository extends JpaRepository<EscrituraEntity, Integer> {

    Optional<EscrituraEntity> findByPropiedad_Casilla_IdCasilla(Integer id_casilla);
}
