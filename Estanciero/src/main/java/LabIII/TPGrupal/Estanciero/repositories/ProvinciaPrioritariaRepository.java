package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.ProvinciaPrioritariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinciaPrioritariaRepository extends JpaRepository<ProvinciaPrioritariaEntity, Integer> {
    List<ProvinciaPrioritariaEntity> findAllByTipoJugador_TipoJugador(String tipo);
}
