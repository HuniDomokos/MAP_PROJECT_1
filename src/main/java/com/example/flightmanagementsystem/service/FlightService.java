package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Flight;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    Flight createFlight(String name, String airplaneId, String noticeBoardId, LocalDateTime departureTime);
    List<Flight> getAllFlights();
    Flight getFlightById(String id);
    void deleteFlight(String id);
}
