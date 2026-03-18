package exceptions;

/**
 * Custom exception for failed login attempts
 */
public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}