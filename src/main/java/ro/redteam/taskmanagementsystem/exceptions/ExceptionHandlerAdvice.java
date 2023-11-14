package ro.redteam.taskmanagementsystem.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<String> dataExistException(DataExistsException dataExistException) {
        return new ResponseEntity<>(objectToString(Map.of("message", dataExistException.getMessage())), CONFLICT);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> emptyInputException(EmptyInputException emptyInputException) {
        return new ResponseEntity<>(objectToString(Map.of("message", emptyInputException.getMessage())), BAD_REQUEST);
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> dataNotFoundException(DataNotFoundException dataNotFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", dataNotFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(InvalidUserIdOrTaskIdException.class)
    public ResponseEntity<String> invalidUserIdOrTaskIdException(InvalidUserIdOrTaskIdException invalidUserIdOrTaskIdException) {
        return new ResponseEntity<>(objectToString(Map.of("message", invalidUserIdOrTaskIdException.getMessage())), BAD_REQUEST);

    }

    @ExceptionHandler(NoTaskFoundException.class)
    public ResponseEntity<String> noTaskFoundException(NoTaskFoundException noTaskFoundException) {
        return new ResponseEntity<>(objectToString(Map.of("message", noTaskFoundException.getMessage())), NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> invalidDataException(InvalidDataException invalidDataException) {
        return new ResponseEntity<>(objectToString(Map.of("message", invalidDataException.getMessage())), BAD_REQUEST);
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