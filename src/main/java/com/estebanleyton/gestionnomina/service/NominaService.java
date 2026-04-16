package com.estebanleyton.gestionnomina.service;

// Importo las entidades necesarias
import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.model.Nomina;

import java.util.List;
import java.util.Optional;

/*
 * En esta interfaz defino el servicio de gestión de nóminas.
 * Aquí establezco los métodos necesarios para el cálculo
 * y la consulta de las nóminas de los empleados.
 */
public interface NominaService {

    /*
     * Este método me permite calcular la nómina de un empleado,
     * teniendo en cuenta su salario base, horas extras y ventas.
     *
     * Retorno un objeto Nomina con todos los valores calculados.
     */
    Nomina calcularNomina(Empleado empleado);

    /*
     * Este método me permite obtener la lista de todas
     * las nóminas que han sido calculadas en el sistema.
     */
    List<Nomina> listarNominas();

    /*
     * Este método me permite buscar una nómina específica
     * a partir del empleado, el mes y el año.
     * Retorno un Optional para manejar el caso
     * en que la nómina no exista.
     */
    Optional<Nomina> buscarNominaPorPeriodo(
            Long empleadoId,
            int mes,
            int anio
    );

}
