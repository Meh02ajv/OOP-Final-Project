import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/**
 * The central class for calculating environmental impacts of meals.
 * 
 * <p>This class serves as the main coordinator between:</p>
 * <ul>
 *   <li>The food database (loaded from CSV)</li>
 *   <li>The image analysis service (Gemini AI)</li>
 *   <li>The meal calculation logic</li>
 * </ul>
 * 
 * <p><b>Typical usage:</b></p>
 * <pre>
 * EnvironmentalImpactCalculator calculator = new EnvironmentalImpactCalculator();
 * calculator.loadFromFile("food_data.csv");
 * Meal meal = calculator.createMealFromImage("lunch_photo.jpg");
 * System.out.println(meal);
 * </pre>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 * @see FoodItem
 * @see Meal
 * @see ImageAnalysis
 */
public class EnvironmentalImpactCalculator implements FileOperations {
    
    /**
     * A map of food names to their environmental data.
     * Key = food name (e.g., "Rice"), Value = FoodItem with impact data.
     * Using a HashMap allows O(1) lookup by name.
     */
    private HashMap<String, FoodItem> foodItemDataset;
    
    /**
     * The image analysis service for AI-powered food recognition.
     */
    private ImageAnalysis imageAnalysis;

    /**
     * Creates a new EnvironmentalImpactCalculator.
     * Initializes an empty food database and the image analysis service.
     */
    public EnvironmentalImpactCalculator() {
        this.foodItemDataset = new HashMap<>();
        this.imageAnalysis = new ImageAnalysis("AIzaSyBqZv9G6inrgXgimMasF15-K-W5x4apyK4");
    }

    /**
     * Retrieves a food item by name from the database.
     * 
     * @param name The exact name of the food item (case-sensitive)
     * @return The FoodItem if found, null otherwise
     */
    public FoodItem getFoodItem(String name) {
        if (foodItemDataset.containsKey(name)) {
            return foodItemDataset.get(name);
        } else {
            System.out.println("Food item not found: " + name);
            return null;
        }
    }

    /**
     * Loads food items and their environmental data from a CSV file.
     * 
     * <p>The CSV should have the following columns:</p>
     * <ul>
     *   <li>Column 0: Food name</li>
     *   <li>Column 2: Carbon footprint (kg CO2e per kg)</li>
     *   <li>Column 6: Land use (m² per kg)</li>
     *   <li>Column 10: Nitrogen footprint (g N per kg)</li>
     *   <li>Column 18: Water usage (L per kg)</li>
     * </ul>
     * 
     * @param filename Path to the CSV file
     * @throws IOException If the file cannot be read
     */
    @Override
    public void loadFromFile(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            boolean isFirstLine = true;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                
                // Skip header row
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
                // Ensure we have enough columns
                if (parts.length < 19) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }
                
                try {
                    String name = parts[0];
                    double carbonFootprintPerKg = Double.parseDouble(parts[2]);
                    double waterUsagePerKg = Double.parseDouble(parts[18]);
                    double landUsePerKg = Double.parseDouble(parts[6]);
                    double nitrogenFootprint = Double.parseDouble(parts[10]);

                    FoodItem foodItem = new FoodItem(name, carbonFootprintPerKg, waterUsagePerKg, landUsePerKg, nitrogenFootprint);
                    foodItemDataset.put(name, foodItem);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid number format: " + parts[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Analyzes a food image and creates a Meal object with environmental data.
     * 
     * <p>This method coordinates the entire analysis pipeline:</p>
     * <ol>
     *   <li>Sends the image to Gemini AI for food recognition</li>
     *   <li>Parses the AI's JSON response to extract detected foods</li>
     *   <li>Looks up each detected food in our database</li>
     *   <li>Creates FoodPortion objects with the estimated weights</li>
     *   <li>Assembles everything into a Meal object</li>
     * </ol>
     * 
     * @param imagePath Path to the food image file
     * @return A Meal object containing all detected foods with their environmental impacts
     * @throws IOException If image analysis fails
     */
    public Meal createMealFromImage(String imagePath) throws IOException {
        System.out.println("Analyzing image...");
        
        // Step 1: Get AI analysis of the image
        String jsonResult = imageAnalysis.analyzeImage(imagePath);
        
        // Step 2: Parse the meal name from JSON
        int mealNameStart = jsonResult.indexOf("\"mealName\": \"") + 13;
        int mealNameEnd = jsonResult.indexOf("\"", mealNameStart);
        String mealName = jsonResult.substring(mealNameStart, mealNameEnd);

        // Step 3: Extract the detected items array
        int itemsStart = jsonResult.indexOf("\"detectedItems\": [") + 18;
        int itemsEnd = jsonResult.indexOf("]", itemsStart);
        String itemsSection = jsonResult.substring(itemsStart, itemsEnd);

        // Step 4: Split into individual item JSON objects
        String[] items = itemsSection.split("\\},");

        // Step 5: Process each detected item
        List<FoodPortion> portions = new ArrayList<>();

        for (String item : items) {
            // Extract the original label (what the AI saw)
            int originalLabelStart = item.indexOf("\"originalLabel\": \"") + 18;
            int originalLabelEnd = item.indexOf("\"", originalLabelStart);
            String originalLabel = item.substring(originalLabelStart, originalLabelEnd);
            
            // Extract the canonical name (matched to our database)
            int nameStart = item.indexOf("\"canonicalName\": \"") + 18;
            int nameEnd = item.indexOf("\"", nameStart);
            String canonicalName = item.substring(nameStart, nameEnd);
            
            // Extract the portion weight in kg
            int portionStart = item.indexOf("\"portionKg\": ") + 13;
            int portionEnd = item.indexOf(",", portionStart);
            double portionKg = Double.parseDouble(item.substring(portionStart, portionEnd).trim());
            
            // Look up the FoodItem in our database
            FoodItem foodItem = this.getFoodItem(canonicalName);
            
            // If found, create a FoodPortion
            if (foodItem != null) {
                portions.add(new FoodPortion(foodItem, portionKg));
            } else {
                System.out.println("Warning: " + originalLabel + " not found in database");
            }
        }

        // Step 6: Convert to array and create Meal
        FoodPortion[] portionsArray = portions.toArray(new FoodPortion[0]);
        return new Meal(mealName, portionsArray);
    }
}
