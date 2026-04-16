package com.estebanleyton.gestionnomina.repository;

// Importo Optional para manejar resultados que pueden o no existir
import java.util.Optional;

// Importo la entidad Empleado
import com.estebanleyton.gestionnomina.model.Empleado;

// Importo JpaRepository para acceder a los métodos CRUD de JPA
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * En esta interfaz defino el repositorio para la entidad Empleado.
 * Extiendo JpaRepository para poder utilizar los métodos CRUD
 * sin necesidad de implementarlos manualmente.
 */
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    /*
     * Este método me permite buscar un empleado a partir de su
     * tipo de identificación y número de identificación.
     *
     * Spring Data JPA genera automáticamente la consulta
     * basándose en el nombre del método.
     */
    Optional<Empleado> findByTipoIdentificacionAndIdentificacion(
            String tipoIdentificacion,
            String identificacion
    );

}
