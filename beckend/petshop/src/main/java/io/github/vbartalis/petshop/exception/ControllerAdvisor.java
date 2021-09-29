package io.github.vbartalis.petshop.exception;

import io.github.vbartalis.petshop.exception.custom.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * This Class contains global error handling methods.
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    //----Custom Exceptions----

    /**
     * This method handles responses when the FileTypeNotSupportedException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(FileTypeNotSupportedException.class)
    public ResponseEntity<Object> handleFileTypeNotSupportedException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        body.put("error", "File Type Not Supported Exception");
        body.put("message", "The submitted file has an unsupported media type!");
        return new ResponseEntity<>(body, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * This method handles responses when the ImageWriterException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(ImageWriterException.class)
    public ResponseEntity<Object> handleImageWriterException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Image Writer Exception");
        body.put("message", "The submitted image could not be saved!");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method handles responses when the InvalidPasswordException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Invalid Password Exception");
        body.put("message", "The submitted password is not valid!");
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * This method handles responses when the InvalidUsernameException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<Object> handleInvalidUsernameException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Invalid Username Exception");
        body.put("message", "The submitted username is not valid!");
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * This method handles responses when the UsernameAlreadyInUseException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<Object> handleUsernameAlreadyInUseException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Username Already In Use Exception");
        body.put("message", "The submitted username is already in use!");
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //----Default Exceptions----

    /**
     * This method handles responses when the NoSuchElementException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "No Such Element Exception");
        body.put("message", "The requested resource does not exist!");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles responses when the AuthenticationException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Authentication Exception");
        body.put("message", "Invalid credentials!");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * This method handles responses when the IOException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Object> handleIOException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "IOException");
        body.put("message", "Internal server error!");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method handles responses when the AccessDeniedException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException e, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Access Denied Exception");
        body.put("message", "Accessing the requested resource is not authorized!");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * This method handles responses when the MethodArgumentNotValidException is thrown.
     *
     * @return The ResponseEntity to the exception containing the timestamp, status,
     * error and the message to the exception.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        body.put("errors", errors);
        body.put("message", "The submitted resource is not valid!");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
