package service.interfaces;

import model.Course;

import java.util.Comparator;
import java.util.List;

public interface CourseServiceI {
    void create(Course course);
    List<Course> getAll();
    List<Course> getAllSorted(Comparator<Course> comparator);
    Course getById(int id);
    void update(int id, Course course);
    void delete(int id);
}
