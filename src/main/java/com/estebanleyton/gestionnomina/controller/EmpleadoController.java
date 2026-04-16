package com.estebanleyton.gestionnomina.controller;

import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.estebanleyton.gestionnomina.util.RolValidator;

/**
 * Controlador REST para operaciones relacionadas con Empleados.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Obtener lista de todos los empleados.
     *
     * @return Lista de empleados.
     */
    @GetMapping
    public List<Empleado> listarEmpleados(
            @RequestHeader("Rol") String rol) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        return empleadoService.listarEmpleados();
    }

    /**
     * Obtener empleado por ID.
     *
     * @param id ID del empleado.
     * @return Empleado encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(
            @RequestHeader("Rol") String rol,
            @PathVariable Long id) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        Optional<Empleado> empleado = empleadoService.buscarEmpleadoPorId(id);
        return empleado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo empleado.
     *
     * @param empleado Datos del empleado a crear (validado automáticamente).
     * @return Empleado creado con código 201.
     */
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(
            @RequestHeader("Rol") String rol,
            @Valid @RequestBody Empleado empleado) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        Empleado nuevoEmpleado = empleadoService.guardarEmpleado(empleado);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    /**
     * Actualizar un empleado existente.
     *
     * @param id       ID del empleado a actualizar.
     * @param empleado Datos actualizados del empleado (validado automáticamente).
     * @return Empleado actualizado o 404 si no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(
            @RequestHeader("Rol") String rol,
            @PathVariable Long id,
            @Valid @RequestBody Empleado empleado) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH");

        Optional<Empleado> empleadoExistente = empleadoService.buscarEmpleadoPorId(id);
        if (empleadoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        empleado.setId(id);
        Empleado actualizado = empleadoService.guardarEmpleado(empleado);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Eliminar un empleado por ID.
     *
     * @param id ID del empleado a eliminar.
     * @return Respuesta vacía con código 204 o 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(
            @RequestHeader("Rol") String rol,
            @PathVariable Long id) {

        RolValidator.validarRol(rol, "ADMIN");

        try {
            empleadoService.eliminarEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Buscar empleado por tipo de identificación y número.
     *
     * Ejemplo:
     * /api/empleados/buscar?tipo=Cédula&identificacion=1083894079
     */
    /**
     * Buscar empleado por tipo de identificación e identificación
     */
    @GetMapping("/buscar")
    public ResponseEntity<Empleado> buscarPorIdentificacion(
            @RequestHeader("Rol") String rol,
            @RequestParam String tipo,
            @RequestParam String identificacion) {

        RolValidator.validarRol(rol, "ADMIN", "RRHH", "SUPERVISOR");

        Optional<Empleado> empleado = empleadoService
                .buscarPorTipoEIdentificacion(tipo, identificacion);

        return empleado
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
