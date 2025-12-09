/**
 * Represents a specific portion of a food item.
 * 
 * This class combines a FoodItem (which stores per-kg environmental data)
 * with a specific portion weight. It provides methods to calculate the actual environmental impact for that specific portion.
 * For example, if Rice has 4 kg CO2e per kg, and you have a 0.25 kg portion, the carbon footprint would be 4 × 0.25 = 1 kg CO2e.
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public class FoodPortion {
    
    /** The food item containing per-kg environmental data */
    private FoodItem foodItem;
    
    /** The weight of this portion in kilograms */
    private double portionKg;

    /**
     * Creates a new FoodPortion with a specified food item and weight.
     * 
     * @param foodItem The food item containing per-kg environmental data
     * @param portionKg The weight of this portion in kilograms
     */
    public FoodPortion(FoodItem foodItem, double portionKg) {
        this.foodItem = foodItem;
        this.portionKg = portionKg;
    }

    /**
     * Gets the food item associated with this portion.
     * @return The FoodItem object
     */
    public FoodItem getFoodItem() {
        return foodItem;
    }

    /**
     * Sets the food item for this portion.
     * @param foodItem The FoodItem to set
     */
    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    /**
     * Gets the portion weight in kilograms.
     * @return The portion weight in kg
     */
    public double getPortionKg() {
        return portionKg;
    }

    /**
     * Sets the portion weight in kilograms.
     * @param portionKg The weight to set
     */
    public void setPortionKg(double portionKg) {
        this.portionKg = portionKg;
    }

    /**
     * Calculates the carbon footprint for this specific portion.
     * 
     * @return Carbon footprint in kg CO2 equivalent
     */
    public double calculateCarbonFootprint() {
        return foodItem.getCarbonFootprintPerKg() * portionKg;
    }

    /**
     * Calculates the water usage for this specific portion.
     * 
     * @return Water usage in liters
     */
    public double calculateWaterUsage() {
        return foodItem.getWaterUsagePerKg() * portionKg;
    }

    /**
     * Calculates the land usage for this specific portion.
     * @return Land usage in square meters
     */
    public double calculateLandUsage() {
        return foodItem.getLandUsePerKg() * portionKg;
    }

    /**
     * Calculates the nitrogen waste for this specific portion.
     * @return Nitrogen waste in grams
     */
    public double calculateNitrogenWaste() {
        return foodItem.getNitrogenFootprint() * portionKg;
    }

    @Override
    public String toString() {
        return "FoodPortion{" +
                "foodItem=" + foodItem.getName() +
                ", portionKg=" + portionKg +
                '}';
    }
}
