
# FluxMono Project Overview

## Introduction

**FluxMono** is a Java-based web application that demonstrates the use of Spring WebFlux and GraphQL to consume and expose data from a public REST API. Specifically, it interfaces with the [Bored API](https://www.boredapi.com/api/activity), which provides random activities (e.g., "read a book"). The application retrieves activity data using a reactive, non-blocking web client and then serves this data through GraphQL endpoints.

## Repository Metadata
- **Owner:** [ajaydinakar](https://github.com/ajaydinakar)
- **Primary Language:** Java
- **Created:** March 31, 2023
- **Contributors:** Main contributor is ajaydinakar

## Features

- **Reactive API Consumption:** Utilizes Spring WebFlux's `WebClient` to asynchronously fetch data from the Bored API.
- **GraphQL Endpoints:** Exposes activity data as GraphQL queries for flexible client consumption.
- **REST Controller:** REST endpoints are also available, providing multiple ways to access the activity data.
- **Service Testing:** Comprehensive unit tests using JUnit and Mockito, including mock web server responses.
- **Error Handling & Logging:** Handles server/client errors reactively, with logging for easier debugging.
- **Domain Model:** Uses a simple `Activity` data class for mapping API responses.

## Repository Structure

```
fluxmono/
├── src/
│   ├── main/
│   │   ├── java/com/ajay/fluxmono/
│   │   │   ├── controller/         # Controllers exposing endpoints
│   │   │   ├── model/              # Domain models (e.g., Activity.java)
│   │   │   ├── service/            # Business logic (services for fetching and processing activities)
│   │   │   ├── util/               # Utility classes for testing
│   │   │   └── FluxmonoApplication.java # Spring Boot entry point
│   ├── test/
│   │   ├── java/com/ajay/fluxmono/
│   │   │   ├── service/            # JUnit/Mockito tests for services
│   │   │   └── FluxmonoApplicationTests.java # Spring Boot test
├── README.md                       # Project overview and setup (brief)
```

### Key Files

- `FluxmonoApplication.java`: Entry point for the Spring Boot application.
- `controller/PingController.java`: REST and GraphQL endpoints for activity retrieval.
- `service/PingService.java`: Core logic for consuming the Bored API using `WebClient`.
- `service/PingBuilderService.java`: Alternative service implementation using `WebClient.Builder` with enhanced error handling.
- `model/Activity.java`: Data model representing an activity from the Bored API.
- `test/service/PingServiceTest.java`: Unit tests for the `PingService`.
- `test/service/PingBuilderServiceTest.java`: Unit tests for the `PingBuilderService`.
- `util/CustomMinimalForTestResponseSpec.java`: Utility class for response mocking in tests.

## Main Components

### 1. Activity Model

Defines the structure of the activity data:

```java
public class Activity {
    String activity;
    String type;
    String participants;
    int price;
    String link;
    String key;
    Double accessibility;
}
```

### 2. PingService & PingBuilderService

- **PingService:** Basic service class that uses a `WebClient` to fetch a single activity from the Bored API, both reactively (`Mono<Activity>`) and in a blocking fashion (`Activity`).
- **PingBuilderService:** More advanced service using `WebClient.Builder` to support advanced configuration, error handling, and retry logic. Offers both single (`Mono<Activity>`) and multiple (`Mono<List<Activity>>`) activity retrieval methods.

### 3. PingController

Exposes REST and GraphQL endpoints:
- `getActivity()`: Returns a string description of a random activity.
- `getActivityMonoGraph()`: Returns an `Activity` object as a GraphQL response.

### 4. Testing

- Uses JUnit and Mockito for unit testing.
- Employs a mock web server to simulate API responses and test error scenarios.
- Custom utility classes for minimal response mocking.

## Contributions & Activity

- **Primary Contributor:** [ajaydinakar](https://github.com/ajaydinakar) with all commits (as of the last update).
- **Development Status:** Last code push was July 2023. No open issues or pull requests at this time. No external contributors listed.

## Usage and Setup

To run the project:
1. Clone the repository:
   ```
   git clone https://github.com/ajaydinakar/fluxmono.git
   ```
2. Ensure Java and Maven are installed.
3. Configure the `baseurl` property (typically in `application.properties`) to point to the Bored API.
4. Run the application:
   ```
   mvn spring-boot:run
   ```
5. Access the REST or GraphQL endpoints as documented in the controller.

## Conclusion

FluxMono is a demonstration of integrating Spring WebFlux and GraphQL with an external public API, featuring robust error handling, test coverage, and a clear modular structure. It serves as a useful template for building reactive, Java-based API consumers with modern endpoint exposure.

