package com.example.flightmanagementsystem.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoticeBoard {
    private String id;
    private LocalDate date;
    private final List<Flight> flightsOfTheDay = new ArrayList<>();

    public NoticeBoard(String id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Flight> getFlightsOfTheDay() {
        return Collections.unmodifiableList(flightsOfTheDay);
    }

    public void addFlight(Flight flight) {
        flightsOfTheDay.add(flight);
    }

    public void removeFlight(Flight flight) {
        flightsOfTheDay.remove(flight);
    }
}
