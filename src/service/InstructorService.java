package service;

import exception.InvalidInputException;
import model.Instructor;
import repository.interfaces.InstructorRepositoryI;
import service.interfaces.InstructorServiceI;

import java.util.List;

public class InstructorService implements InstructorServiceI {

    private final InstructorRepositoryI instructorRepo;

    public InstructorService(InstructorRepositoryI instructorRepo) {
        this.instructorRepo = instructorRepo;
    }

    @Override
    public void create(Instructor instructor) {
        if (instructor == null) throw new InvalidInputException("Instructor cannot be null");
        if (instructor.getName() == null || instructor.getName().isBlank()) {
            throw new InvalidInputException("Instructor name cannot be empty");
        }
        instructorRepo.create(instructor);
    }

    @Override
    public List<Instructor> getAll() {
        return instructorRepo.getAll();
    }

    @Override
    public Instructor getById(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid instructor ID");
        return instructorRepo.getById(id);
    }

    @Override
    public void update(int id, Instructor instructor) {
        if (id <= 0) throw new InvalidInputException("Invalid instructor ID");
        if (instructor == null) throw new InvalidInputException("Instructor cannot be null");
        if (instructor.getName() == null || instructor.getName().isBlank()) {
            throw new InvalidInputException("Instructor name cannot be empty");
        }
        instructorRepo.update(id, instructor);
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid instructor ID");
        instructorRepo.delete(id);
    }
}
