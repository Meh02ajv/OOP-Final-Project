public interface InterfaceFoodItem {

    String getName();
    void setName(String name);

    double getPortionKg();
    void setPortionKg(double portion_kg);

    double getCarbon_footprintPerKg();
    void setCarbonFootprintPerKg(double carbonFootprintPerKg);

    double getWaterUsagePerKg();
    void setWaterUsagePerKg(double waterUsagePerKg);

    double getLandUsagePerKg();
    void setLandUsagePerKg(double landUsagePerKg);

    String getFoodCategory();
    void setFoodCategory(String foodCategory);

    double getNitrogenFootprint();
    void setNitrogenFootprint(double nitrogenFootprint);


    // totals
//    default double getTotalCarbonFootprint() {
//        return getPortionKg() * getCarbonFootprintPerKg();
//    }
//
//    default double getTotalWaterUsage() {
//        return getPortionKg() * getWaterUsagePerKg();
//    }

}
