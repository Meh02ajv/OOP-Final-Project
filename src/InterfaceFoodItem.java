/**
 * Interface defining the contract for food items with environmental impact data.
 * 
 * <p>Any class implementing this interface must provide methods to access and modify
 * the environmental metrics associated with a food item, including carbon footprint,
 * water usage, land use, and nitrogen footprint - all measured per kilogram.</p>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public interface InterfaceFoodItem {
    
    /**
     * Gets the name of the food item.
     * @return The food item name (e.g., "Rice", "Chicken breast")
     */
    String getName();
    
    /**
     * Sets the name of the food item.
     * @param name The name to set
     */
    void setName(String name);
    
    /**
     * Gets the carbon footprint per kilogram of this food.
     * @return Carbon footprint in kg CO2 equivalent per kg of food
     */
    double getCarbonFootprintPerKg();
    
    /**
     * Sets the carbon footprint per kilogram.
     * @param carbonFootprintPerKg The carbon footprint value to set
     */
    void setCarbonFootprintPerKg(double carbonFootprintPerKg);
    
    /**
     * Gets the water usage per kilogram of this food.
     * @return Water usage in liters per kg of food
     */
    double getWaterUsagePerKg();
    
    /**
     * Sets the water usage per kilogram.
     * @param waterUsagePerKg The water usage value to set
     */
    void setWaterUsagePerKg(double waterUsagePerKg);
    
    /**
     * Gets the land use per kilogram of this food.
     * @return Land use in square meters per kg of food
     */
    double getLandUsePerKg();
    
    /**
     * Sets the land use per kilogram.
     * @param landUsePerKg The land use value to set
     */
    void setLandUsePerKg(double landUsePerKg);
    
    /**
     * Gets the nitrogen footprint (eutrophication) per kilogram.
     * @return Nitrogen footprint in grams of nitrogen per kg of food
     */
    double getNitrogenFootprint();
    
    /**
     * Sets the nitrogen footprint per kilogram.
     * @param nitrogenFootprint The nitrogen footprint value to set
     */
    void setNitrogenFootprint(double nitrogenFootprint);
    
    /**
     * Returns a string representation of the food item.
     * @return String containing all food item details
     */
    String toString();
}
