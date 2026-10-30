package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.FlightAssignment;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FlightAssignmentRepository implements CrudRepository<FlightAssignment, String> {

    private final Map<String, FlightAssignment> storage = new HashMap<>();

    @Override
    public FlightAssignment save(FlightAssignment assignment) {
        if (assignment != null && assignment.getId() != null) {
            storage.put(assignment.getId(), assignment);
        }
        return assignment;
    }

    @Override
    public Optional<FlightAssignment> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<FlightAssignment> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}