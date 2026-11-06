package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("passengers", passengerService.getAllPassengers());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String form() {
        return "passenger/form";
    }

    @PostMapping
    public String create(@RequestParam("name") String name,
                         @RequestParam("currency") String currency,
                         @RequestParam("email") String email) {
        passengerService.createPassenger(name, currency, email);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        passengerService.deletePassenger(id);
        return "redirect:/passengers";
    }
}
