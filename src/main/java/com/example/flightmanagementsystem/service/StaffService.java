package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class StaffService {
    private final CrudRepository<Staff, String> staffRepository;
    private final CrudRepository<FlightAssignment, String> flightAssignmentRepository;

    public StaffService(CrudRepository<Staff, String> staffRepository,
                        CrudRepository<FlightAssignment, String> flightAssignmentRepository) {
        this.staffRepository = staffRepository;
        this.flightAssignmentRepository = flightAssignmentRepository;
    }

    public AirlineEmployee createAirlineEmployee(String name, String role) {
        checkNotBlank(name, "Name");
        checkNotBlank(role, "Role");
        AirlineEmployee employee = new AirlineEmployee(UUID.randomUUID().toString(), name, role);
        staffRepository.save(employee);
        return employee;
    }

    public AirportEmployee createAirportEmployee(String name, String designation, String department) {
        checkNotBlank(name, "Name");
        AirportEmployee employee = new AirportEmployee(UUID.randomUUID().toString(), name, designation, department);
        staffRepository.save(employee);
        return employee;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String id) {
        return staffRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + id));
    }

    public void deleteStaff(String id) {
        Staff staff = getStaffById(id);
        boolean assigned = flightAssignmentRepository.findAll().stream()
                .anyMatch(a -> a.getStaffId().equals(id));
        if (assigned) {
            throw new IllegalStateException("Staff still has flight assignments");
        }
        staffRepository.deleteById(id);
    }

    private void checkNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " must not be empty");
        }
    }
}
