/**
 * Represents a meal consisting of multiple food portions.
 * 
 * A meal aggregates multiple FoodPortion objects and provides methods
 * to calculate the total environmental impact by summing the impacts of all portions.
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public class Meal implements InterfaceMeal {
    
    /** Array of food portions that make up this meal */
    private FoodPortion[] foodPortions;
    
    /** The name of the meal (e.g., "Dinner", "Jollof Rice with Chicken") */
    private String name;

    /**
     * Creates a new Meal with a name and array of food portions.
     * 
     * @param name The name of the meal
     * @param foodPortions Array of FoodPortion objects in the meal
     */
    public Meal(String name, FoodPortion[] foodPortions) {
        this.name = name;
        this.foodPortions = foodPortions;
    }

    /**
     * Gets the array of food portions in this meal.
     * @return Array of FoodPortion objects
     */
    public FoodPortion[] getFoodPortions() {
        return foodPortions;
    }

    /**
     * Sets the food portions for this meal.
     * @param foodPortions Array of FoodPortion objects to set
     */
    public void setFoodPortions(FoodPortion[] foodPortions) {
        this.foodPortions = foodPortions;
    }

    /**
     * Gets the name of this meal.
     * @return The meal name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this meal.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Calculates the total carbon footprint of the meal.
     * Sums the carbon footprint of all food portions.
     * 
     * @return Total carbon footprint in kg CO2 equivalent
     */
    @Override
    public double calculateTotalFootprints() {
        double total = 0;
        if (foodPortions != null) {
            for (FoodPortion portion : foodPortions) {
                if (portion != null) {
                    total += portion.calculateCarbonFootprint();
                }
            }
        }
        return total;
    }

    /**
     * Calculates the total water usage of the meal.
     * Sums the water usage of all food portions.
     * 
     * @return Total water usage in liters
     */
    @Override
    public double calculateTotalWaterUsage() {
        double total = 0;
        if (foodPortions != null) {
            for (FoodPortion portion : foodPortions) {
                if (portion != null) {
                    total += portion.calculateWaterUsage();
                }
            }
        }
        return total;
    }

    /**
     * Sums the land usage of all food portions.
     * 
     * @return Total land usage in square meters
     */
    @Override
    public double calculateLandUsage() {
        double total = 0;
        if (foodPortions != null) {
            for (FoodPortion portion : foodPortions) {
                if (portion != null) {
                    total += portion.calculateLandUsage();
                }
            }
        }
        return total;
    }

    /**
     * Sums the nitrogen waste of all food portions.
     * 
     * @return Total nitrogen waste in grams
     */
    @Override
    public double calculateNitrogenWaste() {
        double total = 0;
        if (foodPortions != null) {
            for (FoodPortion portion : foodPortions) {
                if (portion != null) {
                    total += portion.calculateNitrogenWaste();
                }
            }
        }
        return total;
    }
    
    /**
     * Returns a string of the meal.
     * Includes the meal name, all items with portions, and calculated totals.
     * 
     * @return Formatted string with meal details and environmental impact
     */
    @Override
    public String toString() {
        String mealString = "\nMeal: " + this.getName() + "\n";
        mealString += "Items:\n";
        for (FoodPortion item : this.getFoodPortions()) {
            mealString += "- " + item.getFoodItem().getName() + " (" + item.getPortionKg() + "kg)\n";
        }
        mealString += "\nCalculations:\n";
        mealString += String.format("Total Carbon Footprint: %.2f kgCO2e%n", this.calculateTotalFootprints());
        mealString += String.format("Total Water Usage: %.2f L%n", this.calculateTotalWaterUsage());
        mealString += String.format("Total Land Usage: %.2f m2%n", this.calculateLandUsage());
        mealString += String.format("Total Nitrogen Waste: %.2f gN%n", this.calculateNitrogenWaste());
        return mealString;
    }

    /**
     * Converts the meal to a JSON string representation that the web app can use.
     * @return JSON string representation of the meal
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"mealName\": \"").append(this.name).append("\",\n");
        json.append("  \"items\": [\n");
        
        for (int i = 0; i < foodPortions.length; i++) {
            FoodPortion portion = foodPortions[i];
            json.append("    {\n");
            json.append("      \"name\": \"").append(portion.getFoodItem().getName()).append("\",\n");
            json.append("      \"portionKg\": ").append(portion.getPortionKg()).append("\n");
            json.append("    }");
            if (i < foodPortions.length - 1) json.append(",");
            json.append("\n");
        }
        
        json.append("  ],\n");
        json.append("  \"totals\": {\n");
        json.append("    \"carbonFootprint\": ").append(String.format("%.2f", calculateTotalFootprints())).append(",\n");
        json.append("    \"waterUsage\": ").append(String.format("%.2f", calculateTotalWaterUsage())).append(",\n");
        json.append("    \"landUsage\": ").append(String.format("%.2f", calculateLandUsage())).append(",\n");
        json.append("    \"nitrogenWaste\": ").append(String.format("%.2f", calculateNitrogenWaste())).append("\n");
        json.append("  }\n");
        json.append("}");
        
        return json.toString();
    }
}
