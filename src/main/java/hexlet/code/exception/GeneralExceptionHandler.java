package hexlet.code.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice //говорит Spring Boot о том что этот класс отвечает за централизованную обработку исключений.
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class) //какое искл обраб этот метод
    public ResponseEntity<String> handler404Exception(ResourceNotFoundException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exc.getMessage());
    }
    @ExceptionHandler(ForbiddenException.class) //какое искл обраб этот метод
    public ResponseEntity<String> handler403Exception(ForbiddenException exc) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exc.getMessage());
    }
    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<String> handler409Exception(ExistsException exc) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.getMessage());
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<String> handlerValidException(ConstraintViolationException exc) {
//        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
//                .body(shortener(exc.getMessage(), "interpolatedMessage"));
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(Exception exc) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exc.getMessage());
    }

    private String shortener(String message, String param) {
        if(message.contains(param)) {
            String paramSplit = message.split(param)[1];
            String shorter = paramSplit.split("'")[1];
            if(message.contains("propertyPath=")) {
                String fieldNameString = message.split("propertyPath=")[1];
                String fieldVNameValue = fieldNameString.split(",")[0];
                return new String("Поле " + fieldVNameValue + " " + shorter);
            }
            return shorter;
        }
        return message;
    }
}
