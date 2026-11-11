package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Airplane;

public class AirplaneRepository extends InFileRepository<Airplane, String> {
    public AirplaneRepository() {
        super("src/main/resources/data/airplanes.json", Airplane.class, Airplane::getId);
    }
}