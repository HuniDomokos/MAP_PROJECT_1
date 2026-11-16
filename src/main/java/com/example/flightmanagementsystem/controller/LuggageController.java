package com.example.flightmanagementsystem.controller;

import java.util.List;
import java.util.Arrays;
import com.example.flightmanagementsystem.model.Luggage;
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

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        model.addAttribute("luggage", luggageService.getLuggageById(id));
        return "luggage/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") String id, Model model) {
        Luggage luggage = luggageService.getLuggageById(id);
        // only the current status and the statuses it may change to
        List<Luggage.Status> statuses = Arrays.stream(Luggage.Status.values())
                .filter(s -> s == luggage.getStatus() || luggage.getStatus().canChangeTo(s))
                .toList();
        model.addAttribute("luggage", luggage);
        model.addAttribute("statuses", statuses);
        return "luggage/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") String id,
                         @RequestParam("status") Luggage.Status status) {
        luggageService.updateStatus(id, status);
        return "redirect:/luggage";
    }
}
