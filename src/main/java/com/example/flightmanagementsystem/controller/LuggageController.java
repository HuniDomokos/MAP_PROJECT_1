package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.LuggageService;
import com.example.flightmanagementsystem.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/luggage")
public class LuggageController {
    private final LuggageService luggageService;
    private final TicketService ticketService;

    public LuggageController(LuggageService luggageService, TicketService ticketService) {
        this.luggageService = luggageService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("luggageList", luggageService.getAllLuggage());
        return "luggage/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "luggage/form";
    }

    @PostMapping
    public String create(@RequestParam("ticketId") String ticketId) {
        luggageService.registerLuggage(ticketId);
        return "redirect:/luggage";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        luggageService.deleteLuggage(id);
        return "redirect:/luggage";
    }
}
