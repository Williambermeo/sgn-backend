package com.estebanleyton.gestionnomina.service;

// Importo la entidad Empleado
import com.estebanleyton.gestionnomina.model.Empleado;

// Importo el repositorio de empleados
import com.estebanleyton.gestionnomina.repository.EmpleadoRepository;

// Importo las anotaciones necesarias de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * En esta clase realizo la implementación del servicio de gestión de empleados.
 * Aquí utilizo el repositorio para ejecutar la lógica relacionada con
 * las operaciones CRUD sobre la entidad Empleado.
 */
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    /*
     * Defino el repositorio de empleados como dependencia final,
     * ya que será inyectado por Spring y no cambiará.
     */
    private final EmpleadoRepository empleadoRepository;

    /*
     * Utilizo inyección de dependencias por constructor
     * para recibir el repositorio de empleados.
     */
    @Autowired
    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /*
     * Este método me permite obtener la lista completa
     * de empleados registrados en el sistema.
     */
    @Override
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    /*
     * Este método me permite buscar un empleado
     * a partir de su identificador.
     * Retorno un Optional para manejar el caso
     * en que el empleado no exista.
     */
    @Override
    public Optional<Empleado> buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    /*
     * Este método me permite guardar un nuevo empleado
     * o actualizar uno existente en la base de datos.
     */
    @Override
    public Empleado guardarEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    /*
     * Este método me permite eliminar un empleado
     * utilizando su identificador.
     */
    @Override
    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    /*
     * Este método me permite buscar un empleado
     * por su tipo de identificación y número de identificación.
     * La consulta se delega directamente al repositorio.
     */
    @Override
    public Optional<Empleado> buscarPorTipoEIdentificacion(
            String tipo,
            String identificacion
    ) {
        return empleadoRepository
                .findByTipoIdentificacionAndIdentificacion(tipo, identificacion);
    }

}
