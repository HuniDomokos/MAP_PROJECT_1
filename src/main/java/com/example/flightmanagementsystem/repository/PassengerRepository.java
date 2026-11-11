package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Passenger;

public class PassengerRepository extends InFileRepository<Passenger, String> {
    public PassengerRepository() {
        super("src/main/resources/data/passengers.json", Passenger.class, Passenger::getId);
    }
}