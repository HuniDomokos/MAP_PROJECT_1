package com.example.flightmanagementsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AirlineEmployee extends Staff {
    private String role;
    private final List<FlightAssignment> assignments = new ArrayList<>();

    public AirlineEmployee(String id, String name, String role) {
        super(id, name);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<FlightAssignment> getAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    public void addAssignment(FlightAssignment assignment) {
        assignments.add(assignment);
    }

    public void removeAssignment(FlightAssignment assignment) {
        assignments.remove(assignment);
    }
}
