package models;

/**
 * Stores encrypted password credentials
 * Demonstrates INHERITANCE
 */
public class PasswordItem extends VaultItem {
    private static final long serialVersionUID = 1L;
    
    private String website;
    private String username;
    private String encryptedPassword;
    
    public PasswordItem(String id, String title, String website, String username, String encryptedPassword) {
        super(id, title);
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }
    
    @Override
    public void display() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│       PASSWORD ENTRY                │");
        System.out.println("├──────────���──────────────────────────┤");
        System.out.println("│ ID       : " + id);
        System.out.println("│ Title    : " + title);
        System.out. println("│ Website  : " + website);
        System.out.println("│ Username : " + username);
        System.out.println("│ Password : " + encryptedPassword);
        System.out.println("│ Created  : " + getCreatedAt());
        System.out.println("└─────────────────────────────────────┘");
    }
    
    @Override
    public String getItemType() {
        return "PASSWORD";
    }
    
    // Getters
    public String getWebsite() {
        return website;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}