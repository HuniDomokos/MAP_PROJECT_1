package com.example.flightmanagementsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Airplane {
    private String id;
    private int number;
    private int capacity;
    private final List<Flight> flights = new ArrayList<>();

    public Airplane(String id, int number, int capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void removeFlight(Flight flight) {
        flights.remove(flight);
    }
}
