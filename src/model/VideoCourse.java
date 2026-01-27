package model;

public class VideoCourse extends Course {
    private int videoHours;

    public VideoCourse(int id, String name, double price, Instructor instructor, int videoHours) {
        super(id, name, price, instructor);
        this.videoHours = videoHours;
    }

    public int getVideoHours() {
        return videoHours;
    }

    @Override
    public String getCourseType() {
        return "VIDEO";
    }

    @Override
    public double calculateFinalPrice() {
        return price;
    }
}