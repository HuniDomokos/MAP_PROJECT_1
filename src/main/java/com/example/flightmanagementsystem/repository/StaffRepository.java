package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Staff;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StaffRepository implements CrudRepository<Staff, String> {

    private final Map<String, Staff> storage = new HashMap<>();

    @Override
    public Staff save(Staff staff) {
        if (staff != null && staff.getId() != null) {
            storage.put(staff.getId(), staff);
        }
        return staff;
    }

    @Override
    public Optional<Staff> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Staff> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}