package repository;

import model.Enrollment;
import model.Course;
import model.VideoCourse;
import model.LiveCourse;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import exception.DuplicateResourceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepository {

    public void create(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getCourse().getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DuplicateResourceException("Student already enrolled in this course");
        }
    }

    public List<Enrollment> getAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = """
            SELECT e.id, e.student_id,
                   c.id AS course_id, c.name, c.price, c.type,
                   c.video_hours, c.live_sessions
            FROM enrollments e
            JOIN courses c ON e.course_id = c.id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course course = mapCourse(rs);

                enrollments.add(
                        new Enrollment(
                                rs.getInt("id"),
                                rs.getInt("student_id"),
                                course
                        )
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch enrollments", e);
        }

        return enrollments;
    }

    public Enrollment getById(int id) {
        String sql = """
            SELECT e.id, e.student_id,
                   c.id AS course_id, c.name, c.price, c.type,
                   c.video_hours, c.live_sessions
            FROM enrollments e
            JOIN courses c ON e.course_id = c.id
            WHERE e.id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Course course = mapCourse(rs);

                return new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        course
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch enrollment", e);
        }

        throw new ResourceNotFoundException("Enrollment not found");
    }

    public void delete(int id) {
        String sql = "DELETE FROM enrollments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0)
                throw new ResourceNotFoundException("Enrollment not found");

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete enrollment", e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        int id = rs.getInt("course_id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String type = rs.getString("type");

        if ("VIDEO".equals(type))
            return new VideoCourse(id, name, price, null, rs.getInt("video_hours"));

        return new LiveCourse(id, name, price, null, rs.getInt("live_sessions"));
    }
}
