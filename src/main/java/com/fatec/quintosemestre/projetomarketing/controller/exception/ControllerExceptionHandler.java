package com.fatec.quintosemestre.projetomarketing.controller.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErrosDeValidacao(MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.put(x.getField(), x.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(errors, "Foram encontrados erros de validação"));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> tratarErrosDeObjetoNaoEncontrado(NoSuchElementException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Object> tratarErrosDeServlet(ServletException ex,
            HttpServletRequest request) {

        HttpStatus status;
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity.status(status)
                .body(new ApiResponse<>(ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> tratarErrosDeViolacaoDeDados(DataIntegrityViolationException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(ex.getMostSpecificCause().getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> tratarErrosDeCorpoDeRequisicao(HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ex.getMostSpecificCause().getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> tratarErrosDeMetodosNaoSuportados(UnsupportedOperationException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new ApiResponse<>(ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> tratarErrosDeCredenciaisInvalidas(BadCredentialsException ex,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(ex.getMessage(), request.getRequestURI()));
    }

}
