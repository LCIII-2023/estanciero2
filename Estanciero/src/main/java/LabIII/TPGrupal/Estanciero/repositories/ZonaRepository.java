package LabIII.TPGrupal.Estanciero.repositories;


import LabIII.TPGrupal.Estanciero.entities.ZonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<ZonaEntity, Integer> {
}
