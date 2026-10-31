package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    private final AirplaneRepository airplaneRepository;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository) {
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    public Airplane createAirplane(int number, int capacity) {
        validate(capacity);
        Airplane airplane = new Airplane(UUID.randomUUID().toString(), number, capacity);
        return airplaneRepository.save(airplane);
    }

    @Override
    public List<Airplane> getAllAirplanes() {
        return airplaneRepository.findAll();
    }

    @Override
    public Airplane getAirplaneById(String id) {
        return airplaneRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Airplane not found: " + id));
    }

    @Override
    public Airplane updateAirplane(String id, int number, int capacity) {
        validate(capacity);
        Airplane airplane = getAirplaneById(id);
        airplane.setNumber(number);
        airplane.setCapacity(capacity);
        return airplaneRepository.save(airplane);
    }

    @Override
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
