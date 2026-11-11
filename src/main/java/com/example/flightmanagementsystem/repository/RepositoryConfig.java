package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public CrudRepository<Airplane, String> airplaneRepository() {
        return new AirplaneRepository();
    }

    @Bean
    public CrudRepository<Passenger, String> passengerRepository() {
        return new PassengerRepository();
    }

    @Bean
    public CrudRepository<NoticeBoard, String> noticeBoardRepository() {
        return new NoticeBoardRepository();
    }

    @Bean
    public CrudRepository<Flight, String> flightRepository() {
        return new FlightRepository();
    }

    @Bean
    public CrudRepository<Ticket, String> ticketRepository() {
        return new TicketRepository();
    }

    @Bean
    public CrudRepository<Luggage, String> luggageRepository() {
        return new LuggageRepository();
    }

    @Bean
    public CrudRepository<Staff, String> staffRepository() {
        return new StaffRepository();
    }

    @Bean
    public CrudRepository<FlightAssignment, String> flightAssignmentRepository() {
        return new FlightAssignmentRepository();
    }
}