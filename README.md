# Flight Management System (Projektaufgabe 1)

## General Information

* **Topic:** Theme 2 - Flight Management System (Spring Boot, In-Memory)

* **Course:** Advanced Programming Methods (MAP / Labor)

* **Team Members:** Hunor-Tas Domokos, Vilmos Orbán

## Architecture & Layered Organization

This project follows a strict **Layered Architecture** and adheres to clean code conventions and **SOLID** principles:

1. **`model` Layer:**

    * Contains the core entities based on the UML diagram (`Flight`, `Airplane`, `Passenger`, `Ticket`, `Luggage`, `NoticeBoard`, `FlightAssignment`, and the `Staff` hierarchy including `AirlineEmployee` and `AirportEmployee`).

    * Implements OOP principles: private attributes, constructors, getters, and setters.

    * Date and time attributes use modern Java API (`LocalDate`, `LocalTime`) instead of raw strings.

2. **`repository` Layer (In-Memory):**

    * Implements a generic `CrudRepository<T, ID>` interface to avoid code duplication (**DRY principle**).

    * Uses Java Collections (`HashMap`) for runtime in-memory storage (`save`, `findById`, `findAll`, `deleteById`). No database or JPA is used in this phase.

3. **`service` Layer:**

    * Handles the business logic for each entity, ensuring the **Single Responsibility Principle (SRP)**.

    * Interfaces and implementations are cleanly separated, relying on dependency injection.

4. **`controller` Layer:**

    * Exposes REST/Web endpoints using `@Controller` to handle user requests and connect the application logic.

## Design Choices & Implementation Details

* **In-Memory Storage:** To prepare for future phases, all repositories simulate database operations using thread-safe or standard Java maps (`Map<String, T>`).

* **Polymorphism & Inheritance:** The `Staff` abstract class handles common attributes (`id`, `name`), while specialized employee roles (`AirlineEmployee`, `AirportEmployee`) extend it, satisfying the Liskov Substitution Principle.

* **Error Handling:** A `GlobalExceptionHandler` ensures consistent error responses across controllers.

## Running the Application

1. Ensure you have **JDK 17** or newer installed.

2. Clone the repository and open it in **IntelliJ IDEA (Community or Ultimate)**.

3. Run the `FlightManagementSystemApplication.java` main class.

4. Access test endpoints (e.g., `/hello`) to verify that the application is running correctly.