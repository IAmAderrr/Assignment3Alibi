import model.Course;
import model.VideoCourse;
import model.LiveCourse;
import repository.CourseRepository;

public class Main {
    public static void main(String[] args) {
        utils.SchemaRunner.run();
        CourseRepository courseRepo = new CourseRepository();

        Course c1 = new VideoCourse(0, "Java Basics", 100.0, null, 20);
        Course c2 = new LiveCourse(0, "Spring Boot Live", 150.0, null, 5);

        courseRepo.create(c1);
        courseRepo.create(c2);

        for (Course c : courseRepo.getAll()) {
            System.out.println(c.basicInfo() + " | " + c.getCourseType()
                    + " | final=" + c.calculateFinalPrice());
        }
    }
}