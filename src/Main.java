import java.io.IOException;

/**
 * Main entry point for the Environmental Impact Calculator application.
 * 
 * <p>This class demonstrates how to use the calculator to analyze
 * a food image and display its environmental impact.</p>
 * 
 * <p><b>Usage:</b></p>
 * <pre>
 * javac -d bin *.java
 * java -cp bin Main
 * </pre>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public class Main {
    
    /**
     * Main method - entry point of the application.
     * 
     * <p>This method:</p>
     * <ol>
     *   <li>Creates an EnvironmentalImpactCalculator</li>
     *   <li>Loads the food database from CSV</li>
     *   <li>Analyzes a test food image</li>
     *   <li>Displays the meal with environmental calculations</li>
     * </ol>
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create the calculator instance
        EnvironmentalImpactCalculator calculator = new EnvironmentalImpactCalculator();
        
        try {
            // Load the food database
            System.out.println("Loading food database from CSV...");
            calculator.loadFromFile("Environmental impacts of food (Clark et al. 2022) copy.csv");
            System.out.println("Database loaded successfully!\n");
            
            // Analyze a food image and create a meal
            Meal meal = calculator.createMealFromImage("test-image.png");
            
            // Display the meal with environmental calculations
            System.out.println(meal);
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
