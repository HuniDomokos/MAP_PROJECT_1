package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class AirplaneService {
    private final CrudRepository<Airplane, String> airplaneRepository;

    public AirplaneService(CrudRepository<Airplane, String> airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    public Airplane createAirplane(int number, int capacity) {
        validate(capacity);
        Airplane airplane = new Airplane(UUID.randomUUID().toString(), number, capacity);
        return airplaneRepository.save(airplane);
    }

    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    public Airplane getAirplaneById(String id) {
        return airplaneRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Airplane not found: " + id));
    }

    public Airplane updateAirplane(String id, int number, int capacity) {
        validate(capacity);
        Airplane airplane = getAirplaneById(id);
        airplane.setNumber(number);
        airplane.setCapacity(capacity);
        return airplaneRepository.save(airplane);
    }

    public void deleteAirplane(String id) {
        Airplane airplane = getAirplaneById(id);
        if (!airplane.getFlights().isEmpty()) {
            throw new IllegalStateException("Airplane still has flights");
        }
        airplaneRepository.deleteById(id);
    }

    private void validate(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
    }
}
