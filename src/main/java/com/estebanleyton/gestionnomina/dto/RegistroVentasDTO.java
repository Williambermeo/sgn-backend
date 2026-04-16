package com.estebanleyton.gestionnomina.dto;

// Importo Lombok para generar automáticamente getters, setters y otros métodos
import lombok.Data;

// Importo la clase List para manejar colecciones de ventas
import java.util.List;

// Anoto la clase con @Data para evitar escribir código repetitivo
@Data
public class RegistroVentasDTO {

    /*
     * Este atributo almacena el identificador del empleado
     * al que se le van a asociar las ventas registradas.
     */
    private Long empleadoId;

    /*
     * Esta lista contiene las ventas que se van a registrar.
     * Cada elemento corresponde a un objeto VentaDTO recibido
     * desde el frontend.
     */
    private List<VentaDTO> ventas;
}
