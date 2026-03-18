package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all vault items
 * Demonstrates ABSTRACTION and INHERITANCE
 */
public abstract class VaultItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String title;
    protected LocalDateTime createdAt;
    protected LocalDateTime lastModified;
    
    public VaultItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }
    
    // Abstract method - forces subclasses to implement
    public abstract void display();
    
    public abstract String getItemType();
    
    // Common getters
    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter. ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }
    
    protected void updateModifiedTime() {
        this.lastModified = LocalDateTime. now();
    }
}