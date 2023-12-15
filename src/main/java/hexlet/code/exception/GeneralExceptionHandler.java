package hexlet.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<String> handlerDefaultException(DefaultException exc) {
//        System.out.println("cause: " + exc.getCause()); //null
//        System.out.println("localized msg " + exc.getLocalizedMessage()); // =exc.getMsg()
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(exc.getMessage());
    }
}
