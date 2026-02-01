package service.interfaces;

import model.Instructor;

import java.util.List;

public interface InstructorServiceI {
    void create(Instructor instructor);
    List<Instructor> getAll();
    Instructor getById(int id);
    void update(int id, Instructor instructor);
    void delete(int id);
}
