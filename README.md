# trippingo - Travel Planner

This is the Spring Boot application for the NUS-ISS MTech course Intelligent Reasoning Systems which exposes a variety of APIs for travel recommendations, itinerary optimisation and retrieving information about Tourist attractions, promotion bundles and travel distances in Singapore. 

This repository has a linked repository for the web interface of this application at [trippingo-web](https://github.com/vidur6789/trippingo-web)

## Problem Background
In 2018, Singapore received about 18 million International Visitor Arrivals with an average stay of about 3.7 days per visit, making Singapore the 5th most visited cities in the world and 2nd in the Asia-Pacific. 
Given that the largest age group of visitors was from 25 to 34 years old at 23% of visitors, survey data has shown that most visitors within this age group are likely to have travelled based on their own itinerary. With more than 200 attractions over Singapore, significant effort is required to find attractions that fit your travel interests, preferences and context. 
Our project simplifies and eases the process of itinerary planning by filtering attractions to fit the user preferences. The system enhances these recommendations with market basket analysis on the travel history of  TripAdvisor users with similar interests. The selected attractions are then used to create an optimised travel itinerary.

## Import Project into Development Environment:
1. Download Eclipse or your preferred IDE
2. Install the corresponding Gradle plugin for your IDE(Help -> Eclipse Marketplace -> Eclipse Buildship for Eclipse). Alternatively, use the Gradle Wrapper(gradlew file in root directory) as your build tool. 
3. Import the root directory as an Existing Gradle Project. 

## Starting the Application from IDE:
 - Run the TrippingoApp.java as Java Application
 
 
## Starting the Application from Command Line using Gradle:
 - Execute 'gradlew bootRun' in root directory
 
## Starting the Application from Command Line using JAR without Source code:
  - Download jar file from latest [release](https://github.com/vidur6789/trippingo/releases/tag/v2.0) into a new project directory
  - Download datastore.mv.db into a new sub-directory 'data'
  - Navigate to project directory and execute java -jar trippingo.jar in command line
  - API will be available on 8001 port of the localhost
 
 ## Accessing the Embedded H2 Database
  - Start the application
  - Navigate to [localhost:8001]/h2 in the Browser to access the embedded database
 
 
