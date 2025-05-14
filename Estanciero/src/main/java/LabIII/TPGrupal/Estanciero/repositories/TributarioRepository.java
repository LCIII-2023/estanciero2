package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.TributarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TributarioRepository extends JpaRepository<TributarioEntity, Integer> {
    Optional<TributarioEntity> findByCasilla_IdCasilla(int idCasilla);
}
