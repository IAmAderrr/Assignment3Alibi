package service;

import exception.InvalidInputException;
import model.Course;
import repository.interfaces.CourseRepositoryI;
import repository.interfaces.EnrollmentRepositoryI;
import service.interfaces.CourseServiceI;
import utils.SortingUtils;

import java.util.Comparator;
import java.util.List;

public class CourseService implements CourseServiceI {

    private final CourseRepositoryI courseRepo;
    private final EnrollmentRepositoryI enrollmentRepo;

    public CourseService(CourseRepositoryI courseRepo, EnrollmentRepositoryI enrollmentRepo) {
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    @Override
    public void create(Course course) {
        if (course == null) throw new InvalidInputException("Course cannot be null");
        courseRepo.create(course);
    }

    @Override
    public List<Course> getAll() {
        return courseRepo.getAll();
    }

    @Override
    public List<Course> getAllSorted(Comparator<Course> comparator) {
        List<Course> courses = courseRepo.getAll();
        return SortingUtils.sortedCopy(courses, comparator);
    }

    @Override
    public Course getById(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid course ID");
        return courseRepo.getById(id);
    }

    @Override
    public void update(int id, Course course) {
        if (id <= 0) throw new InvalidInputException("Invalid course ID");
        if (course == null) throw new InvalidInputException("Course cannot be null");
        courseRepo.update(id, course);
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid course ID");
        if (enrollmentRepo.existsByCourseId(id)) {
            throw new InvalidInputException("Cannot delete course: enrollments exist (FK constraint)");
        }
        courseRepo.delete(id);
    }
}
