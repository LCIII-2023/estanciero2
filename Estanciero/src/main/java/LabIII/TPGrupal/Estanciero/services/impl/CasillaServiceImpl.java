package LabIII.TPGrupal.Estanciero.services.impl;


import LabIII.TPGrupal.Estanciero.entities.*;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.cells.*;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Compañia;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Escritura;
import LabIII.TPGrupal.Estanciero.models.cells.properties.Ferrocarril;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCasilla;
import LabIII.TPGrupal.Estanciero.repositories.*;
import LabIII.TPGrupal.Estanciero.services.CasillaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CasillaServiceImpl implements CasillaService {
    //Inyecciones
    @Autowired
    CasillaRepository casillaRepository;

    @Autowired
    PropiedadRepository propiedadRepository;

    @Autowired
    EscrituraRepository escrituraRepository;

    @Autowired
    TributarioRepository tributarioRepository;

    @Autowired
    ModelMapper modelMapper;

    //
    @Override
    public List<Casilla> GetAllCasillasBase() {
        //Busqueda de todas las casillasEntity
        List<CasillaEntity> casillasE = casillaRepository.findAll();

        //Creacion de la lista de Casillas que se tiene que devolver
        List<Casilla> casillas = new ArrayList<>();

        //Recorrido de la lista de casillasEntity para mapearlas (manualmente) a su respectiva clase hija
        for (CasillaEntity casillaE : casillasE) {

            //-------------------------ESCRITURA-------------------------//
            switch (casillaE.getTipoCasilla().getTipoCasilla()) {
                case "Escritura" -> {

                    Optional<EscrituraEntity> entidad = escrituraRepository.findByPropiedad_Casilla_IdCasilla(casillaE.getIdCasilla());

                    casillas.add(new Escritura(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            entidad.get().getPropiedad().getPrecio(),
                            entidad.get().getPropiedad().getAlquiler().getAlquileres(),
                            entidad.get().getPropiedad().getValorHipoteca(),
                            false,
                            null,
                            entidad.get().getProvincia().getProvincia(),
                            entidad.get().getZona().getZona(),
                            0,
                            entidad.get().getProvincia().getCosteMejora()
                    ));

//                    casillas.add(modelMapper.map(entidad.get(), Escritura.class));
                }


                //-------------------------FERROCARRIL-------------------------//
                case "Ferrocarril" -> {

                    Optional<PropiedadEntity> entidad = propiedadRepository.findByCasilla_IdCasilla(casillaE.getIdCasilla());

                    casillas.add(new Ferrocarril(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            entidad.get().getPrecio(),
                            entidad.get().getAlquiler().getAlquileres(),
                            entidad.get().getValorHipoteca(),
                            false,
                            null
                    ));
                }


                //-------------------------COMPAÑIA-------------------------//
                case "Compañia" -> {

                    Optional<PropiedadEntity> entidad = propiedadRepository.findByCasilla_IdCasilla(casillaE.getIdCasilla());

                    casillas.add(new Compañia(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            entidad.get().getPrecio(),
                            entidad.get().getAlquiler().getAlquileres(),
                            entidad.get().getValorHipoteca(),
                            false,
                            null
                    ));
                }


                //-------------------------SUERTE_&_DESTINO-------------------------//
                case "SuerteDestino" -> {

                    String descripcion = "Levante una carta del mazo " + casillaE.getNombre();

                    casillas.add(new SuerteDestino(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            descripcion
                    ));
                }

                //-------------------------TRIBUTARIO-------------------------//
                case "Tributario" -> {

                    Optional<TributarioEntity> entidad = tributarioRepository.findByCasilla_IdCasilla(casillaE.getIdCasilla());

                    casillas.add(new Tributario(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            entidad.get().getDescripcion(),
                            entidad.get().getMonto()
                    ));
                }


                //-------------------------COMISARIA-------------------------//
                case "Comisaria" ->{

                    casillas.add(new Comisaria(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            "Usted esta de visita",
                            new ArrayList<>()
                    ));
                }


                //-------------------------DESCANSO-------------------------//
                case "Descanso" ->{

                    casillas.add(new Descanso(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            "Puede elegir permanecer en la casilla",
                            new ArrayList<>()
                    ));
                }


                //-------------------------MARCHE_PRESO-------------------------//
                case "MarchePreso" ->{

                    casillas.add(new MarchePreso(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            "Marche preso directamente"
                    ));
                }



                //-------------------------ESTACIONAMIENTO_LIBRE-------------------------//
                case "EstacionamientoLibre" ->{

                    casillas.add(new EstacionamientoLibre(
                            casillaE.getIdCasilla(),
                            casillaE.getNombre(),
                            TipoCasilla.valueOf(casillaE.getTipoCasilla().getTipoCasilla()),
                            "Esta casilla no hace nada en especial"
                    ));
                }


                default -> {
                    System.out.println("\nTipo: Error");
                }
            }

        }

        return casillas;
    }



}
