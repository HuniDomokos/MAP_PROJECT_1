package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Luggage;

public class LuggageRepository extends InFileRepository<Luggage, String> {
    public LuggageRepository() {
        super("src/main/resources/data/luggage.json", Luggage.class, Luggage::getId);
    }
}