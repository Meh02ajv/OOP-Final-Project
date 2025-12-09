import java.io.IOException;

/**
 * Interface for classes that can load data from external files.
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public interface FileOperations {
    
    /**
     * Loads data from a specified file.
     * 
     * @param filename The path to the file to load data from
     * @throws IOException If an I/O error occurs during reading
     */
    void loadFromFile(String filename) throws IOException;
}
