package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Airplane;
import java.util.List;

public interface AirplaneService {
    Airplane createAirplane(int number, int capacity);
    List<Airplane> getAllAirplanes();
    Airplane getAirplaneById(String id);
    Airplane updateAirplane(String id, int number, int capacity);
    void deleteAirplane(String id);
}
