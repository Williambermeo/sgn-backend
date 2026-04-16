package com.estebanleyton.gestionnomina.controller;

// Importo el DTO que utilizo para recibir el registro de ventas desde el frontend
import com.estebanleyton.gestionnomina.dto.RegistroVentasDTO;

// Importo la entidad Venta
import com.estebanleyton.gestionnomina.model.Venta;

// Importo el servicio que contiene la lógica de negocio para las ventas
import com.estebanleyton.gestionnomina.service.VentaService;

import com.estebanleyton.gestionnomina.util.RolValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

// Marco esta clase como un controlador REST
@RestController

// Defino la ruta base para todos los endpoints relacionados con ventas
@RequestMapping("/api/ventas")

// Permito peticiones desde el frontend que se ejecuta en localhost:5173
@CrossOrigin(origins = "http://localhost:5173")
public class VentaController {

    // Inyecto el servicio de ventas para poder usar sus métodos
    @Autowired
    private VentaService ventaService;

    // Endpoint para registrar ventas asociadas a un empleado
    @PostMapping(
            value = "/registrar",
            consumes = "application/json",
            produces = "application/json"
    )
    public void registrarVentas(
            @RequestHeader("Rol") String rol,
            @RequestBody RegistroVentasDTO dto) {

        RolValidator.validarRol(rol, "ADMIN", "SUPERVISOR");

        /*
         * Utilizo estos mensajes de consola de forma temporal
         * para verificar que los datos lleguen correctamente
         * desde el frontend o desde Postman.
         */
        System.out.println("DTO recibido en /api/ventas/registrar:");
        System.out.println(dto);

        /*
         * Convierto la lista de ventas que llega en el DTO
         * a una lista de entidades Venta usando streams.
         * Aquí construyo cada objeto Venta con el patrón builder.
         */
        List<Venta> ventas = dto.getVentas().stream()
                .map(v -> Venta.builder()
                        .fecha(v.getFecha())
                        .producto(v.getProducto())
                        .unidades(v.getUnidades())
                        .valorUnidad(v.getValorUnidad())
                        .build())
                .collect(Collectors.toList());

        /*
         * Envío el id del empleado y la lista de ventas al servicio,
         * donde se maneja la lógica de negocio y el guardado en la base de datos.
         */
        ventaService.registrarVentas(dto.getEmpleadoId(), ventas);
    }

    /*
     * Endpoint para listar todas las ventas registradas
     * de un empleado específico.
     */
    @GetMapping("/empleado/{empleadoId}")
    public List<Venta> listarVentasEmpleado(
            @RequestHeader("Rol") String rol,
            @PathVariable Long empleadoId) {

        RolValidator.validarRol(rol, "ADMIN", "SUPERVISOR");

        return ventaService.listarVentasPorEmpleado(empleadoId);
    }

    /*
     * Endpoint para listar las ventas de un empleado
     * dentro de un rango de fechas determinado.
     * Las fechas se reciben como parámetros de la URL.
     */
    @GetMapping("/empleado/{empleadoId}/rango")
    public List<Venta> listarVentasPorRango(
            @RequestHeader("Rol") String rol,
            @PathVariable Long empleadoId,
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta
    ) {

        RolValidator.validarRol(rol, "ADMIN", "SUPERVISOR");

        return ventaService.listarVentasPorEmpleadoYRango(
                empleadoId,
                desde,
                hasta
        );
    }
}
