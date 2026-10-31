package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Luggage;
import java.util.List;

public interface LuggageService {
    Luggage registerLuggage(String ticketId);
    Luggage updateStatus(String luggageId, String newStatus);
    List<Luggage> getAllLuggage();
    Luggage getLuggageById(String id);
}
