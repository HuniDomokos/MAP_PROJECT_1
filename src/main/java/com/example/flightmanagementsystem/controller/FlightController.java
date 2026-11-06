package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.AirplaneService;
import com.example.flightmanagementsystem.service.FlightService;
import com.example.flightmanagementsystem.service.NoticeBoardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;
    private final AirplaneService airplaneService;
    private final NoticeBoardService noticeBoardService;

    public FlightController(FlightService flightService,
                            AirplaneService airplaneService,
                            NoticeBoardService noticeBoardService) {
        this.flightService = flightService;
        this.airplaneService = airplaneService;
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        return "flight/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
        return "flight/form";
    }

    @PostMapping
    public String create(@RequestParam("name") String name,
                         @RequestParam("airplaneId") String airplaneId,
                         @RequestParam("noticeBoardId") String noticeBoardId,
                         @RequestParam("departureTime")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime) {
        flightService.createFlight(name, airplaneId, noticeBoardId, departureTime);
        return "redirect:/flights";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        flightService.deleteFlight(id);
        return "redirect:/flights";
    }
}
