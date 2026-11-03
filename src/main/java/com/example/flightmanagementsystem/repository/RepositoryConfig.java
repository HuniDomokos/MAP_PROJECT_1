package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public CrudRepository<Airplane, String> airplaneRepository() {
        return new InMemoryRepository<>(Airplane::getId);
    }

    @Bean
    public CrudRepository<Passenger, String> passengerRepository() {
        return new InMemoryRepository<>(Passenger::getId);
    }

    @Bean
    public CrudRepository<NoticeBoard, String> noticeBoardRepository() {
        return new InMemoryRepository<>(NoticeBoard::getId);
    }

    @Bean
    public CrudRepository<Flight, String> flightRepository() {
        return new InMemoryRepository<>(Flight::getId);
    }

    @Bean
    public CrudRepository<Ticket, String> ticketRepository() {
        return new InMemoryRepository<>(Ticket::getId);
    }

    @Bean
    public CrudRepository<Luggage, String> luggageRepository() {
        return new InMemoryRepository<>(Luggage::getId);
    }

    @Bean
    public CrudRepository<Staff, String> staffRepository() {
        return new InMemoryRepository<>(Staff::getId);
    }

    @Bean
    public CrudRepository<FlightAssignment, String> flightAssignmentRepository() {
        return new InMemoryRepository<>(FlightAssignment::getId);
    }
}
