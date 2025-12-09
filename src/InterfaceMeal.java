/**
 * Interface defining the contract for meal objects.
 * 
 * <p>Provides methods for calculating the total environmental impact of a meal
 * by aggregating the impacts of all food portions it contains.</p>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public interface InterfaceMeal {
    
    /**
     * Calculates the total carbon footprint of the meal.
     * @return Total carbon footprint in kg CO2 equivalent
     */
    double calculateTotalFootprints();
    
    /**
     * Calculates the total water usage of the meal.
     * @return Total water usage in liters
     */
    double calculateTotalWaterUsage();
    
    /**
     * Calculates the total land usage of the meal.
     * @return Total land usage in square meters
     */
    double calculateLandUsage();
    
    /**
     * Calculates the total nitrogen waste (eutrophication) of the meal.
     * @return Total nitrogen waste in grams
     */
    double calculateNitrogenWaste();
}
