package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.Ticket;
import com.example.flightmanagementsystem.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public record TicketRequest(String passengerId, String flightId, String seatNumber, double price) {}

    @GetMapping
    public List<Ticket> getAll() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public Ticket getById(@PathVariable("id") String id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/flight/{flightId}")
    public List<Ticket> getByFlight(@PathVariable("flightId") String flightId) {
        return ticketService.getTicketsByFlight(flightId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ticket book(@RequestBody TicketRequest request) {
        return ticketService.bookTicket(request.passengerId(), request.flightId(),
                request.seatNumber(), request.price());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable("id") String id) {
        ticketService.cancelTicket(id);
    }
}
