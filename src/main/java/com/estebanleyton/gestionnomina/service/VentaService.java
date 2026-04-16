package com.estebanleyton.gestionnomina.service;

// Importo la entidad Venta
import com.estebanleyton.gestionnomina.model.Venta;

import java.time.LocalDate;
import java.util.List;

/*
 * En esta interfaz defino el servicio para la gestión de ventas.
 * Aquí establezco las operaciones necesarias para el registro
 * y la consulta de las ventas realizadas por los empleados.
 */
public interface VentaService {

    /*
     * Este método me permite registrar una o varias ventas
     * asociadas a un empleado específico.
     */
    void registrarVentas(Long empleadoId, List<Venta> ventas);

    /*
     * Este método me permite obtener todas las ventas
     * realizadas por un empleado.
     */
    List<Venta> listarVentasPorEmpleado(Long empleadoId);

    /*
     * Este método me permite obtener las ventas realizadas
     * por un empleado dentro de un rango de fechas determinado.
     */
    List<Venta> listarVentasPorEmpleadoYRango(
            Long empleadoId,
            LocalDate inicio,
            LocalDate fin
    );
}
