# trippingo - Travel Planner

This is the Spring Boot application for the NUS-ISS MTech course Intelligent Reasoning Systems which exposes a variety of APIs for travel recommendations, itinerary optimisation and retrieving information about Tourist attractions, promotion bundles and travel distances in Singapore. 


This repository has a linked repository for the web interface of this application at [trippingo-web](https://github.com/vidur6789/trippingo-web)

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
 
 
