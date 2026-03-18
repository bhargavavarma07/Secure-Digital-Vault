package exceptions;

/**
 * Thrown when data file is corrupted or invalid
 */
public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}