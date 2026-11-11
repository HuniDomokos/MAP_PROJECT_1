package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Ticket;

public class TicketRepository extends InFileRepository<Ticket, String> {
    public TicketRepository() {
        super("src/main/resources/data/tickets.json", Ticket.class, Ticket::getId);
    }
}