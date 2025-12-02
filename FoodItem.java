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
    private double portion_kg;
    private double carbon_footprint_per_kg;
    private double water_usage_per_kg;
    private double land_use_per_kg;
    private String food_category;
    private double nitrogen_footprint;

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
    public FoodItem(String name, double portion_kg, double carbon_footprint_per_kg,
                    double water_usage_per_kg, double land_use_per_kg,
                    String food_category, double nitrogen_footprint) {

        // Validate and set name
        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown Food";
            System.out.println("Warning: Invalid name provided. Set to 'Unknown Food'");
        } else {
            this.name = name;
        }

        // Validate and set food category
        if (food_category == null || food_category.trim().isEmpty()) {
            this.food_category = "Uncategorized";
            System.out.println("Warning: Invalid category provided. Set to 'Uncategorized'");
        } else {
            this.food_category = food_category;
        }

        // Validate environmental values (set to 0 if negative)
        this.carbon_footprint_per_kg = (carbon_footprint_per_kg < 0) ? 0 : carbon_footprint_per_kg;
        if (carbon_footprint_per_kg < 0) {
            System.out.println("Warning: Negative carbon footprint provided. Set to 0");
        }

        this.water_usage_per_kg = (water_usage_per_kg < 0) ? 0 : water_usage_per_kg;
        if (water_usage_per_kg < 0) {
            System.out.println("Warning: Negative water usage provided. Set to 0");
        }

        this.land_use_per_kg = (land_use_per_kg < 0) ? 0 : land_use_per_kg;
        if (land_use_per_kg < 0) {
            System.out.println("Warning: Negative land use provided. Set to 0");
        }

        this.nitrogen_footprint = (nitrogen_footprint < 0) ? 0 : nitrogen_footprint;
        if (nitrogen_footprint < 0) {
            System.out.println("Warning: Negative nitrogen footprint provided. Set to 0");
        }

        //Initialise portion to 0
        this.portion_kg = 0.0;
    }

    // Accessors or Getters
    //Gets the name of the food item
    public String getName() {
        return name;
    }

    //Gets the portion size in kilograms.
    public double getPortion_Kg() {
        return portion_kg;
    }

    //Gets the carbon footprint per kilogram.
    public double getCarbon_Footprint_Per_Kg() {
        return carbon_footprint_per_kg;
    }

    //Gets the water usage per kilogram.
    public double getWater_Usage_Per_Kg() {
        return water_usage_per_kg;
    }

    //Gets the land use per kilogram
    public double getLand_Use_Per_Kg() {
        return land_use_per_kg;
    }

    //Gets the food category.
    public String getFood_Category() {
        return food_category;
    }

    //Gets the nitrogen footprint per kilogram.
    public double getNitrogen_Footprint() {
        return nitrogen_footprint;
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
    public void setPortion_Kg(double portion_kg) {
        if (portion_kg < 0) {
            System.out.println("Warning: Negative portion provided. Setting to 0.");
            this.portion_kg = 0;
            return;
        }
        this.portion_kg = portion_kg;
    }

    //Sets the carbon footprint per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setCarbon_Footprint_Per_Kg(double carbon_footprint_per_kg) {
        if (carbon_footprint_per_kg < 0) {
            System.out.println("Warning: Negative carbon footprint provided. Setting to 0.");
            this.carbon_footprint_per_kg = 0;
            return;
        }
        this.carbon_footprint_per_kg = carbon_footprint_per_kg;
    }

    //Sets the water usage per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setWater_Usage_Per_Kg(double water_usage_per_kg) {
        if (water_usage_per_kg < 0) {
            System.out.println("Warning: Negative water usage provided. Setting to 0.");
            this.water_usage_per_kg = 0;
            return;
        }
        this.water_usage_per_kg = water_usage_per_kg;
    }
    //Sets the land use per kilogram.
    //Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setLand_Use_Per_Kg(double land_use_per_kg) {
        if (land_use_per_kg < 0) {
            System.out.println("Warning: Negative land use provided. Setting to 0.");
            this.land_use_per_kg = 0;
            return;
        }
        this.land_use_per_kg = land_use_per_kg;
    }
    //Sets the food category.
    //Validates that category is not null or empty.
    public void setFood_Category(String food_category) {
        if (food_category == null || food_category.trim().isEmpty()) {
            System.out.println("Warning: Invalid category provided. Category unchanged.");
            return;
        }
        this.food_category = food_category;
    }
    //Sets the nitrogen footprint per kilogram.
    // Validates that value is non-negative. Sets to 0 if negative value provided.
    public void setNitrogen_Footprint(double nitrogen_footprint) {
        if (nitrogen_footprint < 0) {
            System.out.println("Warning: Negative nitrogen footprint provided. Setting to 0.");
            this.nitrogen_footprint = 0;
            return;
        }
        this.nitrogen_footprint = nitrogen_footprint;
    }

    // Calculation methods - calculate total impact based on portion
    //Calculates the total carbon footprint for this food item.
    public double getTotalCarbonFootprint() {
        return portion_kg * carbon_footprint_per_kg;
    }

    //Calculates the total water usage for this food item.
    public double getTotalWaterUsage() {
        return portion_kg * water_usage_per_kg;
    }

    //Calculates the total land usage for this food item.
    public double getTotalLandUsage() {
        return portion_kg * land_use_per_kg;
    }

    //Calculates the total nitrogen footprint for this food item.
    public double getTotalNitrogenFootprint() {
        return portion_kg * nitrogen_footprint;
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
        return Double.compare(foodItem.carbon_footprint_per_kg, carbon_footprint_per_kg) == 0 &&
                Double.compare(foodItem.water_usage_per_kg, water_usage_per_kg) == 0 &&
                Double.compare(foodItem.land_use_per_kg, land_use_per_kg) == 0 &&
                Double.compare(foodItem.nitrogen_footprint, nitrogen_footprint) == 0 &&
                name.equals(foodItem.name) &&
                food_category.equals(foodItem.food_category);
    }

    //Generates a hash code for this FoodItem.
    @Override
    public int hashCode(){
        int result = name.hashCode();
        result = 31 * result + food_category.hashCode();
        result = 31 * result + Double.hashCode(carbon_footprint_per_kg);
        result = 31 * result + Double.hashCode(water_usage_per_kg);
        result = 31 * result + Double.hashCode(land_use_per_kg);
        result = 31 * result + Double.hashCode(nitrogen_footprint);
        return result;
    }
}

