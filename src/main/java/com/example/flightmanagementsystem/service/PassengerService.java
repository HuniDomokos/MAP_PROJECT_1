package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.Passenger;
import java.util.List;

public interface PassengerService {
    Passenger createPassenger(String name, String currency, String email);
    List<Passenger> getAllPassengers();
    Passenger getPassengerById(String id);
    Passenger updatePassenger(String id, String name, String currency, String email);
    void deletePassenger(String id);
}
