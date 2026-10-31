package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final NoticeBoardRepository noticeBoardRepository;

    public FlightServiceImpl(FlightRepository flightRepository,
                             AirplaneRepository airplaneRepository,
                             NoticeBoardRepository noticeBoardRepository) {
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.noticeBoardRepository = noticeBoardRepository;
    }

    @Override
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

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getFlightById(String id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + id));
    }

    @Override
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
