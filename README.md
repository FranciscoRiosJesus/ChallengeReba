# Challenge Reba

## Setting up the local environment

### Prerequisites
- Java Development Kit (JDK) 17 
- Gradle 6.3 or higher
- Git
- To run the performance test, you need to have Gatling installed on your machine.
- To run the `persons.http` file, you need an IDE such as IntelliJ or VS Code.

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
4. Run the application locally:
  ```bash
  java -jar build/libs/person-0.0.1-SNAPSHOT.war
  ```
5. The application will be available at http://localhost:8080. You can test the endpoints by using the requests defined in the [persons.http](/persons.http) file. This file contains sample HTTP request that you can use to interact with the application's endpoints. 

## Running Tests

* You can run the unit tests using the following command:
  ```bash
  gradle test
  ```

## Testing EC2 Instance

To test the EC2 instance, you can use the `persons.http` file.

## Performance Testing

The performance testing code using Gatling is located in `PerformanceSimulation.java`. Follow these steps to run it:
1. Make sure you have Gatling installed on your machine.
2. Locate the `PerformanceSimulation.java` file.
3. Run the Gatling performance test by executing the `PerformanceSimulation` class.
4. Analyze the performance results.

Alternatively, you can also run the performance test using Gatling's online platform.

For more information and documentation on Gatling, you can visit their website at [Gatling Documentation](https://gatling.io/docs/current/).

## Populating the Database
You can populate the database by running the following endpoints:
- `persons/SEED`: Use this endpoint to populate the database with person data.
- `relations/SEED`: Use this endpoint to populate the database with relationship data.