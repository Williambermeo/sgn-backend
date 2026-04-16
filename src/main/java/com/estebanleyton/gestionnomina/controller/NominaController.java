package com.estebanleyton.gestionnomina.controller;

import com.estebanleyton.gestionnomina.model.Nomina;
import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.service.NominaService;
import com.estebanleyton.gestionnomina.service.EmpleadoService;
import com.estebanleyton.gestionnomina.util.RolValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones relacionadas con la Nómina.
 */
@RestController
@RequestMapping("/api/nomina")
@CrossOrigin(origins = "http://localhost:5173")
public class NominaController {

    @Autowired
    private NominaService nominaService;

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Calcular la nómina para un empleado dado.
     */
    @PostMapping("/calcular/{empleadoId}")
    public ResponseEntity<?> calcularNomina(
            @RequestHeader("Rol") String rol,
            @PathVariable Long empleadoId) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        Optional<Empleado> empleadoOpt =
                empleadoService.buscarEmpleadoPorId(empleadoId);

        if (empleadoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Nomina nominaCalculada =
                    nominaService.calcularNomina(empleadoOpt.get());

            return ResponseEntity.ok(nominaCalculada);

        } catch (IllegalStateException e) {
            // Nómina duplicada en el mismo mes
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /**
     * Listar todas las nóminas calculadas.
     */
    @GetMapping
    public List<Nomina> listarNominas(
            @RequestHeader("Rol") String rol) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        return nominaService.listarNominas();
    }

    /**
     * Consultar nómina por empleado, mes y año.
     */
    @GetMapping("/consultar")
    public ResponseEntity<Nomina> consultarNomina(
            @RequestHeader("Rol") String rol,
            @RequestParam Long empleadoId,
            @RequestParam int mes,
            @RequestParam int anio
    ) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        return nominaService
                .buscarNominaPorPeriodo(empleadoId, mes, anio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
