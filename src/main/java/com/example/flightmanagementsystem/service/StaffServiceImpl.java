package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public AirlineEmployee createAirlineEmployee(String name, String role) {
        checkNotBlank(name, "Name");
        checkNotBlank(role, "Role");
        AirlineEmployee employee = new AirlineEmployee(UUID.randomUUID().toString(), name, role);
        staffRepository.save(employee);
        return employee;
    }

    @Override
    public AirportEmployee createAirportEmployee(String name, String designation, String department) {
        checkNotBlank(name, "Name");
        AirportEmployee employee = new AirportEmployee(UUID.randomUUID().toString(), name, designation, department);
        staffRepository.save(employee);
        return employee;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(String id) {
        return staffRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Staff not found: " + id));
    }

    @Override
    public void deleteStaff(String id) {
        Staff staff = getStaffById(id);
        if (staff instanceof AirlineEmployee employee && !employee.getAssignments().isEmpty()) {
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
