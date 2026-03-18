package models;

/**
 * Stores encrypted secure notes
 * Demonstrates INHERITANCE
 */
public class SecureNote extends VaultItem {
    private static final long serialVersionUID = 1L;
    
    private String encryptedContent;
    private String category;
    
    public SecureNote(String id, String title, String encryptedContent, String category) {
        super(id, title);
        this.encryptedContent = encryptedContent;
        this.category = category;
    }
    
    @Override
    public void display() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│       SECURE NOTE                   │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ ID       : " + id);
        System.out.println("│ Title    : " + title);
        System.out.println("│ Category : " + category);
        System.out.println("│ Content  : " + encryptedContent);
        System.out.println("│ Created  : " + getCreatedAt());
        System.out.println("└─────────────────────────────────────┘");
    }
    
    @Override
    public String getItemType() {
        return "NOTE";
    }
    
    // Getters
    public String getEncryptedContent() {
        return encryptedContent;
    }
    
    public String getCategory() {
        return category;
    }
}