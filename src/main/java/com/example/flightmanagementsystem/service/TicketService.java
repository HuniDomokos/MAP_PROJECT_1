package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class TicketService {
    private final CrudRepository<Ticket, String> ticketRepository;
    private final CrudRepository<Passenger, String> passengerRepository;
    private final CrudRepository<Flight, String> flightRepository;
    private final CrudRepository<Airplane, String> airplaneRepository;
    private final CrudRepository<Luggage, String> luggageRepository;

    public TicketService(CrudRepository<Ticket, String> ticketRepository,
                             CrudRepository<Passenger, String> passengerRepository,
                             CrudRepository<Flight, String> flightRepository,
                             CrudRepository<Airplane, String> airplaneRepository,
                             CrudRepository<Luggage, String> luggageRepository) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.luggageRepository = luggageRepository;
    }

    public Ticket bookTicket(String passengerId, String flightId, String seatNumber, double price) {
        if (seatNumber == null || seatNumber.isBlank()) {
            throw new IllegalArgumentException("Seat number must not be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        Passenger passenger = passengerRepository.findById(passengerId)
            .orElseThrow(() -> new IllegalArgumentException("Passenger not found: " + passengerId));
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
        Airplane airplane = airplaneRepository.findById(flight.getAirplaneId())
            .orElseThrow(() -> new IllegalStateException("Airplane of flight not found"));

        List<Ticket> flightTickets = getTicketsByFlight(flightId);
        if (flightTickets.size() >= airplane.getCapacity()) {
            throw new IllegalStateException("Flight is fully booked");
        }
        boolean seatTaken = flightTickets.stream()
            .anyMatch(t -> t.getSeatNumber().equals(seatNumber));
        if (seatTaken) {
            throw new IllegalStateException("Seat already taken: " + seatNumber);
        }

        Ticket ticket = new Ticket(UUID.randomUUID().toString(), passengerId, flightId, price, seatNumber);
        ticketRepository.save(ticket);
        passenger.addTicket(ticket);
        flight.addTicket(ticket);
        return ticket;
    }

    public void cancelTicket(String ticketId) {
        Ticket ticket = getTicketById(ticketId);
        passengerRepository.findById(ticket.getPassengerId()).ifPresent(p -> p.removeTicket(ticket));
        flightRepository.findById(ticket.getFlightId()).ifPresent(f -> f.removeTicket(ticket));
        ticket.getLuggages().forEach(l -> luggageRepository.deleteById(l.getId()));
        ticketRepository.deleteById(ticketId);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + id));
    }

    public List<Ticket> getTicketsByFlight(String flightId) {
        return ticketRepository.findAll().stream()
            .filter(t -> t.getFlightId().equals(flightId))
            .toList();
    }
}
