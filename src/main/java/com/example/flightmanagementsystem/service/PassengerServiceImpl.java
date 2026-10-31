package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger createPassenger(String name, String currency, String email) {
        validate(name, email);
        Passenger passenger = new Passenger(UUID.randomUUID().toString(), name, currency, email);
        return passengerRepository.save(passenger);
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger getPassengerById(String id) {
        return passengerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Passenger not found: " + id));
    }

    @Override
    public Passenger updatePassenger(String id, String name, String currency, String email) {
        validate(name, email);
        Passenger passenger = getPassengerById(id);
        passenger.setName(name);
        passenger.setCurrency(currency);
        passenger.setEmail(email);
        return passengerRepository.save(passenger);
    }

    @Override
    public void deletePassenger(String id) {
        Passenger passenger = getPassengerById(id);
        if (!passenger.getTickets().isEmpty()) {
            throw new IllegalStateException("Passenger still has tickets");
        }
        passengerRepository.deleteById(id);
    }

    private void validate(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
