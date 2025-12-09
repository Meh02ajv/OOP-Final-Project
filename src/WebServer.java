import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;

/**
 * A simple HTTP server that provides a REST API for food image analysis.
 * 
 * <p>Endpoints:</p>
 * <ul>
 *   <li>POST /analyze-image - Analyzes a food image and returns JSON</li>
 *   <li>GET / - Serves static files (HTML, CSS, JS)</li>
 * </ul>
 * 
 * @author Environmental Impact Calculator Team
 * @version 1.0
 * @see ImageAnalysis
 * @see EnvironmentalImpactCalculator
 */
public class WebServer {
    
    /** The underlying HTTP server */
    private HttpServer server;
    
    /** Calculator instance for processing requests */
    private EnvironmentalImpactCalculator calculator;
    
    /**
     * Creates and configures a new web server.
     * 
     * @param port The port number to listen on
     * @throws IOException If the server cannot start or database cannot load
     */
    public WebServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Load the food database
        calculator = new EnvironmentalImpactCalculator();
        calculator.loadFromFile("Environmental impacts of food (Clark et al. 2022) copy.csv");
        
        // Register endpoint handlers
        server.createContext("/analyze-image", new ImageAnalysisHandler());
        server.createContext("/", new StaticFileHandler("../web"));
        
        server.setExecutor(null);
    }
    
    /**
     * Starts the server and begins listening for requests.
     */
    public void start() {
        server.start();
        System.out.println("Server running on http://localhost:" + server.getAddress().getPort());
    }
    
    /**
     * Handles HTTP requests to the /analyze-image endpoint.
     * Accepts POST requests with an image path and returns meal analysis as JSON.
     */
    class ImageAnalysisHandler implements HttpHandler {
        
        /**
         * Processes an incoming HTTP request.
         * 
         * @param exchange The HTTP request/response exchange
         * @throws IOException If there's a problem processing the request
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Only allow POST requests
            if (!"POST".equals(exchange.getRequestMethod())) {
                String response = "Method not allowed";
                exchange.sendResponseHeaders(405, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
                return;
            }
            
            // Read the request body (image path)
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody())
            );
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String imagePath = sb.toString().trim();
            
            try {
                // Analyze the image and get meal data
                Meal meal = calculator.createMealFromImage(imagePath);
                String jsonResponse = meal.toJson();
                
                // Send successful response
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonResponse.length());
                
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
                
            } catch (Exception e) {
                // Send error response
                String error = "{\"error\": \"" + e.getMessage() + "\"}";
                exchange.sendResponseHeaders(500, error.length());
                exchange.getResponseBody().write(error.getBytes());
            }
            
            exchange.close();
        }
    }

    /**
     * Serves static files (HTML, CSS, JavaScript) from a directory.
     */
    class StaticFileHandler implements HttpHandler {
        
        /** Root directory for static files */
        private String webRoot;
        
        /**
         * Creates a handler for serving files from the specified directory.
         * 
         * @param webRoot Path to the static files directory
         */
        public StaticFileHandler(String webRoot) {
            this.webRoot = webRoot;
        }
        
        /**
         * Handles a request for a static file.
         * 
         * @param exchange The HTTP request/response exchange
         * @throws IOException If there's a problem reading the file
         */
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/index.html";
            
            File file = new File(webRoot + path);
            if (file.exists()) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            } else {
                exchange.sendResponseHeaders(404, 0);
            }
            exchange.close();
        }
    }
    
    /**
     * Entry point for the web server application.
     * 
     * @param args Command line arguments (not used)
     * @throws IOException If the server cannot start
     */
    public static void main(String[] args) throws IOException {
        WebServer webServer = new WebServer(8080);
        webServer.start();
    }
}