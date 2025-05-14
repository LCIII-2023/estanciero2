package LabIII.TPGrupal.Estanciero.controllers;

import LabIII.TPGrupal.Estanciero.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Estanciero/Jugador")
public class JugadorController {

    @Autowired
    JugadorService jugadorService;

    @GetMapping("/GetProvinciasP/{tipo}")
    public ResponseEntity<List<String>> listarProvinciasP(@PathVariable String tipo) {

        List<String> provinciasP = jugadorService.GetProvinciasPByTipo(tipo);

        if (provinciasP.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(provinciasP);
    }
}
