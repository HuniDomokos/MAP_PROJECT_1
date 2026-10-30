package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Flight;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FlightRepository implements CrudRepository<Flight, String> {

    private final Map<String, Flight> storage = new HashMap<>();

    @Override
    public Flight save(Flight flight) {
        if (flight != null && flight.getId() != null) {
            storage.put(flight.getId(), flight);
        }
        return flight;
    }

    @Override
    public Optional<Flight> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Flight> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}