package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Luggage;
import com.example.flightmanagementsystem.service.LuggageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/luggage")
public class LuggageController {
    private final LuggageService luggageService;

    public LuggageController(LuggageService luggageService) {
        this.luggageService = luggageService;
    }

    public record LuggageRequest(String ticketId) {}
    public record StatusRequest(Luggage.Status status) {}

    @GetMapping
    public List<Luggage> getAll() {
        return luggageService.getAllLuggage();
    }

    @GetMapping("/{id}")
    public Luggage getById(@PathVariable("id") String id) {
        return luggageService.getLuggageById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Luggage register(@RequestBody LuggageRequest request) {
        return luggageService.registerLuggage(request.ticketId());
    }

    @PutMapping("/{id}/status")
    public Luggage updateStatus(@PathVariable("id") String id, @RequestBody StatusRequest request) {
        return luggageService.updateStatus(id, request.status());
    }
}
