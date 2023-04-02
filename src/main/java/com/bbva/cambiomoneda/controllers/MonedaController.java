package com.bbva.cambiomoneda.controllers;

import com.bbva.cambiomoneda.controllers.dto.MonedaDTO;
import com.bbva.cambiomoneda.controllers.dto.RespuestaCambioDTO;
import com.bbva.cambiomoneda.controllers.dto.SolicitudCambioDTO;
import com.bbva.cambiomoneda.mappers.MonedaMapper;
import com.bbva.cambiomoneda.repository.entity.Moneda;
import com.bbva.cambiomoneda.service.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moneda")
public class MonedaController {

    @Autowired
    private MonedaMapper monedaMapper;

    @Autowired
    private MonedaService monedaService;

    @GetMapping(value = "/listar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonedaDTO>> listar() {
        try {
            List<MonedaDTO> monedaDTOList = monedaService.listar();
            return new ResponseEntity<>(monedaDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity<MonedaDTO> registrar(@RequestBody MonedaDTO monedaDTO) {
        MonedaDTO monedaGuardada = monedaService.guardar(monedaDTO);
        return new ResponseEntity(monedaGuardada, HttpStatus.OK);
    }

    @PostMapping(value = "/cambiar", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaCambioDTO> cambiar(@RequestBody SolicitudCambioDTO solicitudCambioDTO) {

        MonedaDTO monedaLocal = monedaService.obtenerPorNombre(solicitudCambioDTO.getMonedaLocal());
        MonedaDTO monedaDestino = monedaService.obtenerPorNombre(solicitudCambioDTO.getMonedaDestino());

        double montoFinal;
        if (solicitudCambioDTO.isCompra()) {
            if (monedaLocal.getCompra() < monedaDestino.getCompra()) {
                montoFinal = solicitudCambioDTO.getMonto() / monedaDestino.getCompra();
            } else {
                montoFinal = solicitudCambioDTO.getMonto() * monedaDestino.getCompra();
            }
        } else {
            if (monedaLocal.getVenta() < monedaDestino.getVenta()) {
                montoFinal = solicitudCambioDTO.getMonto() / monedaDestino.getVenta();
            } else {
                montoFinal = solicitudCambioDTO.getMonto() * monedaDestino.getVenta();

            }
        }
        RespuestaCambioDTO dtoRespuesta = RespuestaCambioDTO.builder()
                .tipoCambio(solicitudCambioDTO.isCompra() ? "compra" : "venta")
                .monto(montoFinal)
                .monedas(monedaLocal.getNombre() + "->" + monedaDestino.getNombre())
                .build();
        return new ResponseEntity<>(dtoRespuesta, HttpStatus.OK);
    }
}
