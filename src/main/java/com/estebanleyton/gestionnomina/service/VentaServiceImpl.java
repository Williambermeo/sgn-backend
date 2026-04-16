package com.estebanleyton.gestionnomina.service;

// Importo las entidades necesarias
import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.model.Venta;

// Importo los repositorios utilizados
import com.estebanleyton.gestionnomina.repository.EmpleadoRepository;
import com.estebanleyton.gestionnomina.repository.VentaRepository;

// Importo la excepción personalizada para reglas de negocio
import com.estebanleyton.gestionnomina.exception.BadRequestException;

// Importo las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/*
 * En esta clase realizo la implementación del servicio de ventas.
 * Aquí gestiono el registro de ventas y su asociación con los empleados,
 * así como la consulta de ventas por empleado y por rango de fechas.
 */
@Service
public class VentaServiceImpl implements VentaService {

    /*
     * Defino los repositorios necesarios como dependencias finales,
     * los cuales son inyectados por Spring.
     */
    private final VentaRepository ventaRepository;
    private final EmpleadoRepository empleadoRepository;

    /*
     * Utilizo inyección de dependencias por constructor
     * para recibir los repositorios de ventas y empleados.
     */
    @Autowired
    public VentaServiceImpl(
            VentaRepository ventaRepository,
            EmpleadoRepository empleadoRepository
    ) {
        this.ventaRepository = ventaRepository;
        this.empleadoRepository = empleadoRepository;
    }

    /*
     * Este método me permite registrar una lista de ventas
     * asociadas a un empleado específico.
     */
    @Override
    public void registrarVentas(Long empleadoId, List<Venta> ventas) {

        /*
         * Busco el empleado por su identificador.
         * Si no existe, lanzo una excepción.
         */
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        /*
         * Recorro la lista de ventas recibidas,
         * asigno el empleado y calculo el subtotal de cada venta.
         */
        for (Venta v : ventas) {

            /*
             * VALIDACIONES CONTABLES IMPORTANTES
             * No permito valores negativos ni cero en unidades o valor unitario.
             */
            if (v.getUnidades() == null || v.getUnidades() <= 0) {
                throw new BadRequestException("Las unidades deben ser mayores que cero");
            }

            if (v.getValorUnidad() == null || v.getValorUnidad() <= 0) {
                throw new BadRequestException("El valor unitario debe ser mayor que cero");
            }

            v.setEmpleado(empleado);
            v.setSubtotal(v.getUnidades() * v.getValorUnidad());

            ventaRepository.save(v);
        }

        /*
         * Calculo el total de ventas registradas en esta operación.
         */
        double totalVentas = ventas.stream()
                .mapToDouble(Venta::getSubtotal)
                .sum();

        /*
         * Actualizo el valor acumulado de ventas realizadas
         * en el registro del empleado.
         */
        empleado.setVentasRealizadas(
                (empleado.getVentasRealizadas() == null
                        ? 0
                        : empleado.getVentasRealizadas())
                        + totalVentas
        );

        /*
         * Guardo los cambios realizados en el empleado.
         */
        empleadoRepository.save(empleado);
    }

    /*
     * Este método me permite listar todas las ventas
     * registradas para un empleado específico.
     */
    @Override
    public List<Venta> listarVentasPorEmpleado(Long empleadoId) {
        return ventaRepository.findByEmpleadoId(empleadoId);
    }

    /*
     * Este método me permite listar las ventas realizadas
     * por un empleado dentro de un rango de fechas.
     */
    @Override
    public List<Venta> listarVentasPorEmpleadoYRango(
            Long empleadoId,
            LocalDate desde,
            LocalDate hasta
    ) {
        return ventaRepository.findByEmpleadoIdAndFechaBetween(
                empleadoId,
                desde,
                hasta
        );
    }
}