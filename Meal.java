public class Meal implements MealInterface {
    private String name;
    private FoodItem[] foodItems;

    // Constructor
    public Meal(String name, FoodItem[] foodItems) {
        this.name = name;
        this.foodItems = foodItems;
    }

    public String getName() {
        return name;
    }

    public FoodItem[] getFoodItems() {
        return foodItems;
    }

    // ---------- Calculations ----------

    @Override
    public double calculateTotalCarbonFootprints() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getCarbonFootprint();
        }
        return total;
    }

    @Override
    public double calculateTotalNitrogenFootprints() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getNitrogenFootprint();
        }
        return total;
    }

    @Override
    public double calculateTotalFootprints() {
        return calculateTotalCarbonFootprints() + calculateTotalNitrogenFootprints();
    }

    @Override
    public double calculateTotalWaterUsage() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getWaterUsage();
        }
        return total;
    }

    @Override
    public double calculateLandUsage() {
        double total = 0;
        for (FoodItem item: foodItems) {
            total += item.getLandUsage();
        }
        return total;
    }
}
