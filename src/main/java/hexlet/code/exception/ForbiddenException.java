package hexlet.code.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForbiddenException extends RuntimeException {
    Logger log = LoggerFactory.getLogger("FORBIDDEN 403: ");
    public ForbiddenException(String message) {
        super(message);
        log.info(message);
    }
}
