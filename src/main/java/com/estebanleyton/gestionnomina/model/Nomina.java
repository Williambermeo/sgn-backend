package com.estebanleyton.gestionnomina.model;

// Importo las anotaciones necesarias para el mapeo con JPA
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

// Importo Lombok para reducir código repetitivo
import lombok.*;

/*
 * Esta clase representa la entidad Nómina dentro del sistema.
 * La utilizo para almacenar toda la información relacionada
 * al cálculo de la nómina mensual de cada empleado.
 */
@Entity
@Table(name = "nominas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nomina {

    /*
     * Este atributo corresponde a la clave primaria de la tabla nominas.
     * Se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Defino la relación muchos a uno con la entidad Empleado.
     * Cada nómina pertenece a un solo empleado.
     * Uso JsonIgnoreProperties para evitar ciclos infinitos al serializar.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    @JsonIgnoreProperties({"nominas"})
    private Empleado empleado;

    /*
     * Indico el mes al que corresponde la nómina.
     */
    @Column(nullable = false)
    private int mes;

    /*
     * Indico el año al que corresponde la nómina.
     */
    @Column(nullable = false)
    private int anio;

    /*
     * =========================
     * INGRESOS
     * =========================
     */

    /*
     * Almaceno el salario base del empleado.
     */
    private Double salarioBase;

    /*
     * Almaceno la cantidad de horas extras realizadas.
     */
    private Double horasExtras;

    /*
     * Almaceno el valor unitario de la hora extra.
     */
    private Double valorHoraExtra;

    /*
     * Almaceno el total pagado por concepto de horas extras.
     */
    private Double totalHorasExtras;

    /*
     * Almaceno el valor total de las ventas realizadas.
     */
    private Double ventas;

    /*
     * Almaceno el valor de la comisión calculada sobre las ventas.
     */
    private Double comision;

    /*
     * Almaceno el valor del auxilio de transporte, si aplica.
     */
    private Double auxilioTransporte;

    /*
     * Almaceno el total de ingresos del empleado en el periodo.
     */
    private Double ingresos;

    /*
     * =========================
     * DEDUCCIONES
     * =========================
     */

    /*
     * Almaceno el valor correspondiente al descuento por salud.
     */
    private Double salud;

    /*
     * Almaceno el valor correspondiente al descuento por pensión.
     */
    private Double pension;

    /*
     * Almaceno el valor correspondiente al aporte ARL.
     */
    private Double arl;

    /*
     * Almaceno el total de deducciones aplicadas.
     */
    private Double deducciones;

    /*
     * =========================
     * RESULTADO FINAL
     * =========================
     */

    /*
     * Almaceno el valor neto a pagar al empleado.
     */
    private Double neto;

    /*
     * Almaceno el periodo de la nómina en formato legible
     * (por ejemplo: "Enero 2026").
     */
    private String periodo;
}
