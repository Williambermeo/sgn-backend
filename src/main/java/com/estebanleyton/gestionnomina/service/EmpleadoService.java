package com.estebanleyton.gestionnomina.service;

// Importo la entidad Empleado
import com.estebanleyton.gestionnomina.model.Empleado;

import java.util.List;
import java.util.Optional;

/*
 * En esta interfaz defino el servicio de gestión de empleados.
 * Aquí establezco las operaciones principales que se pueden
 * realizar sobre los empleados dentro del sistema.
 */
public interface EmpleadoService {

    /*
     * Este método me permite obtener la lista de todos
     * los empleados registrados en el sistema.
     */
    List<Empleado> listarEmpleados();

    /*
     * Este método me permite buscar un empleado
     * a partir de su identificador.
     */
    Optional<Empleado> buscarEmpleadoPorId(Long id);

    /*
     * Este método me permite guardar un nuevo empleado
     * o actualizar uno existente.
     */
    Empleado guardarEmpleado(Empleado empleado);

    /*
     * Este método me permite eliminar un empleado
     * a partir de su identificador.
     */
    void eliminarEmpleado(Long id);

    /*
     * Este método me permite buscar un empleado
     * por su tipo de identificación y número de identificación.
     * Solo se define la firma, la implementación se realiza en la clase service.
     */
    Optional<Empleado> buscarPorTipoEIdentificacion(
            String tipo,
            String identificacion
    );

}
