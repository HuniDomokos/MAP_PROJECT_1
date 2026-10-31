package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class LuggageServiceImpl implements LuggageService {
    private final LuggageRepository luggageRepository;
    private final TicketRepository ticketRepository;

    public LuggageServiceImpl(LuggageRepository luggageRepository, TicketRepository ticketRepository) {
        this.luggageRepository = luggageRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Luggage registerLuggage(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new IllegalArgumentException("Ticket not found: " + ticketId));
        Luggage luggage = new Luggage(UUID.randomUUID().toString(), ticketId, "CheckedIn");
        luggageRepository.save(luggage);
        ticket.addLuggage(luggage);
        return luggage;
    }

    @Override
    public Luggage updateStatus(String luggageId, String newStatus) {
        Luggage luggage = getLuggageById(luggageId);
        boolean valid =
            (luggage.getStatus().equals("CheckedIn") && newStatus.equals("Loaded")) ||
            (luggage.getStatus().equals("Loaded") && newStatus.equals("Delivered"));
        if (!valid) {
            throw new IllegalStateException(
                "Invalid transition: " + luggage.getStatus() + " -> " + newStatus);
        }
        luggage.setStatus(newStatus);
        return luggageRepository.save(luggage);
    }

    @Override
    public List<Luggage> getAllLuggage() {
        return luggageRepository.findAll();
    }

    @Override
    public Luggage getLuggageById(String id) {
        return luggageRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Luggage not found: " + id));
    }
}
