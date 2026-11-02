package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Airplane;
import com.example.flightmanagementsystem.service.AirplaneService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController {
    private final AirplaneService airplaneService;

    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    public record AirplaneRequest(int number, int capacity) {}

    @GetMapping
    public List<Airplane> getAll() {
        return airplaneService.getAllAirplanes();
    }

    @GetMapping("/{id}")
    public Airplane getById(@PathVariable("id") String id) {
        return airplaneService.getAirplaneById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Airplane create(@RequestBody AirplaneRequest request) {
        return airplaneService.createAirplane(request.number(), request.capacity());
    }

    @PutMapping("/{id}")
    public Airplane update(@PathVariable("id") String id, @RequestBody AirplaneRequest request) {
        return airplaneService.updateAirplane(id, request.number(), request.capacity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        airplaneService.deleteAirplane(id);
    }
}
