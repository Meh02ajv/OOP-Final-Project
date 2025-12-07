/**
 * FoodItem class represents various food items with their environmental impact metrics.
 *
 * This class models food items such as lentils, cheese, beef, etc., and stores their
 * environmental attributes including carbon footprint, water usage, land usage, and
 * nitrogen footprint per kilogram. It provides methods to calculate the total
 * environmental impact based on portion size.
 *
 * @author Miriam Gale
 * @version 1.0
 */
public class FoodItem {
    private String name;
    private double portionKg;
    private double carbonFootprintPerKg;
    private double waterUsagePerKg;
    private double LandUsePerKg;
    private String foodCategory;
    private double nitrogenFootprint;

    /**
     * Constructor
     * Validates all inputs and sets safe defaults for invalid values.
     *
     * @param name the name of the food item (e.g., "Beef", "Lentils")
     * @param portion_kg the portion size of the food item in kilograms (not used in constructor, set via setter)
     * @param carbon_footprint_per_kg  emissions in kg CO2 per kg of food
     * @param water_usage_per_kg water consumption in liters per kg of food
     * @param land_use_per_kg land usage in square meters per kg of food
     * @param food_category the category of food (e.g., "Meat", "Legumes", "Dairy")
     * @param nitrogen_footprint nitrogen emissions in grams per kg of food
     */
    public FoodItem(String name, double portionKg, double carbonFootprintPerKg,
                    double waterUsagePerKg, double landUsePerKg,
                    String foodCategory, double nitrogenFootprint) {

        // Validate and set name
        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown Food";
            System.out.println("Warning: Invalid name provided. Set to 'Unknown Food'");
        } else {
            this.name = name;
        }

        // Validate and set food category
        if (foodCategory == null || foodCategory.trim().isEmpty()) {
            this.foodCategory = "Uncategorized";
            System.out.println("Warning: Invalid category provided. Set to 'Uncategorized'");
        } else {
            this.foodCategory = foodCategory;
        }

        // Validate environmental values (set to 0 if negative)
        this.carbonFootprintPerKg = (carbonFootprintPerKg < 0) ? 0 : carbonFootprintPerKg;
        if (carbonFootprintPerKg < 0) {
            System.out.println("Warning: Negative carbon footprint provided. Set to 0");
        }

        this.waterUsagePerKg = (waterUsagePerKg < 0) ? 0 :waterUsagePerKg;
        if (waterUsagePerKg< 0) {
            System.out.println("Warning: Negative water usage provided. Set to 0");
        }

        this.landUsePerKg = (landUsePerKg < 0) ? 0 : landUsePerKg;
        if (landUsePerKg < 0) {
            System.out.println("Warning: Negative land use provided. Set to 0");
        }

        this.nitrogenFootprint = (nitrogenFootprint < 0) ? 0 : nitrogenFootprint;
        if (nitrogenFootprint < 0) {
            System.out.println("Warning: Negative nitrogen footprint provided. Set to 0");
        }

        //Initialise portion to 0
        this.portionKg = 0.0;
    }

    // Accessors or Getters
    //Gets the name of the food item
    public String getName() {
        return name;
    }

    //Gets the portion size in kilograms.
    public double getPortionKg() {
        return portionKg;
    }

    //Gets the carbon footprint per kilogram.
    public double getCarbonFootprintPerKg() {
        return carbonFootprintPerKg;
    }

    //Gets the water usage per kilogram.
    public double getWaterUsagePerKg() {
        returnwaterUsagePerKg;
    }

    //Gets the land use per kilogram
    public double getLandUsePerKg() {
        return landUsePerKg;
    }

    //Gets the food category.
    public String getFoodCategory() {
        return foodCategory;
    }

    //Gets the nitrogen footprint per kilogram.
    public double getNitrogenFootprint() {
        return nitrogenFootprint;
    }

    // Setters with validation

    //Sets the name of the food item.
    //Validates that name is not null or empty.
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Warning: Invalid name provided. Name unchanged.");
            return;
        }
        this.name = name;
    }

    //Sets the portion size in kilograms.
    // Validates that portion is non-negative. Sets to 0 if negative value provided.
    public void setPortionKg(double portionkg) {
        if (portionKg < 0) {
            System.out.println("Warning: Negative portion provided. Setting to 0.");
            this.portionKg = 0;
            return;
        }
        this.portion_kg = portion_kg;
    }

    //Sets the carbon footprint per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setcarbonFootprintPerKg(double carbonFootprintPerKg) {
        if (carbon_footprint_per_kg < 0) {
            System.out.println("Warning: Negative carbon footprint provided. Setting to 0.");
            this.carbonFootprintPerKg = 0;
            return;
        }
        this.carbonFootprintPerKg = carbonFootprintPerKg;
    }

    //Sets the water usage per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setwaterUsagePerKg(double waterUsagePerKg) {
        if (waterUsagePerKg < 0) {
            System.out.println("Warning: Negative water usage provided. Setting to 0.");
            this.waterUsagePerKg = 0;
            return;
        }
        this.waterUsagePerKg =waterUsagePerKg;
    }
    //Sets the land use per kilogram.
    //Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setLandUsePerKg(double LandUsePerKg) {
        if (landUsePerKg < 0) {
            System.out.println("Warning: Negative land use provided. Setting to 0.");
            this.landUsePerKg = 0;
            return;
        }
        this.land_use_per_kg = land_use_per_kg;
    }
    //Sets the food category.
    //Validates that category is not null or empty.
    public void setFoodCategory(String food_category) {
        if (foodCategory == null || foodCategory.trim().isEmpty()) {
            System.out.println("Warning: Invalid category provided. Category unchanged.");
            return;
        }
        this.foodCategory = foodCategory;
    }
    //Sets the nitrogen footprint per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setNitrogenFootprint(double nitrogenFootprint) {
        if (nitrogenFootprint < 0) {
            System.out.println("Warning: Negative nitrogen footprint provided. Setting to 0.");
            this.nitrogenFootprint = 0;
            return;
        }
        this.nitrogenFootprint = nitrogenFootprint;
    }

    // Calculation methods - calculate total impact based on portion
    //Calculates the total carbon footprint for this food item.
    public double getTotalCarbonFootprint() {
        return portionKg * carbonFootprintPerKg;
    }

    //Calculates the total water usage for this food item.
    public double getTotalWaterUsage() {
        return portionKg * waterUsagePerKg;
    }

    //Calculates the total land usage for this food item.
    public double getTotalLandUsage() {
        return portionKg * landUsePerKg;
    }

    //Calculates the total nitrogen footprint for this food item.
    public double getTotalNitrogenFootprint() {
        return portionKg * nitrogenFootprint;
    }

    // Returns a string representation of this FoodItem.
    //Includes the name, category, portion, and all calculated environmental impacts.
    @Override
    public String toString() {
        return String.format(
                "FoodItem[name='%s', category='%s', portion=%.2fkg, " +
                        "carbon=%.2fkg CO2, water=%.2fL, land=%.2fm², nitrogen=%.2fg]",
                name, food_category, portion_kg,
                getTotalCarbonFootprint(), getTotalWaterUsage(),
                getTotalLandUsage(), getTotalNitrogenFootprint()
        );
    }

    // Compares this FoodItem with another object for equality.
    //Two FoodItems are considered equal if they have the same name, category,and environmental impact values per kg .
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FoodItem foodItem = (FoodItem) obj;
        return Double.compare(foodItem.carbonFootprintPerKg, carbonFootprintPerKg) == 0 &&
                Double.compare(foodItem.waterUsagePerKg, waterUsagePerKg) == 0 &&
                Double.compare(foodItem.landUsePerKg, landUsePerKg) == 0 &&
                Double.compare(foodItem.nitrogenFootprint, nitrogenFootprint) == 0 &&
                name.equals(foodItem.name) &&
                food_category.equals(foodItem.foodCategory);
    }

    //Generates a hash code for this FoodItem.
    @Override
    public int hashCode(){
        int result = name.hashCode();
        result = 31 * result + foodCategory.hashCode();
        result = 31 * result + Double.hashCode(carbonFootprintPerKg);
        result = 31 * result + Double.hashCode(waterUsagePerKg);
        result = 31 * result + Double.hashCode(landUsePerKg);
        result = 31 * result + Double.hashCode(nitrogenFootprint);
        return result;
    }
}

