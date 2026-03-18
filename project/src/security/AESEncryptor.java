package security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES encryption implementation
 * Implements the Encryptor interface
 */
public class AESEncryptor implements Encryptor {
    
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    
    @Override
    public String encrypt(String plainText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = generateKey(key);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    @Override
    public String decrypt(String encryptedText, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = generateKey(key);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
    
    private SecretKey generateKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        // Pad or truncate key to 16 bytes (128 bits) for AES
        byte[] fixedKey = new byte[16];
        System.arraycopy(keyBytes, 0, fixedKey, 0, Math.min(keyBytes.length, 16));
        return new SecretKeySpec(fixedKey, 0, fixedKey.length, ALGORITHM);
    }
}
