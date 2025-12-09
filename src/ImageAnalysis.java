import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

/**
 * Handles AI-powered food image analysis using Google's Gemini Vision API.
 * This class sends food images to Google's Gemini AI for recognition
 * and returns structured JSON with detected food items and portions.
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 */
public class ImageAnalysis {
    
    /** API key for authenticating with Google's Gemini API */
    private String apiKey;

    /** The Gemini API endpoint URL */
    private static final String GEMINI_API_URL = 
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    /** System prompt with instructions for the AI on how to analyze food images */
    private static final String SYSTEM_PROMPT = """
        You are an expert food recognition system specialized in analyzing meal images for environmental impact calculation.

        **YOUR TASK:**
        1. Analyze the uploaded image and identify all visible food items
        2. Match each detected food to its closest canonical name from the provided database
        3. Estimate portion sizes in kilograms (kg) based on visual cues
        4. Provide confidence scores for your detections

        **CANONICAL NAME MATCHING RULES:**
        - You MUST match detected foods to names from this list (these are the exact keys in our database):
          [Beef burger, Beef steak, Beef mince, Chicken breast, Chicken thighs, Chicken wings, Pork chops, Pork sausages, Lamb chops, Salmon, Tuna, Prawns, Cod, Rice, Bread, Pasta shells, Spaghetti, Naan, Potatoes, Beans, Lentils, Chickpeas, Tofu, Eggs, Cow's milk, Cheddar cheese, Butter, Yoghurt, Broccoli, Carrots, Tomatoes, Lettuce, Spinach, Onions, Peppers, Cabbage, Apples, Bananas, Oranges, Grapes, Strawberries, Pineapple, Olive oil, Sunflower oil, Sugar, Chocolate cake, Ice cream, Biscuits, Coffee beans, Tea, Beer, Wine]
        - If an exact match is not possible, use the CLOSEST available canonical name
        - If no reasonable match exists, use "other" and explain in notes

        **PORTION ESTIMATION GUIDELINES:**
        - Small portion: 0.05 - 0.1 kg (50-100g)
        - Medium portion: 0.1 - 0.2 kg (100-200g)
        - Large portion: 0.2 - 0.4 kg (200-400g)
        - Use visual references (plate size, utensil comparison) to estimate
        - For liquids, estimate volume then convert (1L water ≈ 1kg)

        **CONFIDENCE SCORING:**
        - 0.9 - 1.0: Clear image, definite identification
        - 0.7 - 0.9: Good visibility, high certainty
        - 0.5 - 0.7: Partial visibility or some ambiguity
        - 0.3 - 0.5: Poor visibility, educated guess
        - 0.0 - 0.3: Very uncertain, likely incorrect

        **RESPONSE FORMAT:**
        Return ONLY valid JSON. No markdown, no explanations, no comments.

        {
          "mealName": "string (descriptive name for the overall meal)",
          "analysisTimestamp": "ISO 8601 timestamp",
          "detectedItems": [
            {
              "originalLabel": "string (what you see in the image)",
              "canonicalName": "string (MUST match database key exactly)",
              "portionKg": number (decimal, e.g., 0.15 for 150g),
              "confidence": number (0.0 to 1.0),
              "visualNotes": "string (brief description of what helped identification)"
            }
          ],
          "overallConfidence": number (0.0 to 1.0),
          "imageQuality": "excellent | good | fair | poor",
          "warnings": ["array of strings for any issues or uncertainties"],
          "unidentifiedItems": ["array of items seen but not matched to database"]
        }

        **SPECIAL CASES:**
        - Mixed dishes (e.g., curry, stew): Break down into visible components
        - Sauces/condiments: Include if substantial, otherwise note in warnings
        - Garnishes: Only include if they contribute significant mass
        - Processed foods: Match to closest base ingredient

        **IF NO FOOD DETECTED:**
        {
          "mealName": "No meal detected",
          "analysisTimestamp": "ISO 8601 timestamp",
          "detectedItems": [],
          "overallConfidence": 0.0,
          "imageQuality": "string",
          "warnings": ["Reason why no food was detected"],
          "unidentifiedItems": []
        }
      """;

    /**
     * Creates a new ImageAnalysis instance.
     * 
     * @param apiKey The Gemini API key for authentication
     */
    public ImageAnalysis(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Analyzes a food image from a file path and returns JSON with detected items.
     * 
     * @param imagePath Path to the image file
     * @return JSON string with detected food items, portions, and confidence scores
     * @throws IOException If the image cannot be read or the API request fails
     */
    public String analyzeImage(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        String mimeType = Files.probeContentType(Paths.get(imagePath));
        if (mimeType == null) mimeType = "image/jpeg"; // Default fallback
        
        return analyzeImage(imageBytes, mimeType);
    }
    
    /**
     * Analyzes a food image from a byte array definition and returns JSON.
     * 
     * @param imageBytes The raw image data in bytes
     * @param mimeType The MIME type of the image (e.g., "image/jpeg", "image/png")
     * @return JSON string with detected food items, portions, and confidence scores
     * @throws IOException If the API request fails
     */
    public String analyzeImage(byte[] imageBytes, String mimeType) throws IOException {
        // Encode the image to Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Build the JSON request
        String jsonRequest = String.format("""
            {
              "contents": [{
                "parts": [
                  {"text": %s},
                  {
                    "inline_data": {
                      "mime_type": "%s",
                      "data": "%s"
                    }
                  }
                ]
              }]
            }
            """,
            escapeJson(SYSTEM_PROMPT),
            mimeType,
            base64Image
        );

        // Create and send HTTP request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GEMINI_API_URL + "?key=" + apiKey))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
            .build();
        
        try {
            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            String responseBody = response.body();

            if (response.statusCode() != 200) {
                throw new IOException("API Error: " + responseBody);
            }

            // Extract JSON from response
            int jsonStart = responseBody.indexOf("```json\\n") + 9;
            int jsonEnd = responseBody.indexOf("\\n```", jsonStart);

            if (jsonStart == 8 || jsonEnd == -1) {
                throw new IOException("Could not find JSON markers in response");
            }

            String cleanJson = responseBody.substring(jsonStart, jsonEnd);

            // Unescape JSON characters
            cleanJson = cleanJson
                .replace("\\n", "\n")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");

            return cleanJson.trim();

        } catch (InterruptedException e) {
            throw new IOException("Request interrupted", e);
        }
    }
    
    /**
     * Escapes special characters in a string for JSON inclusion.
     * 
     * @param text The text to escape
     * @return The escaped text wrapped in quotes
     */
    private String escapeJson(String text) {
        return "\"" + text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
            + "\"";
    }
}
