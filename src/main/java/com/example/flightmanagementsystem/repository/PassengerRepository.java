package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PassengerRepository implements CrudRepository<Passenger, String> {

    private final Map<String, Passenger> storage = new HashMap<>();

    @Override
    public Passenger save(Passenger passenger) {
        if (passenger != null && passenger.getId() != null) {
            storage.put(passenger.getId(), passenger);
        }
        return passenger;
    }

    @Override
    public Optional<Passenger> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Passenger> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}