package com.estebanleyton.gestionnomina.model;

// Importo las anotaciones necesarias para el mapeo con JPA y validaciones
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// Importo Lombok para reducir código repetitivo
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Esta clase representa la entidad Empleado dentro del sistema.
 * La utilizo para mapear la tabla "empleados" en la base de datos
 * y almacenar la información principal de cada trabajador.
 */
@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    /*
     * Este atributo corresponde a la clave primaria de la tabla empleados.
     * Se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Almaceno el primer nombre del empleado.
     * Este campo es obligatorio.
     */
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    /*
     * Almaceno el segundo nombre del empleado.
     * Este campo es opcional.
     */
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    /*
     * Almaceno el primer apellido del empleado.
     * Este campo es obligatorio.
     */
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    /*
     * Almaceno el segundo apellido del empleado.
     * Este campo es opcional.
     */
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    /*
     * Indico el tipo de identificación del empleado
     * (por ejemplo cédula, pasaporte, etc.).
     */
    @Column(name = "tipo_identificacion", nullable = false)
    private String tipoIdentificacion;

    /*
     * Almaceno el número de identificación del empleado.
     * Este valor debe ser único en el sistema.
     */
    @Column(name = "identificacion", nullable = false, unique = true)
    private String identificacion;

    /*
     * Almaceno la dirección de residencia del empleado.
     */
    private String direccion;

    /*
     * Almaceno el número de teléfono del empleado.
     */
    private String telefono;

    /*
     * Indico el cargo que ocupa el empleado dentro de la empresa.
     */
    private String cargo;

    /*
     * Indico la dependencia o área a la que pertenece el empleado.
     */
    private String dependencia;

    /*
     * Registro la fecha y hora en la que se creó el empleado en el sistema.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    /*
     * Almaceno el usuario que realizó la creación del registro.
     */
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;

    /*
     * Almaceno el salario base del empleado.
     * Este campo es obligatorio para los cálculos de nómina.
     */
    @Column(name = "salario_base", nullable = false)
    private Double salarioBase;

    /*
     * Almaceno la cantidad de horas extras realizadas por el empleado.
     */
    @Column(name = "horas_extras")
    private Integer horasExtras;

    /*
     * Almaceno el valor total de las ventas realizadas por el empleado.
     * Este campo se utiliza para el cálculo de comisiones.
     */
    @Column(name = "ventas_realizadas")
    private Double ventasRealizadas;

    /*
     * Defino la relación uno a muchos con la entidad Nómina.
     * Uso @JsonIgnore para evitar ciclos infinitos al serializar a JSON.
     */
    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Nomina> nominas;
}
