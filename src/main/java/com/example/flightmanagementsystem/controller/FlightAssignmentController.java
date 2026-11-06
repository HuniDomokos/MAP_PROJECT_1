package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.FlightAssignmentService;
import com.example.flightmanagementsystem.service.FlightService;
import com.example.flightmanagementsystem.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/flight-assignments")
public class FlightAssignmentController {
    private final FlightAssignmentService assignmentService;
    private final FlightService flightService;
    private final StaffService staffService;

    public FlightAssignmentController(FlightAssignmentService assignmentService,
                                      FlightService flightService,
                                      StaffService staffService) {
        this.assignmentService = assignmentService;
        this.flightService = flightService;
        this.staffService = staffService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assignments", assignmentService.getAllAssignments());
        return "flightassignment/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("staffMembers", staffService.getAllStaff());
        return "flightassignment/form";
    }

    @PostMapping
    public String create(@RequestParam("flightId") String flightId,
                         @RequestParam("staffId") String staffId) {
        assignmentService.assignStaff(flightId, staffId);
        return "redirect:/flight-assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        assignmentService.removeAssignment(id);
        return "redirect:/flight-assignments";
    }
}
