package com.bbva.cambiomoneda.controllers;

import com.bbva.cambiomoneda.controllers.dto.*;
import com.bbva.cambiomoneda.mappers.MonedaMapper;
import com.bbva.cambiomoneda.repository.entity.Moneda;
import com.bbva.cambiomoneda.service.AuditoriaService;
import com.bbva.cambiomoneda.service.MonedaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moneda")
@CrossOrigin("*")
public class MonedaController {

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private MonedaService monedaService;

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

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
        MonedaDTO monedaBuscada = monedaService.obtenerPorNombre(monedaDTO.getNombre());
        if (monedaBuscada != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        MonedaDTO monedaGuardada = monedaService.guardar(monedaDTO);
        return new ResponseEntity<>(monedaGuardada, HttpStatus.OK);
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
        UsuarioDTO usuarioDTOSession = (UsuarioDTO) httpSessionFactory.getObject().getAttribute("usuario");
        if (usuarioDTOSession == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        AuditoriaDTO auditoriaDTO = AuditoriaDTO
                .builder()
                .detalle(solicitudCambioDTO.getMonto() + " " + monedaLocal.getNombre() + "->" + montoFinal + " " + monedaDestino.getNombre() + " ")
                .nombre_usuario(usuarioDTOSession.getNombre())
                .fecha_hora(LocalDateTime.now()).build();

        auditoriaService.guardar(auditoriaDTO);


        return new ResponseEntity<>(dtoRespuesta, HttpStatus.OK);
    }

    @PostMapping(value = "/actualizar")
    public ResponseEntity<MonedaDTO> actualizarMoneda(@RequestBody MonedaDTO monedaDTO) {
        if (monedaDTO.getId() == 0) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        MonedaDTO monedaActualizada = monedaService.actualizar(monedaDTO);
        UsuarioDTO usuarioDTOSession = (UsuarioDTO) httpSessionFactory.getObject().getAttribute("usuario");
        StringBuilder sb = new StringBuilder();
        sb.append("Se actualizó la moneda a ")
                .append(monedaActualizada.getNombre())
                .append(" con el precio de compra: ")
                .append(monedaActualizada.getCompra())
                .append("Y el precio de venta: ")
                .append(monedaActualizada.getVenta());

        AuditoriaDTO auditoriaDTO = AuditoriaDTO
                .builder()
                .fecha_hora(LocalDateTime.now())
                .nombre_usuario(usuarioDTOSession.getNombre())
                .detalle(sb.toString())
                .build();
        auditoriaService.guardar(auditoriaDTO);
        return new ResponseEntity<>(monedaActualizada, HttpStatus.OK);
    }

    @GetMapping(value = "/buscar/{moneda}")
    public ResponseEntity<MonedaDTO> buscar(@PathVariable String moneda) {
        MonedaDTO monedaDTO = monedaService.obtenerPorNombre(moneda);
        if (monedaDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(monedaDTO, HttpStatus.OK);
        }
    }
}
