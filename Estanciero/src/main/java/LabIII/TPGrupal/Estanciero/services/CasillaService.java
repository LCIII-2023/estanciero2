package LabIII.TPGrupal.Estanciero.services;

import LabIII.TPGrupal.Estanciero.models.Casilla;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CasillaService {

    public List<Casilla> GetAllCasillasBase();

}
