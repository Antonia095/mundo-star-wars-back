package br.com.projeto.mundo_star_wars.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

    log.info("Erro de validação: {}", ex.getMessage());

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.status(BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handleBusiness(BusinessException ex) {

    log.error("Erro de negócio: {}", ex.getMessage(), ex);

    return ResponseEntity.status(BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {

    log.error("Entidade não encontrada: {}", ex.getMessage(), ex);

    return ResponseEntity.status(NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneric(Exception ex) {

    if (ex instanceof org.springframework.security.authorization.AuthorizationDeniedException) {
      log.warn("AuthorizationDeniedException interceptada no GlobalExceptionHandler: {}", ex.getMessage());
      return ResponseEntity.status(FORBIDDEN)
          .body("Acesso negado - Apenas ROLE_ADMIN");
    }

    log.error("Erro inesperado: {}", ex.getMessage(), ex);

    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body("Erro interno do servidor. Tente novamente mais tarde.");
  }
}

