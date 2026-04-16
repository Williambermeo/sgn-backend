package com.estebanleyton.gestionnomina.dto;

// Importo Lombok para generar automáticamente los métodos getters y setters
import lombok.Data;

// Importo LocalDate para manejar la fecha de la venta
import java.time.LocalDate;

// Uso @Data para evitar escribir código repetitivo
@Data
public class VentaDTO {

    /*
     * Este atributo almacena la fecha en la que se realizó la venta.
     */
    private LocalDate fecha;

    /*
     * Este atributo representa el nombre o descripción del producto vendido.
     */
    private String producto;

    /*
     * Este atributo indica la cantidad de unidades vendidas del producto.
     */
    private Integer unidades;

    /*
     * Este atributo almacena el valor unitario del producto vendido.
     */
    private Double valorUnidad;
}
