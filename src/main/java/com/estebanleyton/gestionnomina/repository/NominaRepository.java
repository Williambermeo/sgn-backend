package com.estebanleyton.gestionnomina.repository;

// Importo la entidad Nómina
import com.estebanleyton.gestionnomina.model.Nomina;

// Importo JpaRepository para acceder a los métodos CRUD
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * En esta interfaz defino el repositorio para la entidad Nómina.
 * Extiendo JpaRepository para poder utilizar los métodos CRUD
 * y consultas automáticas proporcionadas por Spring Data JPA.
 */
public interface NominaRepository extends JpaRepository<Nomina, Long> {

    /*
     * Este método me permite verificar si ya existe una nómina
     * registrada para un empleado en un periodo específico.
     * Lo utilizo para evitar registros duplicados.
     */
    boolean existsByEmpleadoIdAndPeriodo(Long empleadoId, String periodo);

    /*
     * Este método me permite validar si ya existe una nómina
     * para un empleado en un mes y año determinados.
     * Esta es la forma correcta de bloquear la duplicación de nóminas.
     */
    boolean existsByEmpleadoIdAndMesAndAnio(
            Long empleadoId,
            int mes,
            int anio
    );

    /*
     * Este método me permite obtener todas las nóminas
     * asociadas a un empleado específico.
     */
    List<Nomina> findByEmpleadoId(Long empleadoId);

    /*
     * Este método me permite buscar una nómina específica
     * a partir del empleado, mes y año.
     */
    Optional<Nomina> findByEmpleadoIdAndMesAndAnio(
            Long empleadoId,
            int mes,
            int anio
    );
}
