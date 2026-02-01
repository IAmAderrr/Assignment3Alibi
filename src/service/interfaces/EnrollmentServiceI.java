package service.interfaces;

import model.Enrollment;

import java.util.List;

public interface EnrollmentServiceI {
    void create(Enrollment enrollment);
    List<Enrollment> getAll();
    Enrollment getById(int id);
    void update(int id, Enrollment enrollment);
    void delete(int id);
}
