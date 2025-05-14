package LabIII.TPGrupal.Estanciero.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JugadorService {
    public List<String> GetProvinciasPByTipo(String tipo);
}
