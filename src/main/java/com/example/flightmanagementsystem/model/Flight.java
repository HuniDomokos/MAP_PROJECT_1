package com.example.flightmanagementsystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Flight {
    private String id;
    private String name;
    private String noticeBoardId;
    private String airplaneId;
    private LocalDateTime departureTime;
    private final List<Ticket> tickets = new ArrayList<>();
    private final List<FlightAssignment> flightAssignments = new ArrayList<>();

    public Flight(String id, String name, String noticeBoardId, String airplaneId,
                  LocalDateTime departureTime) {
        this.id = id;
        this.name = name;
        this.noticeBoardId = noticeBoardId;
        this.airplaneId = airplaneId;
        this.departureTime = departureTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoticeBoardId() {
        return noticeBoardId;
    }

    public void setNoticeBoardId(String noticeBoardId) {
        this.noticeBoardId = noticeBoardId;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    public List<FlightAssignment> getFlightAssignments() {
        return Collections.unmodifiableList(flightAssignments);
    }

    public void addFlightAssignment(FlightAssignment assignment) {
        flightAssignments.add(assignment);
    }

    public void removeFlightAssignment(FlightAssignment assignment) {
        flightAssignments.remove(assignment);
    }
}
