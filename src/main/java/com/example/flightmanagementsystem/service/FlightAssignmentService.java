package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.FlightAssignment;
import java.util.List;

public interface FlightAssignmentService {
    FlightAssignment assignStaff(String flightId, String staffId);
    List<FlightAssignment> getAllAssignments();
    List<FlightAssignment> getAssignmentsByFlight(String flightId);
    void removeAssignment(String assignmentId);
}
