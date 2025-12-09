/**
 * Represents a food item with its environmental impact metrics.
 * 
 * <p>This class stores the base environmental data for a food type, with all
 * values measured per kilogram. It serves as reference data that can be combined
 * with a portion size to calculate actual environmental impact.</p>
 * 
 * <p>Environmental metrics included:</p>
 * <ul>
 *   <li>Carbon footprint (kg CO2 equivalent per kg)</li>
 *   <li>Water usage (liters per kg)</li>
 *   <li>Land use (square meters per kg)</li>
 *   <li>Nitrogen footprint/eutrophication (grams N per kg)</li>
 * </ul>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 * @see FoodPortion
 * @see InterfaceFoodItem
 */
public class FoodItem implements InterfaceFoodItem {
    
    /** The name of the food item (e.g., "Rice", "Beef burger") */
    private String name;
    
    /** Carbon footprint in kg CO2 equivalent per kg of food */
    private double carbonFootprintPerKg;
    
    /** Water usage in liters per kg of food */
    private double waterUsagePerKg;
    
    /** Land use in square meters per kg of food */
    private double landUsePerKg;
    
    /** Nitrogen footprint (eutrophication) in grams per kg of food */
    private double nitrogenFootprint;

    /**
     * Creates a new FoodItem with all environmental metrics specified.
     * 
     * @param name The name of the food item
     * @param carbonFootprintPerKg Carbon footprint in kg CO2e per kg
     * @param waterUsagePerKg Water usage in liters per kg
     * @param landUsePerKg Land use in m² per kg
     * @param nitrogenFootprint Nitrogen footprint in grams per kg
     */
    public FoodItem(String name, double carbonFootprintPerKg, double waterUsagePerKg, 
                   double landUsePerKg, double nitrogenFootprint) {
        this.name = name;
        this.carbonFootprintPerKg = carbonFootprintPerKg;
        this.waterUsagePerKg = waterUsagePerKg;
        this.landUsePerKg = landUsePerKg;
        this.nitrogenFootprint = nitrogenFootprint;
    }

    /**
     * Default constructor for creating an empty FoodItem.
     */
    public FoodItem() {}

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getCarbonFootprintPerKg() {
        return carbonFootprintPerKg;
    }

    @Override
    public void setCarbonFootprintPerKg(double carbonFootprintPerKg) {
        this.carbonFootprintPerKg = carbonFootprintPerKg;
    }

    @Override
    public double getWaterUsagePerKg() {
        return waterUsagePerKg;
    }

    @Override
    public void setWaterUsagePerKg(double waterUsagePerKg) {
        this.waterUsagePerKg = waterUsagePerKg;
    }

    @Override
    public double getLandUsePerKg() {
        return landUsePerKg;
    }

    @Override
    public void setLandUsePerKg(double landUsePerKg) {
        this.landUsePerKg = landUsePerKg;
    }

    @Override
    public double getNitrogenFootprint() {
        return nitrogenFootprint;
    }

    @Override
    public void setNitrogenFootprint(double nitrogenFootprint) {
        this.nitrogenFootprint = nitrogenFootprint;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "name='" + name + '\'' +
                ", carbonFootprintPerKg=" + carbonFootprintPerKg +
                ", waterUsagePerKg=" + waterUsagePerKg +
                ", landUsePerKg=" + landUsePerKg +
                ", nitrogenFootprint=" + nitrogenFootprint +
                '}';
    }
}
