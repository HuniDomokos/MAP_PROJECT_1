package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Flight;
import com.example.flightmanagementsystem.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public record FlightRequest(String name, String airplaneId, String noticeBoardId,
                                LocalDateTime departureTime) {}

    @GetMapping
    public List<Flight> getAll() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getById(@PathVariable("id") String id) {
        return flightService.getFlightById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flight create(@RequestBody FlightRequest request) {
        return flightService.createFlight(request.name(), request.airplaneId(),
                request.noticeBoardId(), request.departureTime());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        flightService.deleteFlight(id);
    }
}
