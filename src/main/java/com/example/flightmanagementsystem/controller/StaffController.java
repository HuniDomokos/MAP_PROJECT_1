package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.AirlineEmployee;
import com.example.flightmanagementsystem.model.AirportEmployee;
import com.example.flightmanagementsystem.model.Staff;
import com.example.flightmanagementsystem.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String index(Model model) {
        List<Staff> allStaff = staffService.getAllStaff();
        model.addAttribute("airlineEmployees", allStaff.stream()
                .filter(s -> s instanceof AirlineEmployee)
                .map(s -> (AirlineEmployee) s)
                .toList());
        model.addAttribute("airportEmployees", allStaff.stream()
                .filter(s -> s instanceof AirportEmployee)
                .map(s -> (AirportEmployee) s)
                .toList());
        return "staff/index";
    }

    @GetMapping("/new")
    public String form() {
        return "staff/form";
    }

    @PostMapping
    public String create(@RequestParam("type") String type,
                         @RequestParam("name") String name,
                         @RequestParam(value = "role", required = false) String role,
                         @RequestParam(value = "designation", required = false) String designation,
                         @RequestParam(value = "department", required = false) String department) {
        if ("AIRLINE".equals(type)) {
            staffService.createAirlineEmployee(name, role);
        } else {
            staffService.createAirportEmployee(name, designation, department);
        }
        return "redirect:/staff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        staffService.deleteStaff(id);
        return "redirect:/staff";
    }
}
