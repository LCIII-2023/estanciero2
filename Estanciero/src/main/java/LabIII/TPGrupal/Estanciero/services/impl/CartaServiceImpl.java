package LabIII.TPGrupal.Estanciero.services.impl;

import LabIII.TPGrupal.Estanciero.entities.CartaEntity;
import LabIII.TPGrupal.Estanciero.models.Carta;
import LabIII.TPGrupal.Estanciero.models.Casilla;
import LabIII.TPGrupal.Estanciero.models.cards.*;
import LabIII.TPGrupal.Estanciero.models.enums.TipoCarta;
import LabIII.TPGrupal.Estanciero.repositories.CartaRepository;
import LabIII.TPGrupal.Estanciero.services.CartaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CartaServiceImpl implements CartaService {

    @Autowired
    public CartaRepository cartaRepository;


    @Override
    public List<Carta> GetAllCartasBase() {
        //
        List<CartaEntity> cartasE = cartaRepository.findAll();

        //
        List<Carta> cartas = new ArrayList<>();

        for (CartaEntity cartaE : cartasE) {

            if (Objects.equals(cartaE.getTipoCarta().getTipoCarta(), "Transaccion")){

                cartas.add(new CartaTransaccion(
                    cartaE.getIdCarta(),
                    cartaE.getDescripcion(),
                    TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                    cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("MoverCantidad")) {

                cartas.add(new CartaMoverCantidad(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("AvanzarHasta")) {

                cartas.add(new CartaAvanzarHasta(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("RetrocederHasta")) {

                cartas.add(new CartaRetrocederHasta(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("CobrarJugadores")) {

                cartas.add(new CartaCobrarJugadores(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("MultaSuerte")){

                cartas.add(new CartaMultaSuerte(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("MarchePreso")){

                cartas.add(new CartaMarchePreso(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta())
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("HabeasCorpus")){

                cartas.add(new CartaHabeasCorpus(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta())
                ));
            }

            else if (cartaE.getTipoCarta().getTipoCarta().equals("MultaMejora")){

                cartas.add(new CartaMultaSuerte(
                        cartaE.getIdCarta(),
                        cartaE.getDescripcion(),
                        TipoCarta.valueOf(cartaE.getTipoCarta().getTipoCarta()),
                        cartaE.getValor()
                ));
            }

        }

        return cartas;
    }
}
