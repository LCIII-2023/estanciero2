package LabIII.TPGrupal.Estanciero.repositories;

import LabIII.TPGrupal.Estanciero.entities.CasillaEntity;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasillaRepository extends JpaRepository<CasillaEntity, Long> { }
