package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.FlightAssignment;

public class FlightAssignmentRepository extends InFileRepository<FlightAssignment, String> {
    public FlightAssignmentRepository() {
        super("src/main/resources/data/flightassignments.json", FlightAssignment.class, FlightAssignment::getId);
    }
}