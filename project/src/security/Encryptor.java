package security;

/**
 * Interface for encryption logic
 * Demonstrates ABSTRACTION
 */
public interface Encryptor {
    String encrypt(String plainText, String key) throws Exception;
    String decrypt(String encryptedText, String key) throws Exception;
}