package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Luggage;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LuggageRepository implements CrudRepository<Luggage, String> {

    private final Map<String, Luggage> storage = new HashMap<>();

    @Override
    public Luggage save(Luggage luggage) {
        if (luggage != null && luggage.getId() != null) {
            storage.put(luggage.getId(), luggage);
        }
        return luggage;
    }

    @Override
    public Optional<Luggage> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Luggage> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}