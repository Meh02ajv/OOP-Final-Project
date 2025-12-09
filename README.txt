Project Overview

Our project focuses on estimating the environmental impact of meals, especially West African meals, based on the food items the user builds to form a meal. This system is a web-based architecture that allows a user to take a picture of their meal, and the system we built identifies the food items and proportions using a recognition module. After identifying the food items, the system calculates the food items' impact using environmental metrics such as carbon footprint per kilogram, water usage, and land usage. Then it combines the individual food items' contributions to calculate the total impact of the meal. The user can also compare the impact of different food choices and make a decision on which food to consume. 

​Key Features

•	GUI (Frontend): what is displayed for the users

•	Backend API: what connects the frontend(GUI) with the backend (Java logic). 

•	Image Recognition Module (Gemini Generative API): external service used to identify food items in the image. 

•	Database: where the data about the food items is stored. 

​🛠️ Technology Stack 

- Language: Java (The core language for building the OOP model and logic).
- HTML: to build the structure of the webpage.
- CSS: to desgin and enhance the webpage's appearance.
- PHP: to handle the backend contents
- GitHub: for collaboration and code management


How to Compile
- Write the following code in the terminal
cd src
javac -d bin *.java

How to Run
- Write the following code in the terminal
 java -cp bin WebServer

- Open the application on an web browser on:
http://localhost:8080

How to use the Application:
   -Upload a food image.
   - Click "Analyze Impact".
   - View detailed environmental data on your meal.

Dependencies
- Gemini Generative API access key 


Assumptions and Limitations
- Internet access is required for the application to work
- The application uses a free tier API key for Google Gemini, so the application might not work due to exhausted quota.
- If a food item is not in our specific database, the system attempts to find the closest match or ignores it

​Contributors

. Miriam Wepiya Gale

. Haris Tiyumtaba Issah

. Nana Banyin Kwofie

. Sebenemaryam Ashebir Asnake

. Meh Ayite Hariel Elinam Ajavon

