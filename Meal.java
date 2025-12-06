import java.util.Arrays;

/**
 * Represents a named meal composed of multiple FoodItem instances and
 * provides methods to calculate aggregated environmental footprints
 * (carbon, nitrogen, water) and land usage for the whole meal.
 *
 * <p>Notes:
 * - The constructor performs a defensive (shallow) copy of the provided
 *   FoodItem array to preserve encapsulation.
 * - getFoodItems() returns a defensive (shallow) copy of the internal array.
 * - Arrays.copyOf performs a shallow copy: the returned array is a new array
 *   object, but elements (FoodItem instances) are the same object references.
 *
 * @see FoodItem
 * @see MealInterface
 * @author Sebenemaryam
 * @version 1.1
 */
public class Meal implements MealInterface {
    /**
     * The name of this meal (e.g., "Banku", "Jollof Rice").
     */
    private String name;

    /**
     * The array of FoodItem objects that make up the meal.
     */
    private FoodItem[] foodItems;

    /**
     * Constructs a Meal with the provided name and food items.
     *
     * Note: this constructor does not perform explicit validation — it stores
     * a defensive shallow copy of the provided array. If foodItems is null,
     * Arrays.copyOf will throw a NullPointerException.
     *
     * @param name the meal name
     * @param foodItems the array of FoodItem instances that comprise the meal
     */
    public Meal(String name, FoodItem[] foodItems) {
        this.name = name;
        this.foodItems = Arrays.copyOf(foodItems, foodItems.length);
    }

    /**
     * Returns the name of the meal.
     *
     * @return the meal name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a defensive (shallow) copy of the internal FoodItem array.
     *
     * @return a new array containing the same FoodItem references
     */
    public FoodItem[] getFoodItems() {
        return Arrays.copyOf(foodItems, foodItems.length);
    }

    // ---------- Calculations ----------

    /**
     * Calculates the total carbon footprint for the meal by summing each item's carbon footprint.
     *
     * @return total carbon footprint
     */
    @Override
    public double calculateTotalCarbonFootprints() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getCarbon_Footprint_Per_Kg();
        }
        return total;
    }

    /**
     * Calculates the total nitrogen footprint for the meal by summing each item's nitrogen footprint.
     *
     * @return total nitrogen footprint
     */
    @Override
    public double calculateTotalNitrogenFootprints() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getNitrogen_Footprint();
        }
        return total;
    }

    /**
     * Calculates the combined total of carbon and nitrogen footprints.
     *
     * @return combined carbon + nitrogen footprints
     */
    @Override
    public double calculateTotalFootprints() {
        return calculateTotalCarbonFootprints() + calculateTotalNitrogenFootprints();
    }

    /**
     * Calculates the total water usage for the meal by summing each item's water usage.
     *
     * @return total water usage
     */
    @Override
    public double calculateTotalWaterUsage() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getWater_Usage_Per_Kg();
        }
        return total;
    }

    /**
     * Calculates the total land usage for the meal by summing each item's land usage.
     *
     * @return total land usage
     */
    @Override
    public double calculateLandUsage() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getLand_Use_Per_Kg();
        }
        return total;
    }
}
