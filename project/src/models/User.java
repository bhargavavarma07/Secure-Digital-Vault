package models;

import java.io.Serializable;

/**
 * Represents a user with encrypted credentials
 * Demonstrates ENCAPSULATION - password hash is private
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String passwordHash; // Never store plain text passwords
    private String salt; // Random salt for additional security
    
    public User(String username, String passwordHash, String salt) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }
    
    // Getters - NO setter for passwordHash (security by design)
    public String getUsername() {
        return username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public String getSalt() {
        return salt;
    }
    
    @Override
    public String toString() {
        return "User:  " + username;
    }
}