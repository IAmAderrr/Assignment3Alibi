package model;

public class LiveCourse extends Course {
    private int liveSessions;

    public LiveCourse(int id, String name, double price, Instructor instructor, int liveSessions) {
        super(id, name, price, instructor);
        this.liveSessions = liveSessions;
    }

    public int getLiveSessions() {
        return liveSessions;
    }

    @Override
    public String getCourseType() {
        return "LIVE";
    }

    @Override
    public double calculateFinalPrice() {
        return price * 1.2;
    }
}