package com.example.student.exception;

import com.example.student.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ContextAuthentication.class)
    public ResponseEntity<ErrorResponseDto> handleContextAuthentication(ContextAuthentication c){
        log.info("email not found for authorization :{}", c.getMessage());
        return new ResponseEntity<>(
                new ErrorResponseDto(
                        c.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()
                ),
                HttpStatus.BAD_REQUEST);
    }

//    SQL ERRORS
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolation(
        DataIntegrityViolationException ex) {

    log.error("Database constraint violation: {}", ex.getMostSpecificCause().getMessage());

    return ResponseEntity.badRequest().body(
            new ErrorResponseDto(
                    "Database constraint violated",
                    HttpStatus.BAD_REQUEST.value(),
                    LocalDateTime.now()
            )
    );
}

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleHibernateConstraintViolation(
            org.hibernate.exception.ConstraintViolationException ex) {

        log.error("Hibernate constraint violation: {}", ex.getMessage());

        return ResponseEntity.badRequest().body(
                new ErrorResponseDto(
                        "Invalid database operation",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorResponseDto> handleUserRegistraionException(UserRegistrationException ex){
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUser(UserAlreadyExists ex){
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedAccess.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedAccess(UnauthorizedAccess ua){
    log.info("unauthorized access : {}", ua.getMessage());
        return new ResponseEntity<>(
                new ErrorResponseDto(ua.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorResponseDto> handleStudentException(StudentException ex) {
        log.info("student not found with id : {}",ex.getMessage() );
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(GenericException g) {
        return new ResponseEntity<>(
                new ErrorResponseDto(g.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(CreateStudentException.class)
    public ResponseEntity<ErrorResponseDto> handleCreateStudentException(CreateStudentException ex){

        log.warn("create student excpetion : {}", ex.getMessage());
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UsernameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler({
            io.jsonwebtoken.JwtException.class
    })
    public ResponseEntity<?> handleJwtException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid or expired token"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        log.error("Unhandled exception occurred", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Something went wrong"));
    }
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ErrorResponseDto> handleJpaSystemException(
            JpaSystemException ex) {

        log.error("JPA system error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDto(
                        "Database error occurred",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now()
                )
        );
    }
}
