package model;

import exception.InvalidInputException;

public abstract class Course {
    protected int id;
    protected String name;
    protected double price;
    protected Instructor instructor;

    public Course(int id, String name, double price, Instructor instructor) {
        this.id = id;
        setName(name);
        setPrice(price);
        this.instructor = instructor;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public abstract String getCourseType();
    public abstract double calculateFinalPrice();

    public String basicInfo() { return id + " - " + name; }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new InvalidInputException("Course name cannot be empty");
        this.name = name;
    }

    public void setPrice(double price) {
        if (price <= 0)
            throw new InvalidInputException("Price must be positive");
        this.price = price;
    }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
}