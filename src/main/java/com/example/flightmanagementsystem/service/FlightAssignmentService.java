package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class FlightAssignmentService {
    private final CrudRepository<FlightAssignment, String> assignmentRepository;
    private final CrudRepository<Flight, String> flightRepository;
    private final CrudRepository<Staff, String> staffRepository;

    public FlightAssignmentService(CrudRepository<FlightAssignment, String> assignmentRepository,
                                       CrudRepository<Flight, String> flightRepository,
                                       CrudRepository<Staff, String> staffRepository) {
        this.assignmentRepository = assignmentRepository;
        this.flightRepository = flightRepository;
        this.staffRepository = staffRepository;
    }

    public FlightAssignment assignStaff(String flightId, String staffId) {
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
        Staff staff = staffRepository.findById(staffId)
            .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + staffId));

        boolean exists = getAssignmentsByFlight(flightId).stream()
            .anyMatch(a -> a.getStaffId().equals(staffId));
        if (exists) {
            throw new IllegalStateException("Staff already assigned to this flight");
        }
        FlightAssignment assignment = new FlightAssignment(UUID.randomUUID().toString(), flightId, staffId);
        assignmentRepository.save(assignment);
        flight.addFlightAssignment(assignment);
        if (staff instanceof AirlineEmployee employee) {
            employee.addAssignment(assignment);
        }
        return assignment;
    }

    public FlightAssignment updateAssignment(String id, String flightId, String staffId) {
        FlightAssignment assignment = getAssignmentById(id);
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
        Staff staff = staffRepository.findById(staffId)
            .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + staffId));

        // the assignment itself does not count as a duplicate
        boolean exists = getAssignmentsByFlight(flightId).stream()
            .anyMatch(a -> a.getStaffId().equals(staffId) && !a.getId().equals(id));
        if (exists) {
            throw new IllegalStateException("Staff already assigned to this flight");
        }

        // take the assignment out of the old lists, then put it into the new ones
        flightRepository.findById(assignment.getFlightId())
            .ifPresent(f -> f.removeFlightAssignment(assignment));
        staffRepository.findById(assignment.getStaffId()).ifPresent(s -> {
            if (s instanceof AirlineEmployee employee) {
                employee.removeAssignment(assignment);
            }
        });
        assignment.setFlightId(flightId);
        assignment.setStaffId(staffId);
        flight.addFlightAssignment(assignment);
        if (staff instanceof AirlineEmployee employee) {
            employee.addAssignment(assignment);
        }
        return assignmentRepository.save(assignment);
    }

    public FlightAssignment getAssignmentById(String id) {
        return assignmentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + id));
    }

    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<FlightAssignment> getAssignmentsByFlight(String flightId) {
        return assignmentRepository.findAll().stream()
            .filter(a -> a.getFlightId().equals(flightId))
            .toList();
    }

    public void removeAssignment(String assignmentId) {
        FlightAssignment assignment = assignmentRepository.findById(assignmentId)
            .orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + assignmentId));
        flightRepository.findById(assignment.getFlightId())
            .ifPresent(f -> f.removeFlightAssignment(assignment));
        staffRepository.findById(assignment.getStaffId()).ifPresent(s -> {
            if (s instanceof AirlineEmployee employee) {
                employee.removeAssignment(assignment);
            }
        });
        assignmentRepository.deleteById(assignmentId);
    }
}
