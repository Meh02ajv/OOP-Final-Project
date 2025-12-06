/**
 * Represents environmental metrics for a Meal (carbon footprint, water usage, land usage).
 * - Keep units consistent when consumers compare or aggregate values.
 * - If you need higher numeric precision (e.g., for summing many small contributions), consider
 *   using BigDecimal in implementing classes and converting to double only where appropriate.
 * This interface provides a small default helper to compute a weighted aggregate impact, so
 * that callers can obtain a single score without duplicating aggregation logic.
 */
public interface MealInterface {


    /**
     * Calculate the meal's total carbon footprint.
     *
     * @return total carbon footprint (recommended: kg CO2e)
     */
    double calculateTotalCarbonFootprints();

    /**
     * Calculate the meal's total nitrogen footprint.
     *
     * @return total nitrogen footprint
     */
    double calculateTotalNitrogenFootprints();


    /**
     * Calculate the meal's total footprint.
     *
     * @return total water usage (recommended: kg)
     */
    double calculateTotalFootprints();


    /**
     * Calculate the meal's total water usage.
     *
     * @return total water usage (recommended: liters)
     */
    double calculateTotalWaterUsage();

    /**
     * Calculate the meal's land usage.
     *
     * @return total land usage (recommended: square meters)
     */
    double calculateLandUsage();
}
