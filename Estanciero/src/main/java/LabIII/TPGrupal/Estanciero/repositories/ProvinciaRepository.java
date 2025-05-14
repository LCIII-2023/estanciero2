package LabIII.TPGrupal.Estanciero.repositories;


import LabIII.TPGrupal.Estanciero.entities.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Integer> {
}
