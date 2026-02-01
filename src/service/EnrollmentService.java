package service;

import exception.InvalidInputException;
import model.Enrollment;
import repository.interfaces.EnrollmentRepositoryI;
import service.interfaces.EnrollmentServiceI;

import java.util.List;

public class EnrollmentService implements EnrollmentServiceI {

    private final EnrollmentRepositoryI enrollmentRepo;

    public EnrollmentService(EnrollmentRepositoryI enrollmentRepo) {
        this.enrollmentRepo = enrollmentRepo;
    }

    @Override
    public void create(Enrollment enrollment) {
        if (enrollment == null) throw new InvalidInputException("Enrollment cannot be null");
        enrollment.validate(enrollment);
        enrollmentRepo.create(enrollment);
    }

    @Override
    public List<Enrollment> getAll() {
        return enrollmentRepo.getAll();
    }

    @Override
    public Enrollment getById(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid enrollment ID");
        return enrollmentRepo.getById(id);
    }

    @Override
    public void update(int id, Enrollment enrollment) {
        if (id <= 0) throw new InvalidInputException("Invalid enrollment ID");
        if (enrollment == null) throw new InvalidInputException("Enrollment cannot be null");
        enrollment.validate(enrollment);
        enrollmentRepo.update(id, enrollment);
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new InvalidInputException("Invalid enrollment ID");
        enrollmentRepo.delete(id);
    }
}
