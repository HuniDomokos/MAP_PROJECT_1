package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class FlightService {
    private final CrudRepository<Flight, String> flightRepository;
    private final CrudRepository<Airplane, String> airplaneRepository;
    private final CrudRepository<NoticeBoard, String> noticeBoardRepository;

    public FlightService(CrudRepository<Flight, String> flightRepository,
                             CrudRepository<Airplane, String> airplaneRepository,
                             CrudRepository<NoticeBoard, String> noticeBoardRepository) {
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public Flight createFlight(String name, String airplaneId, String noticeBoardId, LocalDateTime departureTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time must not be null");
        }
        Airplane airplane = airplaneRepository.findById(airplaneId)
            .orElseThrow(() -> new IllegalArgumentException("Airplane not found: " + airplaneId));
        NoticeBoard board = noticeBoardRepository.findById(noticeBoardId)
            .orElseThrow(() -> new IllegalArgumentException("Notice board not found: " + noticeBoardId));

        if (!departureTime.toLocalDate().equals(board.getDate())) {
            throw new IllegalArgumentException("Departure date must match the notice board date");
        }
        Flight flight = new Flight(UUID.randomUUID().toString(), name, noticeBoardId, airplaneId, departureTime);
        flightRepository.save(flight);
        airplane.addFlight(flight);
        board.addFlight(flight);
        return flight;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + id));
    }

    public Flight updateFlight(String id, String name, String airplaneId, String noticeBoardId,
                               LocalDateTime departureTime) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time must not be null");
        }
        Flight flight = getFlightById(id);
        Airplane airplane = airplaneRepository.findById(airplaneId)
            .orElseThrow(() -> new IllegalArgumentException("Airplane not found: " + airplaneId));
        NoticeBoard board = noticeBoardRepository.findById(noticeBoardId)
            .orElseThrow(() -> new IllegalArgumentException("Notice board not found: " + noticeBoardId));

        if (!departureTime.toLocalDate().equals(board.getDate())) {
            throw new IllegalArgumentException("Departure date must match the notice board date");
        }
        if (flight.getTickets().size() > airplane.getCapacity()) {
            throw new IllegalStateException("Airplane is too small for the tickets already booked on this flight");
        }

        // keep the in-memory relation lists in sync when the references change
        if (!flight.getAirplaneId().equals(airplaneId)) {
            airplaneRepository.findById(flight.getAirplaneId()).ifPresent(a -> a.removeFlight(flight));
            airplane.addFlight(flight);
        }
        if (!flight.getNoticeBoardId().equals(noticeBoardId)) {
            noticeBoardRepository.findById(flight.getNoticeBoardId()).ifPresent(b -> b.removeFlight(flight));
            board.addFlight(flight);
        }
        flight.setName(name);
        flight.setAirplaneId(airplaneId);
        flight.setNoticeBoardId(noticeBoardId);
        flight.setDepartureTime(departureTime);
        return flightRepository.save(flight);
    }

    public void deleteFlight(String id) {
        Flight flight = getFlightById(id);
        if (!flight.getTickets().isEmpty() || !flight.getFlightAssignments().isEmpty()) {
            throw new IllegalStateException("Flight still has tickets or staff assignments");
        }
        airplaneRepository.findById(flight.getAirplaneId()).ifPresent(a -> a.removeFlight(flight));
        noticeBoardRepository.findById(flight.getNoticeBoardId()).ifPresent(b -> b.removeFlight(flight));
        flightRepository.deleteById(id);
    }
}
