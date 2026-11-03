package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class LuggageService {
    private final CrudRepository<Luggage, String> luggageRepository;
    private final CrudRepository<Ticket, String> ticketRepository;

    public LuggageService(CrudRepository<Luggage, String> luggageRepository, CrudRepository<Ticket, String> ticketRepository) {
        this.luggageRepository = luggageRepository;
        this.ticketRepository = ticketRepository;
    }

    public Luggage registerLuggage(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + ticketId));
        Luggage luggage = new Luggage(UUID.randomUUID().toString(), ticketId, Luggage.Status.CHECKED_IN);
        luggageRepository.save(luggage);
        ticket.addLuggage(luggage);
        return luggage;
    }

    public Luggage updateStatus(String luggageId, Luggage.Status newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status must not be null");
        }
        Luggage luggage = getLuggageById(luggageId);
        if (!luggage.getStatus().canChangeTo(newStatus)) {
            throw new IllegalStateException(
                "Invalid transition: " + luggage.getStatus() + " -> " + newStatus);
        }
        luggage.setStatus(newStatus);
        return luggageRepository.save(luggage);
    }

    public List<Luggage> getAllLuggage() {
        return luggageRepository.findAll();
    }

    public Luggage getLuggageById(String id) {
        return luggageRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Luggage not found: " + id));
    }
}
