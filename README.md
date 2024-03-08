# Challenge Reba

## Setting up the local environment

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Gradle 6.3 or higher
- Git

### Steps to Set Up the Local Environment
1. Clone the repository from GitHub:
   ```bash
   git clone https://github.com/FranciscoRiosJesus/ChallengeReba.git
   ```
2. Navigate to the project directory:
   ```bash
   cd ChallengeReba
   ```
3. Build the project with Gradle:
   ```bash
   gradle build
   ```
4. Run up postgres container
   ```bash
   docker compose up -d
   ```
5. Run the application locally:
  ```bash
   java -jar build/libs/person-0.0.1-SNAPSHOT.war
  ```
6. The application will be available at http://localhost:8080. You can test the endpoints by using the requests defined in the [persons.http](/persons.http) file. This file contains sample HTTP request that you can use to interact with the application's endpoints. 

# Running Tests

* You can run the unit tests using the following command:
  ```bash
  gradle test
  ```
