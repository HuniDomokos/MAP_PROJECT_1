package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Ticket;
import java.util.List;

public interface TicketService {
    Ticket bookTicket(String passengerId, String flightId, String seatNumber, double price);
    void cancelTicket(String ticketId);
    List<Ticket> getAllTickets();
    Ticket getTicketById(String id);
    List<Ticket> getTicketsByFlight(String flightId);
}
