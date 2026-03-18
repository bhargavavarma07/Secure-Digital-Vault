package persistence;

import exceptions.InvalidFileFormatException;
import java.io.*;
import java.nio.file.*;

/**
 * Handles all file I/O operations
 * Implements data persistence
 */
public class FileManager {
    
    private static final String DATA_DIR = "data";
    
    static {
        // Create data directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Failed to create data directory: " + e.getMessage());
        }
    }
    
    /**
     * Saves an object to a file using serialization
     */
    public static void saveObject(Object obj, String filename) throws IOException {
        String filepath = DATA_DIR + File.separator + filename;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filepath))) {
            oos.writeObject(obj);
        }
    }
    
    /**
     * Loads an object from a file
     */
    public static Object loadObject(String filename) throws IOException, InvalidFileFormatException {
        String filepath = DATA_DIR + File. separator + filename;
        File file = new File(filepath);
        
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filepath))) {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new InvalidFileFormatException("Invalid file format:  " + filename);
        }
    }
    
    /**
     * Checks if a file exists
     */
    public static boolean fileExists(String filename) {
        String filepath = DATA_DIR + File. separator + filename;
        return Files.exists(Paths.get(filepath));
    }
    
    /**
     * Deletes a file
     */
    public static boolean deleteFile(String filename) {
        try {
            String filepath = DATA_DIR + File. separator + filename;
            return Files.deleteIfExists(Paths.get(filepath));
        } catch (IOException e) {
            return false;
        }
    }
}