package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Airplane;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirplaneRepository implements CrudRepository<Airplane, String> {

    private final Map<String, Airplane> storage = new HashMap<>();

    @Override
    public Airplane save(Airplane airplane) {
        if (airplane != null && airplane.getId() != null) {
            storage.put(airplane.getId(), airplane);
        }
        return airplane;
    }

    @Override
    public Optional<Airplane> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Airplane> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}