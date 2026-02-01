package model;

import exception.InvalidInputException;
import interfaces.Payable;
import interfaces.Validatable;

public class Enrollment implements Validatable<Enrollment>, Payable {
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
        validated(this); 
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public Course getCourse() { return course; }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public void validate(Enrollment e) {
        if (e.studentId <= 0) throw new InvalidInputException("Invalid student ID");
        if (e.course == null) throw new InvalidInputException("Course cannot be null");
        if (e.course.getId() <= 0) throw new InvalidInputException("Course must be persisted before enrollment");
    }

    @Override
    public double calculateFinalPrice() {
        return course.calculateFinalPrice();
    }
}
