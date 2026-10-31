package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import java.util.List;

public interface StaffService {
    AirlineEmployee createAirlineEmployee(String name, String role);
    AirportEmployee createAirportEmployee(String name, String designation, String department);
    List<Staff> getAllStaff();
    Staff getStaffById(String id);
    void deleteStaff(String id);
}
