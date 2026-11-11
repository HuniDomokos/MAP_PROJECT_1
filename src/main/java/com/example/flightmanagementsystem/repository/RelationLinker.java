package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.*;
import org.springframework.stereotype.Component;

@Component
public class RelationLinker {

    public RelationLinker(CrudRepository<Airplane, String> airplanes,
                          CrudRepository<NoticeBoard, String> boards,
                          CrudRepository<Passenger, String> passengers,
                          CrudRepository<Flight, String> flights,
                          CrudRepository<Ticket, String> tickets,
                          CrudRepository<Luggage, String> luggage,
                          CrudRepository<Staff, String> staff,
                          CrudRepository<FlightAssignment, String> assignments) {

        for (Flight f : flights.findAll()) {
            airplanes.findById(f.getAirplaneId()).ifPresent(a -> a.addFlight(f));
            boards.findById(f.getNoticeBoardId()).ifPresent(b -> b.addFlight(f));
        }
        for (Ticket t : tickets.findAll()) {
            passengers.findById(t.getPassengerId()).ifPresent(p -> p.addTicket(t));
            flights.findById(t.getFlightId()).ifPresent(f -> f.addTicket(t));
        }
        for (Luggage l : luggage.findAll()) {
            tickets.findById(l.getTicketId()).ifPresent(t -> t.addLuggage(l));
        }
        for (FlightAssignment a : assignments.findAll()) {
            flights.findById(a.getFlightId()).ifPresent(f -> f.addFlightAssignment(a));
            staff.findById(a.getStaffId()).ifPresent(s -> {
                if (s instanceof AirlineEmployee e) e.addAssignment(a);
            });
        }
    }
}