package hexlet.code.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public ResourceNotFoundException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
