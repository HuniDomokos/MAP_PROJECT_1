package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Flight;

public class FlightRepository extends InFileRepository<Flight, String> {
    public FlightRepository() {
        super("src/main/resources/data/flights.json", Flight.class, Flight::getId);
    }
}