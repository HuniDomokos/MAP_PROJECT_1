package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.AirplaneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {
    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        return "airplane/index";
    }

    @GetMapping("/new")
    public String form() {
        return "airplane/form";
    }

    @PostMapping
    public String create(@RequestParam("number") int number,
                         @RequestParam("capacity") int capacity) {
        airplaneService.createAirplane(number, capacity);
        return "redirect:/airplanes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        airplaneService.deleteAirplane(id);
        return "redirect:/airplanes";
    }
}
