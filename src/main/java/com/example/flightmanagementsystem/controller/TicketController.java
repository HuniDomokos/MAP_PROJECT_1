package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.FlightService;
import com.example.flightmanagementsystem.service.PassengerService;
import com.example.flightmanagementsystem.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final FlightService flightService;

    public TicketController(TicketService ticketService,
                            PassengerService passengerService,
                            FlightService flightService) {
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("passengers", passengerService.getAllPassengers());
        model.addAttribute("flights", flightService.getAllFlights());
        return "ticket/form";
    }

    @PostMapping
    public String create(@RequestParam("passengerId") String passengerId,
                         @RequestParam("flightId") String flightId,
                         @RequestParam("seatNumber") String seatNumber,
                         @RequestParam("price") double price) {
        ticketService.bookTicket(passengerId, flightId, seatNumber, price);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        ticketService.cancelTicket(id);
        return "redirect:/tickets";
    }
}
