package exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
