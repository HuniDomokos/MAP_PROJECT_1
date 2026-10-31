package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class FlightAssignmentServiceImpl implements FlightAssignmentService {
    private final FlightAssignmentRepository assignmentRepository;
    private final FlightRepository flightRepository;
    private final StaffRepository staffRepository;

    public FlightAssignmentServiceImpl(FlightAssignmentRepository assignmentRepository,
                                       FlightRepository flightRepository,
                                       StaffRepository staffRepository) {
        this.assignmentRepository = assignmentRepository;
        this.flightRepository = flightRepository;
        this.staffRepository = staffRepository;
    }

    @Override
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

    @Override
    public List<FlightAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public List<FlightAssignment> getAssignmentsByFlight(String flightId) {
        return assignmentRepository.findAll().stream()
            .filter(a -> a.getFlightId().equals(flightId))
            .toList();
    }

    @Override
    public void removeAssignment(String assignmentId) {
        FlightAssignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new IllegalArgumentException("Assignment not found: " + assignmentId));
        flightRepository.findById(assignment.getFlightId()).ifPresent(f -> f.removeFlightAssignment(assignment));
        staffRepository.findById(assignment.getStaffId()).ifPresent(s -> {
            if (s instanceof AirlineEmployee employee) {
                employee.removeAssignment(assignment);
            }
        });
        assignmentRepository.deleteById(assignmentId);
    }
}
