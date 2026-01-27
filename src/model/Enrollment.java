package model;

import exception.InvalidInputException;
import interfaces.Payable;
import interfaces.Validatable;

public class Enrollment implements Validatable, Payable {
    private int id;
    private int studentId;
    private Course course;

    public Enrollment(Course course, int studentId) {
        this(0, studentId, course);
    }

    public Enrollment(int id, int studentId, Course course) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
        validate();
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public Course getCourse() { return course; }

    @Override
    public void validate() {
        if (studentId <= 0) throw new InvalidInputException("Invalid student ID");
        if (course == null) throw new InvalidInputException("Course cannot be null");
    }

    @Override
    public double getPayableAmount() {
        return course.calculateFinalPrice();
    }
}