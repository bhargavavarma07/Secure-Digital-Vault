package vault;

import models.*;
import security.*;
import persistence.FileManager;
import exceptions.*;

import java.io.IOException;
import java.util.*;

/**
 * Manages the vault operations
 * Central business logic class
 */
public class VaultManager {
    
    private static final String VAULT_FILE = "vault.dat";
    private static final String USERS_FILE = "users.dat";
    
    private Map<String, User> users;
    private List<VaultItem> vaultItems;
    private User currentUser;
    private String encryptionKey;
    private Encryptor encryptor;
    
    public VaultManager() {
        this.users = new HashMap<>();
        this.vaultItems = new ArrayList<>();
        this.encryptor = new AESEncryptor();
        loadUsers();
        loadVault();
    }
    
    /**
     * Registers a new user
     */
    public void registerUser(String username, String password) throws Exception {
        if (users.containsKey(username)) {
            throw new Exception("Username already exists");
        }
        
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hashPassword(password, salt);
        
        User newUser = new User(username, hash, salt);
        users.put(username, newUser);
        saveUsers();
        
        System.out.println("✓ User registered successfully!");
    }
    
    /**
     * Authenticates a user
     */
    public boolean login(String username, String password) throws UnauthorizedAccessException {
        User user = users.get(username);
        
        if (user == null) {
            throw new UnauthorizedAccessException("User not found");
        }
        
        if (!PasswordHasher.verifyPassword(password, user.getPasswordHash(), user.getSalt())) {
            throw new UnauthorizedAccessException("Invalid password");
        }
        
        this.currentUser = user;
        
        // Generate encryption key from password
        try {
            this.encryptionKey = generateEncryptionKey(password, user.getSalt());
        } catch (Exception e) {
            throw new UnauthorizedAccessException("Failed to generate encryption key");
        }
        
        return true;
    }
    
    /**
     * Generates a consistent encryption key from password
     */
    private String generateEncryptionKey(String password, String salt) throws Exception {
        // Use password hash as encryption key (simplified for demo)
        String combined = PasswordHasher.hashPassword(password, salt);
        // Ensure key is proper length for AES-256 (32 bytes = 256 bits)
        byte[] keyBytes = Arrays.copyOf(combined.getBytes(), 32);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
    
    /**
     * Adds a password entry to the vault
     */
    public void addPasswordItem(String title, String website, String username, String password) {
        try {
            String encryptedPassword = encryptor. encrypt(password, encryptionKey);
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            PasswordItem item = new PasswordItem(id, title, website, username, encryptedPassword);
            vaultItems.add(item);
            saveVault();
            
            System.out.println("✓ Password added successfully!  ID: " + id);
        } catch (Exception e) {
            System.err.println("✗ Failed to add password: " + e.getMessage());
        }
    }
    
    /**
     * Adds a secure note to the vault
     */
    public void addSecureNote(String title, String content, String category) {
        try {
            String encryptedContent = encryptor.encrypt(content, encryptionKey);
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            SecureNote note = new SecureNote(id, title, encryptedContent, category);
            vaultItems.add(note);
            saveVault();
            
            System.out. println("✓ Note added successfully! ID: " + id);
        } catch (Exception e) {
            System.err. println("✗ Failed to add note: " + e.getMessage());
        }
    }
    
    /**
     * Displays all vault items (POLYMORPHISM in action!)
     */
    public void displayAllItems() {
        if (vaultItems.isEmpty()) {
            System.out.println("\n📭 Your vault is empty.");
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out. println("║         YOUR VAULT ITEMS              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        for (VaultItem item : vaultItems) {
            item.display(); // Polymorphism - calls the correct display() method
        }
    }
    
    /**
     * Decrypts and shows a password
     */
    public void viewPassword(String id) {
        Optional<VaultItem> itemOpt = vaultItems.stream()
            .filter(item -> item.getId().equals(id) && item instanceof PasswordItem)
            .findFirst();
        
        if (itemOpt.isPresent()) {
            PasswordItem pwdItem = (PasswordItem) itemOpt.get();
            try {
                String decryptedPassword = encryptor.decrypt(pwdItem.getEncryptedPassword(), encryptionKey);
                System.out.println("\n🔓 Decrypted Password: " + decryptedPassword);
            } catch (Exception e) {
                System.err.println("✗ Failed to decrypt password");
            }
        } else {
            System.out.println("✗ Password item not found");
        }
    }
    
    /**
     * Decrypts and shows a note
     */
    public void viewNote(String id) {
        Optional<VaultItem> itemOpt = vaultItems.stream()
            .filter(item -> item.getId().equals(id) && item instanceof SecureNote)
            .findFirst();
        
        if (itemOpt.isPresent()) {
            SecureNote note = (SecureNote) itemOpt.get();
            try {
                String decryptedContent = encryptor.decrypt(note.getEncryptedContent(), encryptionKey);
                System. out.println("\n🔓 Decrypted Note: " + decryptedContent);
            } catch (Exception e) {
                System.err.println("✗ Failed to decrypt note");
            }
        } else {
            System.out.println("✗ Note not found");
        }
    }
    
    /**
     * Deletes an item from the vault
     */
    public void deleteItem(String id) {
        boolean removed = vaultItems.removeIf(item -> item.getId().equals(id));
        if (removed) {
            saveVault();
            System.out. println("✓ Item deleted successfully");
        } else {
            System.out.println("✗ Item not found");
        }
    }
    
    /**
     * Saves users to file
     */
    private void saveUsers() {
        try {
            FileManager.saveObject(users, USERS_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save users: " + e. getMessage());
        }
    }
    
    /**
     * Loads users from file
     */
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try {
            Object obj = FileManager.loadObject(USERS_FILE);
            if (obj != null) {
                users = (Map<String, User>) obj;
            }
        } catch (Exception e) {
            System.err.println("Failed to load users: " + e.getMessage());
        }
    }
    
    /**
     * Saves vault to file
     */
    private void saveVault() {
        try {
            FileManager.saveObject(vaultItems, VAULT_FILE);
        } catch (IOException e) {
            System.err.println("Failed to save vault: " + e.getMessage());
        }
    }
    
    /**
     * Loads vault from file
     */
    @SuppressWarnings("unchecked")
    private void loadVault() {
        try {
            Object obj = FileManager.loadObject(VAULT_FILE);
            if (obj != null) {
                vaultItems = (List<VaultItem>) obj;
            }
        } catch (Exception e) {
            System.err. println("Failed to load vault: " + e.getMessage());
        }
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public String getCurrentUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }
    
    public void logout() {
        currentUser = null;
        encryptionKey = null;
    }
}