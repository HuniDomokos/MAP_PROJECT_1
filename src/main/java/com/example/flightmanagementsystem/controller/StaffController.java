package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.AirlineEmployee;
import com.example.flightmanagementsystem.model.AirportEmployee;
import com.example.flightmanagementsystem.model.Staff;
import com.example.flightmanagementsystem.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    public record AirlineEmployeeRequest(String name, String role) {}
    public record AirportEmployeeRequest(String name, String designation, String department) {}

    @GetMapping
    public List<Staff> getAll() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    public Staff getById(@PathVariable("id") String id) {
        return staffService.getStaffById(id);
    }

    @PostMapping("/airline-employees")
    @ResponseStatus(HttpStatus.CREATED)
    public AirlineEmployee createAirlineEmployee(@RequestBody AirlineEmployeeRequest request) {
        return staffService.createAirlineEmployee(request.name(), request.role());
    }

    @PostMapping("/airport-employees")
    @ResponseStatus(HttpStatus.CREATED)
    public AirportEmployee createAirportEmployee(@RequestBody AirportEmployeeRequest request) {
        return staffService.createAirportEmployee(request.name(), request.designation(), request.department());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        staffService.deleteStaff(id);
    }
}
