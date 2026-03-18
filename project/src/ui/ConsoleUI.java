package ui;

import vault.VaultManager;
import exceptions.UnauthorizedAccessException;
import java.util.Scanner;

/**
 * Console-based user interface
 */
public class ConsoleUI {
    
    private VaultManager vaultManager;
    private Scanner scanner;
    
    public ConsoleUI() {
        this.vaultManager = new VaultManager();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        printWelcomeBanner();
        
        while (true) {
            if (!vaultManager.isLoggedIn()) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }
    
    private void printWelcomeBanner() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║                                          ║");
        System.out.println("║     🔐 SECURE DIGITAL VAULT SYSTEM 🔐    ║");
        System.out.println("║                                          ║");
        System.out. println("║      AES-256 Encrypted Storage           ║");
        System.out.println("║      SHA-256 Authentication              ║");
        System.out.println("║                                          ║");
        System.out. println("╚══════════════════════════════════════════╝\n");
    }
    
    private void showAuthMenu() {
        System.out.println("\n┌──────────────────────┐");
        System.out. println("│   AUTHENTICATION     │");
        System.out. println("├──────────────────────┤");
        System.out.println("│ 1. Login             │");
        System.out. println("│ 2. Register          │");
        System.out.println("│ 3. Exit              │");
        System.out.println("└──────────────────────┘");
        System.out.print("\nChoice: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleRegister();
                break;
            case "3":
                System. out.println("\n👋 Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("✗ Invalid choice");
        }
    }
    
    private void handleLogin() {
        System.out.print("\nUsername: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        
        try {
            vaultManager.login(username, password);
            System.out.println("\n✓ Login successful!  Welcome, " + username);
        } catch (UnauthorizedAccessException e) {
            System.out.println("\n✗ " + e.getMessage());
        }
    }
    
    private void handleRegister() {
        System.out.print("\nNew Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("New Password (min 8 characters): ");
        String password = scanner.nextLine().trim();
        
        if (password.length() < 8) {
            System.out. println("✗ Password must be at least 8 characters");
            return;
        }
        
        System.out.print("Confirm Password: ");
        String confirm = scanner.nextLine().trim();
        
        if (! password.equals(confirm)) {
            System.out.println("✗ Passwords do not match");
            return;
        }
        
        try {
            vaultManager.registerUser(username, password);
        } catch (Exception e) {
            System.out.println("✗ Registration failed: " + e.getMessage());
        }
    }
    
    private void showMainMenu() {
        System.out.println("\n┌────────────────────────────────┐");
        System.out. println("│         VAULT MENU             │");
        System.out. println("├────────────────────────────────┤");
        System.out.println("│ 1. Add Password                │");
        System.out.println("│ 2. Add Secure Note             │");
        System.out.println("│ 3. View All Items              │");
        System.out.println("│ 4.  Decrypt Password            │");
        System.out. println("│ 5. Decrypt Note                │");
        System.out.println("│ 6. Delete Item                 │");
        System.out. println("│ 7. Logout                      │");
        System.out. println("└────────────────────────────────┘");
        System.out.print("\nChoice: ");
        
        String choice = scanner. nextLine().trim();
        
        switch (choice) {
            case "1":
                handleAddPassword();
                break;
            case "2":
                handleAddNote();
                break;
            case "3":
                vaultManager.displayAllItems();
                break;
            case "4": 
                handleViewPassword();
                break;
            case "5":
                handleViewNote();
                break;
            case "6":
                handleDeleteItem();
                break;
            case "7":
                vaultManager.logout();
                System.out.println("\n✓ Logged out successfully");
                break;
            default: 
                System.out.println("✗ Invalid choice");
        }
    }
    
    private void handleAddPassword() {
        System.out.print("\nTitle: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Website: ");
        String website = scanner.nextLine().trim();
        
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out. print("Password: ");
        String password = scanner.nextLine().trim();
        
        vaultManager.addPasswordItem(title, website, username, password);
    }
    
    private void handleAddNote() {
        System.out.print("\nTitle: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        
        System.out.print("Content: ");
        String content = scanner.nextLine().trim();
        
        vaultManager.addSecureNote(title, content, category);
    }
    
    private void handleViewPassword() {
        System.out.print("\nEnter Password Item ID: ");
        String id = scanner.nextLine().trim();
        vaultManager.viewPassword(id);
    }
    
    private void handleViewNote() {
        System.out.print("\nEnter Note ID: ");
        String id = scanner.nextLine().trim();
        vaultManager.viewNote(id);
    }
    
    private void handleDeleteItem() {
        System.out. print("\nEnter Item ID to delete: ");
        String id = scanner.nextLine().trim();
        
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();
        
        if (confirm. equalsIgnoreCase("yes")) {
            vaultManager.deleteItem(id);
        }
    }
}