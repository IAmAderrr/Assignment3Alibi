package model;

import exception.InvalidInputException;

public class Instructor {
    private int id;
    private String name;

    public Instructor(int id, String name) {
        this.id = id;
        setName(name);
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Instructor name cannot be empty");
        }
        this.name = name;
    }
}