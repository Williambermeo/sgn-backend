package com.estebanleyton.gestionnomina.service;

// Importo las entidades necesarias
import com.estebanleyton.gestionnomina.model.Empleado;
import com.estebanleyton.gestionnomina.model.Nomina;
import com.estebanleyton.gestionnomina.model.Venta;
import com.estebanleyton.gestionnomina.model.HoraExtra;

// Importo los repositorios utilizados
import com.estebanleyton.gestionnomina.repository.NominaRepository;
import com.estebanleyton.gestionnomina.repository.VentaRepository;
import com.estebanleyton.gestionnomina.repository.HoraExtraRepository;

// Importo anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/*
 * En esta clase realizo la implementación del servicio de nómina.
 * Aquí se concentra toda la lógica de negocio para el cálculo
 * de la nómina mensual de los empleados.
 */
@Service
public class NominaServiceImpl implements NominaService {

    /*
     * Defino los repositorios necesarios como dependencias finales,
     * los cuales son inyectados por Spring.
     */
    private final NominaRepository nominaRepository;
    private final VentaRepository ventaRepository;
    private final HoraExtraRepository horaExtraRepository;

    /*
     * Utilizo inyección de dependencias por constructor
     * para recibir los repositorios de nómina, ventas y horas extras.
     */
    @Autowired
    public NominaServiceImpl(
            NominaRepository nominaRepository,
            VentaRepository ventaRepository,
            HoraExtraRepository horaExtraRepository
    ) {
        this.nominaRepository = nominaRepository;
        this.ventaRepository = ventaRepository;
        this.horaExtraRepository = horaExtraRepository;
    }

    /*
     * Este método se encarga de calcular la nómina de un empleado
     * para el periodo actual (mes en curso).
     */
    @Override
    public Nomina calcularNomina(Empleado empleado) {

        /*
         * Valido que el empleado no sea nulo antes de continuar
         * con el cálculo de la nómina.
         */
        if (empleado == null) {
            throw new IllegalArgumentException("Empleado no puede ser nulo");
        }

        /*
         * Defino el rango de fechas correspondiente al mes actual,
         * desde el primer hasta el último día del mes.
         */
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = LocalDate.now().withDayOfMonth(
                LocalDate.now().lengthOfMonth()
        );

        /*
         * Construyo el periodo en un formato legible,
         * por ejemplo: 01/02/2026 - 28/02/2026.
         */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String periodo = inicio.format(formatter) + " - " + fin.format(formatter);

        /*
         * Obtengo el mes y el año del periodo actual.
         */
        int mes = inicio.getMonthValue();
        int anio = inicio.getYear();

        /*
         * Verifico si ya existe una nómina calculada para este empleado
         * en el mismo mes y año, con el fin de evitar duplicados.
         */
        boolean yaExiste = nominaRepository
                .existsByEmpleadoIdAndMesAndAnio(empleado.getId(), mes, anio);

        if (yaExiste) {
            throw new IllegalStateException(
                    "Ya existe una nómina calculada para este empleado en este mes"
            );
        }

        /*
         * Obtengo todas las ventas realizadas por el empleado
         * dentro del periodo actual.
         */
        List<Venta> ventasMes = ventaRepository
                .findByEmpleadoIdAndFechaBetween(
                        empleado.getId(),
                        inicio,
                        fin
                );

        /*
         * Calculo el total de ventas del mes
         * sumando los subtotales de cada venta.
         */
        double ventas = ventasMes.stream()
                .mapToDouble(Venta::getSubtotal)
                .sum();

        /*
         * Obtengo las horas extras registradas por el empleado
         * dentro del periodo actual.
         */
        List<HoraExtra> horasExtrasMes = horaExtraRepository
                .findByEmpleadoIdAndFechaBetween(
                        empleado.getId(),
                        inicio,
                        fin
                );

        /*
         * Calculo el total de horas extras del mes.
         */
        double horasExtras = horasExtrasMes.stream()
                .mapToDouble(HoraExtra::getHoras)
                .sum();

        /*
         * Obtengo el salario base del empleado.
         * Si no está definido, utilizo cero como valor por defecto.
         */
        double salarioBase = empleado.getSalarioBase() != null
                ? empleado.getSalarioBase()
                : 0;

        /*
         * Calculo el valor de la hora extra,
         * tomando como base 240 horas mensuales y un recargo del 50%.
         */
        double valorHoraExtra = (salarioBase / 240) * 1.5;

        /*
         * Calculo el total a pagar por horas extras.
         */
        double totalHorasExtras = valorHoraExtra * horasExtras;

        /*
         * Calculo la comisión por ventas.
         * Solo se aplica si las ventas superan los 10.000.000.
         */
        double comision = ventas > 10_000_000 ? ventas * 0.02 : 0;

        /*
         * Calculo el auxilio de transporte según el salario base.
         */
        double auxilioTransporte = salarioBase <= 2_600_000 ? 162_000 : 0;

        /*
         * Calculo el total de ingresos del empleado.
         */
        double ingresos = salarioBase
                + totalHorasExtras
                + comision
                + auxilioTransporte;

        /*
         * Calculo las deducciones obligatorias.
         */
        double salud = ingresos * 0.04;
        double pension = ingresos * 0.04;
        double arl = ingresos * 0.00522;

        double deducciones = salud + pension + arl;

        /*
         * Calculo el valor neto a pagar al empleado.
         */
        double neto = ingresos - deducciones;

        /*
         * Construyo el objeto Nómina con todos los valores calculados.
         */
        Nomina nomina = Nomina.builder()
                .empleado(empleado)
                .salarioBase(salarioBase)
                .horasExtras(horasExtras)
                .valorHoraExtra(valorHoraExtra)
                .totalHorasExtras(totalHorasExtras)
                .ventas(ventas)
                .comision(comision)
                .auxilioTransporte(auxilioTransporte)
                .ingresos(ingresos)
                .salud(salud)
                .pension(pension)
                .arl(arl)
                .deducciones(deducciones)
                .neto(neto)
                .periodo(periodo)
                .mes(mes)
                .anio(anio)
                .build();

        /*
         * Guardo la nómina en la base de datos y retorno el resultado.
         */
        return nominaRepository.save(nomina);
    }

    /*
     * Este método me permite buscar una nómina específica
     * por empleado, mes y año.
     */
    @Override
    public Optional<Nomina> buscarNominaPorPeriodo(
            Long empleadoId,
            int mes,
            int anio
    ) {
        return nominaRepository
                .findByEmpleadoIdAndMesAndAnio(empleadoId, mes, anio);
    }

    /*
     * Este método me permite listar todas las nóminas registradas.
     */
    @Override
    public List<Nomina> listarNominas() {
        return nominaRepository.findAll();
    }
}
