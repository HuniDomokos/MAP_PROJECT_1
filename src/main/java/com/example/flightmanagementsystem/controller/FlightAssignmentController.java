package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.FlightAssignment;
import com.example.flightmanagementsystem.service.FlightAssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/flight-assignments")
public class FlightAssignmentController {
    private final FlightAssignmentService assignmentService;

    public FlightAssignmentController(FlightAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    public record AssignmentRequest(String flightId, String staffId) {}

    @GetMapping
    public List<FlightAssignment> getAll() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/flight/{flightId}")
    public List<FlightAssignment> getByFlight(@PathVariable("flightId") String flightId) {
        return assignmentService.getAssignmentsByFlight(flightId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightAssignment assign(@RequestBody AssignmentRequest request) {
        return assignmentService.assignStaff(request.flightId(), request.staffId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") String id) {
        assignmentService.removeAssignment(id);
    }
}
