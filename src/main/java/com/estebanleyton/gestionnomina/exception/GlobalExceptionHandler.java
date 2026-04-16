package com.estebanleyton.gestionnomina.exception;

// Importo las clases necesarias para manejar respuestas HTTP
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Importo las clases usadas para manejar errores de validación
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

// Importo las anotaciones para manejar excepciones de forma global
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * En esta clase centralizo el manejo global de excepciones del sistema.
 * Principalmente la utilizo para capturar errores de validación y
 * devolver respuestas claras en formato JSON al frontend.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Este método se encarga de manejar las excepciones que se generan
     * cuando fallan las validaciones definidas con @Valid.
     *
     * Recorro los errores de validación y construyo un mapa donde
     * cada campo se asocia con su respectivo mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // Creo un mapa para almacenar los errores de validación
        Map<String, String> errors = new HashMap<>();

        /*
         * Recorro todos los errores detectados durante la validación
         * y extraigo el nombre del campo y su mensaje de error.
         */
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        /*
         * Devuelvo el mapa de errores junto con el estado HTTP 400 (Bad Request),
         * indicando que la información enviada no cumple con las validaciones.
         */
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Este método se encarga de manejar las excepciones personalizadas
     * de tipo BadRequestException que utilizo para validar reglas de negocio.
     *
     * Devuelvo un mensaje claro en formato JSON junto con el estado 400.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
            BadRequestException ex) {

        // Creo un mapa para enviar el mensaje de error
        Map<String, String> error = new HashMap<>();

        // Agrego el mensaje personalizado de la excepción
        error.put("error", ex.getMessage());

        /*
         * Devuelvo el mensaje junto con el estado HTTP 400 (Bad Request),
         * indicando que la solicitud no cumple con las reglas de negocio.
         */
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /*
     * En el futuro puedo agregar más métodos @ExceptionHandler
     * para manejar otros tipos de excepciones del sistema.
     */
}