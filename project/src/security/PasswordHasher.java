package security;

import java.security. MessageDigest;
import java. security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util. Base64;

/**
 * Handles SHA-256 password hashing with salt
 * Key Security Principle: Never store passwords in plain text
 */
public class PasswordHasher {
    
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    
    /**
     * Generates a random salt for password hashing
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Hashes a password with the given salt using SHA-256
     * @param password The plain text password
     * @param salt The salt to use
     * @return Base64 encoded hash
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            
            // Combine password and salt
            String saltedPassword = password + salt;
            
            // Hash the combination
            byte[] hashedBytes = md.digest(saltedPassword.getBytes());
            
            // Convert to Base64 for storage
            return Base64.getEncoder().encodeToString(hashedBytes);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Verifies if a password matches the stored hash
     */
    public static boolean verifyPassword(String inputPassword, String storedHash, String salt) {
        String inputHash = hashPassword(inputPassword, salt);
        return inputHash. equals(storedHash);
    }
}