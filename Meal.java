public class Meal implements InterfaceMeal {
  private FoodPortion[] foodPortions;
  private String name;

  public Meal(String name, FoodPortion[] foodPortions) {
      this.name = name;
      this.foodPortions = foodPortions;
  }

  public FoodPortion[] getFoodPortions() {
      return foodPortions;
  }

  public void setFoodPortions(FoodPortion[] foodPortions) {
      this.foodPortions = foodPortions;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  @Override
  public double calculateTotalFootprints() {
      double total = 0;
      if (foodPortions != null) {
          for (FoodPortion portion : foodPortions) {
              if (portion != null) {
                  total += portion.calculateCarbonFootprint();
              }
          }
      }
      return total;
  }

  @Override
  public double calculateTotalWaterUsage() {
      double total = 0;
      if (foodPortions != null) {
          for (FoodPortion portion : foodPortions) {
              if (portion != null) {
                  total += portion.calculateWaterUsage();
              }
          }
      }
      return total;
  }

  @Override
  public double calculateLandUsage() {
      double total = 0;
      if (foodPortions != null) {
          for (FoodPortion portion : foodPortions) {
              if (portion != null) {
                  total += portion.calculateLandUsage();
              }
          }
      }
      return total;
  }

  @Override
  public double calculateNitrogenWaste() {
      double total = 0;
      if (foodPortions != null) {
          for (FoodPortion portion : foodPortions) {
              if (portion != null) {
                  total += portion.calculateNitrogenWaste();
              }
          }
      }
      return total;
  }
  
  @Override
  public String toString() {
    String mealString = "\nMeal: " + this.getName() + "\n";
      mealString += "Items:\n";
      for (FoodPortion item : this.getFoodPortions()) {
          mealString += "- " + item.getFoodItem().getName() + " (" + item.getPortionKg() + "kg)\n";
      }
      mealString += "\nCalculations:\n";
      mealString += String.format("Total Carbon Footprint: %.2f kgCO2e%n", this.calculateTotalFootprints());
      mealString += String.format("Total Water Usage: %.2f L%n", this.calculateTotalWaterUsage());
      mealString += String.format("Total Land Usage: %.2f m2%n", this.calculateLandUsage());
      mealString += String.format("Total Nitrogen Waste: %.2f gN%n", this.calculateNitrogenWaste());
      return mealString;
  }

  public String toJson() {
    StringBuilder json = new StringBuilder();
    json.append("{\n");
    json.append("  \"mealName\": \"").append(this.name).append("\",\n");
    json.append("  \"items\": [\n");
    
    for (int i = 0; i < foodPortions.length; i++) {
        FoodPortion portion = foodPortions[i];
        json.append("    {\n");
        json.append("      \"name\": \"").append(portion.getFoodItem().getName()).append("\",\n");
        json.append("      \"portionKg\": ").append(portion.getPortionKg()).append("\n");
        json.append("    }");
        if (i < foodPortions.length - 1) json.append(",");
        json.append("\n");
    }
    
    json.append("  ],\n");
    json.append("  \"totals\": {\n");
    json.append("    \"carbonFootprint\": ").append(String.format("%.2f", calculateTotalFootprints())).append(",\n");
    json.append("    \"waterUsage\": ").append(String.format("%.2f", calculateTotalWaterUsage())).append(",\n");
    json.append("    \"landUsage\": ").append(String.format("%.2f", calculateLandUsage())).append(",\n");
    json.append("    \"nitrogenWaste\": ").append(String.format("%.2f", calculateNitrogenWaste())).append("\n");
    json.append("  }\n");
    json.append("}");
    
    return json.toString();
  }
}
