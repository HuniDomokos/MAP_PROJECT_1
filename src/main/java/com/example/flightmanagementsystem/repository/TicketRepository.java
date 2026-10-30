package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TicketRepository implements CrudRepository<Ticket, String> {

    private final Map<String, Ticket> storage = new HashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket != null && ticket.getId() != null) {
            storage.put(ticket.getId(), ticket);
        }
        return ticket;
    }

    @Override
    public Optional<Ticket> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}