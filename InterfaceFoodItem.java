public interface InterfaceFoodItem {

    String getName();
    void setName(String name);

    double getPortion_kg();
    void setPortion_kg(double portion_kg);

    double getCarbon_footprint_per_kg();
    void setCarbon_footprint_per_kg(double carbon_footprint_per_kg);

    double getWater_usage_per_kg();
    void setWater_usage_per_kg(double water_usage_per_kg);

    double getLand_usage_per_kg();
    void setLand_usage_per_kg(double land_usage_per_kg);

    String getFood_category();
    void setFood_category(String food_category);

    double getNitrogen_footprint();
    void setNitrogen_footprint(double nitrogen_footprint);


    // totals
//    default double getTotalCarbonFootprint() {
//        return getPortion_kg() * getCarbon_footprint_per_kg();
//    }
//
//    default double getTotalWaterUsage() {
//        return getPortion_kg() * getWater_usage_per_kg();
//    }

}