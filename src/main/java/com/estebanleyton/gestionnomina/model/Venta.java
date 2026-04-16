package com.estebanleyton.gestionnomina.model;

// Importo las anotaciones necesarias para el mapeo con JPA
import jakarta.persistence.*;

// Importo Lombok para reducir código repetitivo
import lombok.*;

import java.time.LocalDate;

/*
 * Esta clase representa la entidad Venta dentro del sistema.
 * La utilizo para registrar las ventas realizadas por los empleados,
 * las cuales se usan posteriormente para el cálculo de comisiones.
 */
@Entity
@Table(name = "ventas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    /*
     * Este atributo corresponde a la clave primaria de la tabla ventas.
     * Se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Defino la relación muchos a uno con la entidad Empleado.
     * Cada venta está asociada a un empleado específico.
     */
    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    /*
     * Almaceno la fecha en la que se realizó la venta.
     */
    @Column(nullable = false)
    private LocalDate fecha;

    /*
     * Almaceno el nombre o descripción del producto vendido.
     * Limito la longitud a 50 caracteres.
     */
    @Column(nullable = false, length = 50)
    private String producto;

    /*
     * Almaceno la cantidad de unidades vendidas del producto.
     */
    @Column(nullable = false)
    private Integer unidades;

    /*
     * Almaceno el valor unitario del producto vendido.
     */
    @Column(name = "valor_unidad", nullable = false)
    private Double valorUnidad;

    /*
     * Almaceno el valor subtotal de la venta,
     * que corresponde a unidades multiplicadas por el valor unitario.
     */
    @Column(nullable = false)
    private Double subtotal;
}
