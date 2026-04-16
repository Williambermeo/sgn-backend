package com.estebanleyton.gestionnomina.controller;

import com.estebanleyton.gestionnomina.model.HoraExtra;
import com.estebanleyton.gestionnomina.service.HoraExtraService;
import com.estebanleyton.gestionnomina.util.RolValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horas-extras")
@CrossOrigin(origins = "http://localhost:5173")
public class HoraExtraController {

    private final HoraExtraService horaExtraService;

    @Autowired
    public HoraExtraController(HoraExtraService horaExtraService) {
        this.horaExtraService = horaExtraService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(
            @RequestHeader("Rol") String rol,
            @RequestBody HoraExtra horaExtra) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        horaExtraService.registrarHoraExtra(horaExtra);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/empleado/{empleadoId}")
    public List<HoraExtra> listarPorEmpleado(
            @RequestHeader("Rol") String rol,
            @PathVariable Long empleadoId) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH", "SUPERVISOR");

        return horaExtraService.listarPorEmpleado(empleadoId);
    }
}
