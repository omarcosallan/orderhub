package com.omarcosallan.orderhub.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequestException(BadRequestException e) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Requisição inválida.", e.getMessage(), null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFoundException(ResourceNotFoundException e) {
        return createProblemDetail(HttpStatus.NOT_FOUND, "Recurso não encontrado.", e.getMessage(), null);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleAlreadyExistsException(AlreadyExistsException e) {
        return createProblemDetail(HttpStatus.CONFLICT, "Recurso já existe.", e.getMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return createProblemDetail(HttpStatus.BAD_REQUEST, "Dado informado já existe.", "Verifique se já existe um registro semelhante ou se todos os campos obrigatórios foram preenchidos corretamente.", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return createProblemDetail(HttpStatus.BAD_REQUEST, "Dados inválidos.", "Um ou mais campos estão preenchidos incorretamente. Verifique os dados informados e tente novamente.", Map.of("errors", errors));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(BadCredentialsException e) {
        return createProblemDetail(HttpStatus.UNAUTHORIZED, "Credenciais inválidas.", "E-mail ou senha incorretos. Verifique seus dados e tente novamente.", null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception e) {
        return createProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno.",
                "Não foi possível processar a sua requisição. Por favor, entre em contato com o suporte.",
                null
        );
    }

    private ResponseEntity<ProblemDetail> createProblemDetail(HttpStatus status, String title, String detail, Map<String, Object> properties) {
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle(title);
        pd.setDetail(detail);
        pd.setProperty("timestamp", LocalDateTime.now());
        if (properties != null) {
            pd.setProperties(properties);
        }
        return ResponseEntity.status(pd.getStatus()).body(pd);
    }
}
