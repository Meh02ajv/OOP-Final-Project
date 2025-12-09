import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/**
 * The central class for calculating environmental impacts of meals.
 * 
 *This class serves as the main coordinator between the food database (loaded from CSV), the image analysis service (Gemini AI), and the meal calculation logic.
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public class EnvironmentalImpactCalculator implements FileOperations {
    
    /**
     * A map of food names to their environmental data.
     * Key = food name (e.g., "Rice"), Value = FoodItem with impact data.
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
        this.imageAnalysis = new ImageAnalysis("AIzaSyCSm1PtPf9sO1NkegYYe18U2YS_tXX0GxQ");
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
     * Loads food items and their environmental data from our CSV file.
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
                
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                if (line.isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                
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
     * Analyzes a food image from a file path and creates a Meal object.
     * 
     * @param imagePath Path to the food image file
     * @return A Meal object containing all detected foods with their environmental impacts
     * @throws IOException If image analysis fails
     */
    public Meal createMealFromImage(String imagePath) throws IOException {
        System.out.println("Analyzing image from path: " + imagePath);
        String jsonResult = imageAnalysis.analyzeImage(imagePath);
        return parseMealFromJson(jsonResult);
    }
    
    /**
     * Analyzes a food image and creates a Meal object.
     * 
     * @param imageBytes The image data
     * @param mimeType The MIME type of the image (png, jpg, etc.)
     * @return A Meal object containing all detected foods
     * @throws IOException If image analysis fails
     */
    public Meal createMealFromImage(byte[] imageBytes, String mimeType) throws IOException {
        System.out.println("Analyzing uploaded image (" + imageBytes.length + " bytes)...");
        String jsonResult = imageAnalysis.analyzeImage(imageBytes, mimeType);
        return parseMealFromJson(jsonResult);
    }

    /**
     *Method to clean JSON response from Gemini into a Meal object.
     */
    private Meal parseMealFromJson(String jsonResult) {
        // Parse the meal name from JSON
        int mealNameStart = jsonResult.indexOf("\"mealName\": \"") + 13;
        int mealNameEnd = jsonResult.indexOf("\"", mealNameStart);
        String mealName = jsonResult.substring(mealNameStart, mealNameEnd);

        // Extract the detected items array
        int itemsStart = jsonResult.indexOf("\"detectedItems\": [") + 18;
        int itemsEnd = jsonResult.indexOf("]", itemsStart);
        String itemsSection = jsonResult.substring(itemsStart, itemsEnd);

        // Process items
        List<FoodPortion> portions = new ArrayList<>();
        
        if (!itemsSection.trim().isEmpty()) {
            // Split into individual item JSON objects
            String[] items = itemsSection.split("\\},");

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
        }

        // Convert to array and create Meal
        FoodPortion[] portionsArray = portions.toArray(new FoodPortion[0]);
        return new Meal(mealName, portionsArray);
    }
}
