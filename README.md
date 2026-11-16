# Flight Management System

A Spring Boot web application for managing flight operations (airplanes, flights, notice boards, passengers, tickets, luggage, staff, and flight assignments) featuring a Thymeleaf web interface and file-based JSON persistence.

## Technologies & Architecture

- **Backend:** Java, Spring Boot (Spring MVC)
- **Frontend:** Thymeleaf, HTML5, CSS3
- **Persistence:** File-based JSON storage using Jackson (`ObjectMapper` + `JavaTimeModule`)
- **Architecture:** Layered architecture (`Model` $\rightarrow$ `Repository` $\rightarrow$ `Service` $\rightarrow$ `Controller` $\rightarrow$ `View`)

---

## Projektaufgabe 3 Implementation

### 1. JSON Persistence (`InFileRepository<T, ID>`)
- **Generic Repository:** `InFileRepository<T, ID>` implements `CrudRepository<T, ID>` and handles all JSON serialization and deserialization via Jackson.
- **Entity-Specific Repositories:** All eight entities (`Airplane`, `Flight`, `NoticeBoard`, `Passenger`, `Ticket`, `Luggage`, `Staff`, `FlightAssignment`) have dedicated repository classes extending `InFileRepository` and passing their respective file paths.
- **Polymorphic Serialization:** The `Staff` inheritance hierarchy (`AirlineEmployee` and `AirportEmployee`) is handled using `@JsonTypeInfo` and `@JsonSubTypes`.
- **Initial Datasets:** Located in `src/main/resources/data/`, each entity has its own JSON file (`airplanes.json`, `flights.json`, `noticeboards.json`, `passengers.json`, `tickets.json`, `luggage.json`, `staff.json`, `flightassignments.json`) pre-populated with at least 10 valid records.
- **Relation Linking (`RelationLinker`):** On startup, `RelationLinker` reconstructs object relationships across loaded entities using stored IDs (e.g., flights per airplane, tickets per passenger/flight, luggage per ticket), while `@JsonIgnore` prevents circular references during serialization.

### 2. Full Web CRUD Functionality
All CRUD operations are supported via the web interface for every entity:
- **List (Read All):** `GET /<entity>` – Displays all records in a table with action buttons.
- **Details (Read One):** `GET /<entity>/{id}` – Displays all fields of a single record along with its linked sub-entities.
- **Create:** `GET /<entity>/new` and `POST /<entity>` – Form for creating new records.
- **Update (Edit):** `GET /<entity>/{id}/edit` and `POST /<entity>/{id}` – Pre-populated form for editing existing records with immediate persistence to the corresponding JSON file.
- **Delete:** `POST /<entity>/{id}/delete` – Removes records (with dependency validation) and updates the JSON file.

---

## How to Run

1. Clone the repository and navigate to the project root directory.
2. Start the application via Maven or your IDE (`FlightManagementSystemApplication`):
   ```bash
   ./mvnw spring-boot:run
3. Open your browser at: http://localhost:8080/airplanes