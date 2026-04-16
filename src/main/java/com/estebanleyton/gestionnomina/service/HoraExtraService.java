package com.estebanleyton.gestionnomina.service;

// Importo la entidad HoraExtra
import com.estebanleyton.gestionnomina.model.HoraExtra;

import java.util.List;

/*
 * En esta interfaz defino el servicio para la gestión de horas extras.
 * Aquí establezco las operaciones principales relacionadas
 * con el registro y la consulta de horas extras.
 */
public interface HoraExtraService {

    /*
     * Este método me permite registrar una hora extra
     * asociada a un empleado.
     */
    void registrarHoraExtra(HoraExtra horaExtra);

    /*
     * Este método me permite obtener la lista de horas extras
     * registradas para un empleado específico.
     */
    List<HoraExtra> listarPorEmpleado(Long empleadoId);
}
