package com.example.flightmanagementsystem.model;

@com.fasterxml.jackson.annotation.JsonTypeInfo(
        use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME,
        property = "@type"
)
@com.fasterxml.jackson.annotation.JsonSubTypes({
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = AirlineEmployee.class, name = "airline"),
        @com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = AirportEmployee.class, name = "airport")
})

public abstract class Staff {
    private String id;
    private String name;

    protected Staff() {
    }

    protected Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
