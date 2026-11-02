package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Passenger;
import com.example.flightmanagementsystem.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public record PassengerRequest(String name, String currency, String email) {}

    @GetMapping
    public List<Passenger> getAll() {
        return passengerService.getAllPassengers();
    }

    @GetMapping("/{id}")
    public Passenger getById(@PathVariable("id") String id) {
        return passengerService.getPassengerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger create(@RequestBody PassengerRequest request) {
        return passengerService.createPassenger(request.name(), request.currency(), request.email());
    }

    @PutMapping("/{id}")
    public Passenger update(@PathVariable("id") String id, @RequestBody PassengerRequest request) {
        return passengerService.updatePassenger(id, request.name(), request.currency(), request.email());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        passengerService.deletePassenger(id);
    }
}
