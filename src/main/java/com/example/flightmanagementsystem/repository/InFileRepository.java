package com.example.flightmanagementsystem.repository;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InFileRepository<T, ID> implements CrudRepository<T, ID> {

    private final String filePath;
    private final Class<T> entityType;
    private final Function<T, ID> idExtractor;
    private final Map<ID, T> storage = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public InFileRepository(String filePath, Class<T> entityType, Function<T, ID> idExtractor) {
        this.filePath = filePath;
        this.entityType = entityType;
        this.idExtractor = idExtractor;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        loadFromFile();
    }

    private synchronized void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }

        try {
            JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, entityType);
            List<T> entities = objectMapper.readValue(file, listType);
            storage.clear();
            if (entities != null) {
                for (T entity : entities) {
                    ID id = idExtractor.apply(entity);
                    if (id != null) {
                        storage.put(id, entity);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read data from file: " + filePath, e);
        }
    }

    private synchronized void saveToFile() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            List<T> entities = new ArrayList<>(storage.values());
            objectMapper.writeValue(file, entities);
        } catch (IOException e) {
            throw new RuntimeException("Could not write data to file: " + filePath, e);
        }
    }

    @Override
    public synchronized T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }
        ID id = idExtractor.apply(entity);
        if (id == null) {
            throw new IllegalArgumentException("Entity id must not be null");
        }

        storage.put(id, entity);
        saveToFile();
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return id == null ? Optional.empty() : Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public synchronized boolean deleteById(ID id) {
        if (id != null && storage.remove(id) != null) {
            saveToFile();
            return true;
        }
        return false;
    }
}