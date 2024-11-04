# IMRequest-Back
Backend of a simple Inventory System for a tools warehouse

# Tech Stack
- Java Spring Boot 3
- Java Spring Security
- JWT
- MongoDB
- Docker

# Setup
IntelliJ with JDK 22 and lombok plugin is recommended, Community edition can be downloaded here https://youtrack.jetbrains.com/articles/IDEA-A-2100661679/IntelliJ-IDEA-2023.3-Latest-Builds#download-intellij-idea-community-edition-20233-eap-1

Once installed, create a configuration defining the app entry point (com.IMRequest.IMRequest.IMRequestApplication) and the environment variables needed

## Environment variables needed:
- MONGODB_URI: The connection string of the Mongo Db cluster
- MONGODB_DATABASE: The name of the cluster
