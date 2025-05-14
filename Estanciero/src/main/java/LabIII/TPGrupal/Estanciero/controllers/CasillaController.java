package LabIII.TPGrupal.Estanciero.controllers;

import LabIII.TPGrupal.Estanciero.dtos.CasillaDto;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.services.CasillaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Estanciero/Casilla")
public class CasillaController {

    @Autowired
    private CasillaService casillaService;


    @GetMapping("/GetAll")
    public ResponseEntity<List<Casilla>> listarCasillas() {

        List<Casilla> casillas = casillaService.GetAllCasillasBase();

        if (casillas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(casillas);
    }

}
