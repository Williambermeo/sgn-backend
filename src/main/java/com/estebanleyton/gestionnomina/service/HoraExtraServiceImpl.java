package com.estebanleyton.gestionnomina.service;

// Importo las entidades necesarias
import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.model.HoraExtra;

// Importo los repositorios utilizados
import com.estebanleyton.gestionnomina.repository.EmpleadoRepository;
import com.estebanleyton.gestionnomina.repository.HoraExtraRepository;

// Importo las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * En esta clase realizo la implementación del servicio de horas extras.
 * Aquí gestiono el registro de horas extras y su asociación con los empleados.
 */
@Service
public class HoraExtraServiceImpl implements HoraExtraService {

    /*
     * Defino los repositorios necesarios como dependencias finales,
     * los cuales son inyectados por Spring.
     */
    private final HoraExtraRepository horaExtraRepository;
    private final EmpleadoRepository empleadoRepository;

    /*
     * Utilizo inyección de dependencias por constructor
     * para recibir los repositorios de horas extras y empleados.
     */
    @Autowired
    public HoraExtraServiceImpl(
            HoraExtraRepository horaExtraRepository,
            EmpleadoRepository empleadoRepository
    ) {
        this.horaExtraRepository = horaExtraRepository;
        this.empleadoRepository = empleadoRepository;
    }

    /*
     * Este método me permite registrar una hora extra para un empleado.
     * Primero valido que el empleado exista y luego guardo la hora extra.
     * Adicionalmente actualizo el total de horas extras acumuladas
     * en el registro del empleado.
     */
    @Override
    public void registrarHoraExtra(HoraExtra horaExtra) {

        /*
         * Busco el empleado asociado a la hora extra.
         * Si no existe, lanzo una excepción.
         */
        Empleado empleado = empleadoRepository.findById(
                horaExtra.getEmpleado().getId()
        ).orElseThrow(() ->
                new RuntimeException("Empleado no encontrado")
        );

        /*
         * Asigno el empleado recuperado a la hora extra
         * y guardo el registro en la base de datos.
         */
        horaExtra.setEmpleado(empleado);
        horaExtraRepository.save(horaExtra);

        /*
         * Actualizo las horas extras acumuladas del empleado.
         * Si no tiene horas registradas previamente, inicio en cero.
         */
        int horasActuales = empleado.getHorasExtras() != null
                ? empleado.getHorasExtras()
                : 0;

        empleado.setHorasExtras(
                horasActuales + (int) horaExtra.getHoras()
        );

        /*
         * Guardo los cambios realizados en el empleado.
         */
        empleadoRepository.save(empleado);
    }

    /*
     * Este método me permite listar todas las horas extras
     * registradas para un empleado específico.
     */
    @Override
    public List<HoraExtra> listarPorEmpleado(Long empleadoId) {
        return horaExtraRepository.findByEmpleadoId(empleadoId);
    }
}
