package com.estebanleyton.gestionnomina.model;

// Importo las anotaciones necesarias para el mapeo con JPA
import jakarta.persistence.*;

// Importo Lombok para generar automáticamente getters, setters y constructores
import lombok.*;

import java.time.LocalDate;

/*
 * Esta clase representa la entidad HoraExtra dentro del sistema.
 * La utilizo para registrar y controlar las horas extras realizadas
 * por cada empleado.
 */
@Entity
@Table(name = "horas_extras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoraExtra {

    /*
     * Este atributo corresponde a la clave primaria de la tabla horas_extras.
     * Su valor se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Defino la relación muchos a uno con la entidad Empleado.
     * Cada registro de hora extra pertenece a un solo empleado.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    /*
     * Almaceno la fecha en la que se realizaron las horas extras.
     */
    private LocalDate fecha;

    /*
     * Almaceno la hora de inicio de las horas extras.
     */
    private String horaInicio;

    /*
     * Almaceno la hora de finalización de las horas extras.
     */
    private String horaFin;

    /*
     * Almaceno el total de horas extras calculadas.
     */
    private double horas;

    /*
     * Almaceno el motivo por el cual se realizaron las horas extras.
     */
    private String motivo;

    /*
     * Almaceno el nombre del responsable que autorizó las horas extras.
     */
    private String autorizadoPor;
}
