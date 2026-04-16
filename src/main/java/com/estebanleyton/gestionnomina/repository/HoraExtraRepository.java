package com.estebanleyton.gestionnomina.repository;

// Importo la entidad HoraExtra
import com.estebanleyton.gestionnomina.model.HoraExtra;

// Importo JpaRepository para acceder a los métodos CRUD
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.LocalDate;

/*
 * En esta interfaz defino el repositorio para la entidad HoraExtra.
 * Extiendo JpaRepository para poder utilizar los métodos CRUD
 * sin necesidad de escribir consultas manuales.
 */
public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {

    /*
     * Este método me permite obtener todas las horas extras
     * registradas para un empleado específico.
     */
    List<HoraExtra> findByEmpleadoId(Long empleadoId);

    /*
     * Este método me permite obtener las horas extras de un empleado
     * dentro de un rango de fechas determinado.
     *
     * Spring Data JPA genera automáticamente la consulta
     * a partir del nombre del método.
     */
    List<HoraExtra> findByEmpleadoIdAndFechaBetween(
            Long empleadoId,
            LocalDate desde,
            LocalDate hasta
    );
}
