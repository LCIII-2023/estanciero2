package LabIII.TPGrupal.Estanciero.services.impl;

import LabIII.TPGrupal.Estanciero.entities.ProvinciaPrioritariaEntity;
import LabIII.TPGrupal.Estanciero.repositories.ProvinciaPrioritariaRepository;
import LabIII.TPGrupal.Estanciero.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JugadorServiceImpl implements JugadorService {

    @Autowired
    ProvinciaPrioritariaRepository provinciaPrioritariaRepository;

    @Override
    public List<String> GetProvinciasPByTipo(String tipo) {
        //Consulta de las entidades
        List<ProvinciaPrioritariaEntity> provinciasP = provinciaPrioritariaRepository.findAllByTipoJugador_TipoJugador(tipo);

        //Creacion de la lista de string para devolver
        List<String> provinciasPrioritarias = new ArrayList<>();

        //Agregado de los nombres de las provincias a la lista de strings
        for (ProvinciaPrioritariaEntity p : provinciasP) {
            provinciasPrioritarias.add(p.getProvincia().getProvincia());
        }

        return provinciasPrioritarias;
    }
}
