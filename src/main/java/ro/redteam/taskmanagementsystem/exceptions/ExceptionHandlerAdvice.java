package ro.redteam.taskmanagementsystem.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> emailExistException(EmailExistsException emailExistException) {
        return new ResponseEntity<>(objectToString(Map.of("message", emailExistException.getMessage())), CONFLICT);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> emptyInputException(EmptyInputException emptyInputException) {
        return new ResponseEntity<>(objectToString(Map.of("message", emptyInputException.getMessage())), BAD_REQUEST);
    }

    private String objectToString(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException exception) {
            log.error("Error processing response to string");
            return "Internal error";
        }
    }
}