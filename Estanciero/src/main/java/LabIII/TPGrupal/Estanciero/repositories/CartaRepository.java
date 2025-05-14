package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.CartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaRepository extends JpaRepository<CartaEntity, Integer> {
}
