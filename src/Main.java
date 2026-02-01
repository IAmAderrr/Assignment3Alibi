import controller.CourseController;
import controller.EnrollmentController;
import controller.InstructorController;
import exception.InvalidInputException;
import model.Course;
import model.Enrollment;
import model.Instructor;
import model.LiveCourse;
import model.VideoCourse;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.InstructorRepository;
import service.CourseService;
import service.EnrollmentService;
import service.InstructorService;
import utils.ReflectionUtils;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        utils.SchemaRunner.run();

        CourseRepository courseRepo = new CourseRepository();
        InstructorRepository instructorRepo = new InstructorRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();

        CourseService courseService = new CourseService(courseRepo, enrollmentRepo);
        InstructorService instructorService = new InstructorService(instructorRepo);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo);

        CourseController courseController = new CourseController(courseService);
        InstructorController instructorController = new InstructorController(instructorService);
        EnrollmentController enrollmentController = new EnrollmentController(enrollmentService);

        System.out.println("== Create instructors ==");
        try {
            instructorController.createInstructor(new Instructor(0, "Alice"));
            instructorController.createInstructor(new Instructor(0, "Bob"));
        } catch (Exception e) {
            System.out.println("Instructor create error: " + e.getMessage());
        }

        Instructor alice = instructorController.listInstructors().stream()
                .filter(i -> i.getName().equals("Alice"))
                .findFirst()
                .orElse(null);

        System.out.println("\n== Create courses (polymorphism: base type references) ==");
        Course c1 = new VideoCourse(0, "Java Basics", 100.0, alice, 20);
        Course c2 = new LiveCourse(0, "Spring Boot Live", 150.0, alice, 5);

        courseController.createCourse(c1);
        courseController.createCourse(c2);

        System.out.println("\n== List courses ==");
        for (Course c : courseController.listCourses()) {
            System.out.println(c.basicInfo() + " | " + c.getCourseType()
                    + " | final=" + c.calculateFinalPrice()
                    + " | instructor=" + (c.getInstructor() == null ? "-" : c.getInstructor().getName()));
        }

        System.out.println("\n== Sort courses by final price (lambda) ==");
        courseController.listCoursesSorted(Comparator.comparingDouble(Course::calculateFinalPrice))
                .forEach(c -> System.out.println(c.basicInfo() + " -> " + c.calculateFinalPrice()));

        System.out.println("\n== Reflection/RTTI demo ==");
        ReflectionUtils.inspect(c1);

        System.out.println("\n== Enrollment CRUD demo ==");
        Course firstCourse = courseController.listCourses().get(0);
        Enrollment e1 = new Enrollment(firstCourse, 1);
        enrollmentController.createEnrollment(e1);

        System.out.println("Enrollments count: " + enrollmentController.listEnrollments().size());

        System.out.println("\n== Trigger validation failure (service layer) ==");
        try {
            enrollmentController.createEnrollment(new Enrollment(firstCourse, -10));
        } catch (InvalidInputException ex) {
            System.out.println("Validation error: " + ex.getMessage());
        }

        System.out.println("\n== Trigger FK-style delete protection (service layer) ==");
        try {
            courseController.deleteCourse(firstCourse.getId());
        } catch (InvalidInputException ex) {
            System.out.println("Delete blocked: " + ex.getMessage());
        }

        System.out.println("\nDone.");
    }
}
