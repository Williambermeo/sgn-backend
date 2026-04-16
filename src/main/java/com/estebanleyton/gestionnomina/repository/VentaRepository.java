package com.estebanleyton.gestionnomina.repository;

// Importo la entidad Venta
import com.estebanleyton.gestionnomina.model.Venta;

// Importo JpaRepository para acceder a los métodos CRUD
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/*
 * En esta interfaz defino el repositorio para la entidad Venta.
 * Extiendo JpaRepository para poder utilizar los métodos CRUD
 * y las consultas automáticas generadas por Spring Data JPA.
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {

    /*
     * Este método me permite obtener todas las ventas
     * asociadas a un empleado específico.
     */
    List<Venta> findByEmpleadoId(Long empleadoId);

    /*
     * Este método me permite obtener únicamente las ventas
     * realizadas por un empleado dentro de un periodo de tiempo.
     * Lo utilizo para calcular comisiones por mes o rango de fechas.
     */
    List<Venta> findByEmpleadoIdAndFechaBetween(
            Long empleadoId,
            LocalDate inicio,
            LocalDate fin
    );
}
