# Flight Management System (Projektaufgabe 1 & 2)

## General Information

* **Topic:** Theme 2 - Flight Management System (Spring Boot, In-Memory)
* **Course:** Advanced Programming Methods (MAP / Labor)
* **Team Members:** Hunor-Tas Domokos, Vilmos Orbán

---

## Architecture & Layered Organization

The application strictly follows the **Layered Architecture** and **MVC (Model-View-Controller)** pattern, respecting clean code principles and **SOLID** guidelines:

```text
[ Client / Browser ]
         │  HTTP (GET, POST)
         ▼
  1. Controller Layer       (@Controller)
         │  Delegates requests
         ▼
  2. Service Layer          (@Service)
         │  Enforces business rules
         ▼
  3. Repository Layer       (Generic CrudRepository<T, ID>)
         │  In-Memory Storage (ConcurrentHashMap)
         ▼
  4. Model Layer            (Entities & OOP Hierarchy)
```

1. **`model` Layer:**
   * Core entities based on the domain model: `Flight`, `Airplane`, `Passenger`, `Ticket`, `Luggage`, `NoticeBoard`, and `FlightAssignment`.
   * Class hierarchy for employees: Abstract `Staff` base class extended by `AirlineEmployee` and `AirportEmployee` (Liskov Substitution Principle).
   * Safe typing with enums: `Luggage.Status` (`CHECKED_IN`, `LOADED`, `DELIVERED`) prevents typographical errors and controls state transitions.
   * Modern Java Date/Time API (`LocalDate`, `LocalDateTime`).

2. **`repository` Layer (Refactored In-Memory Storage):**
   * **Generic InMemory Implementation:** Eliminated CRUD code duplication across repositories by introducing a generic `InMemoryRepository<T, ID>` implementing `CrudRepository<T, ID>`.
   * Thread-safe in-memory data store powered by `ConcurrentHashMap` and ID-extractor functions.
   * Centralized bean registration in `RepositoryConfig`.

3. **`service` Layer (Business Logic):**
   * Centralizes all domain business logic, validation, and consistency checks.
   * Relies on dependency injection via `CrudRepository` abstractions.
   * **Referential Integrity on Deletion:** `StaffService.deleteStaff()` cross-checks the `FlightAssignmentRepository` before removing any staff member (both airline and airport personnel) to prevent orphaned assignments.

4. **`controller` Layer & Thymeleaf Web Views (Project 2):**
   * Spring MVC web controllers (`@Controller`) exposing standard CRUD endpoints.
   * Clean separation between web actions and business logic.

---

## Web Interface (Projektaufgabe 2)

The presentation layer is implemented with **Thymeleaf** templates located under `src/main/resources/templates/`.

### Implemented Entities & Operations
For each of the 8 main entities, full Create, Read, and Delete (CRUD) operations are supported:
* **Airplanes:** `/airplanes`
* **Flights:** `/flights`
* **Passengers:** `/passengers`
* **Tickets:** `/tickets`
* **Luggage:** `/luggage`
* **Staff:** `/staff`
* **Notice Boards:** `/notice-boards`
* **Flight Assignments:** `/flight-assignments`

### URL Mapping Pattern
Each entity follows standard conventions:
* `GET /<entity>` — Renders the list table (`index.html`) with individual delete buttons.
* `GET /<entity>/new` — Renders the creation form (`form.html`).
* `POST /<entity>` — Submits the form data and persists the entity.
* `POST /<entity>/{id}/delete` — Triggers safe deletion via `POST` requests.

### Layout & Navigation
A shared layout fragment (`templates/fragments/layout.html`) standardizes page headers, styling, and navigation across all views.

---

## Improvements & Addressed P1 Feedback

1. **CRUD Duplication Resolved:** Eight redundant repository classes were replaced by a single generic `InMemoryRepository<T, ID>`.
2. **Staff Deletion Safety:** Staff members cannot be deleted if active flight assignments exist in `FlightAssignmentRepository`.
3. **Typing Luggage Status:** Replaced raw string status fields with an explicit `Luggage.Status` enum and transition validator.
4. **Interface Cleanup:** Streamlined service classes to maintain direct clarity without redundant interface boilerplate.

---

## Running the Application

1. Requires **JDK 17** (or newer).
2. Clone the repository and open it in **IntelliJ IDEA**.
3. Run `FlightManagementSystemApplication.java`.
4. Open your browser and navigate to:
   ```text
   http://localhost:8080/airplanes
   ```
   Use the top navigation bar to explore and test all entities.
