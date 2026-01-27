package repository;

import exception.DatabaseOperationException;
import exception.DuplicateResourceException;
import exception.ResourceNotFoundException;
import model.Course;
import model.Instructor;
import model.LiveCourse;
import model.VideoCourse;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {

    public void create(Course course) {
        String sql = """
            INSERT INTO courses (name, price, type, instructor_id, video_hours, live_sessions)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getName());
            ps.setDouble(2, course.getPrice());
            ps.setString(3, course.getCourseType());

            if (course.getInstructor() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, course.getInstructor().getId());
            }

            // subclass-specific columns
            if (course instanceof VideoCourse vc) {
                ps.setInt(5, vc.getVideoHours());
                ps.setNull(6, Types.INTEGER);
            } else if (course instanceof LiveCourse lc) {
                ps.setNull(5, Types.INTEGER);
                ps.setInt(6, lc.getLiveSessions());
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            String msg = (e.getMessage() == null) ? "" : e.getMessage().toLowerCase();

            if (msg.contains("unique") || msg.contains("constraint")) {
                throw new DuplicateResourceException("Course already exists");
            }
            throw new DatabaseOperationException("Create course failed: " + e.getMessage(), e);
        }

    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();

        String sql = """
            SELECT c.id, c.name, c.price, c.type, c.instructor_id,
                   c.video_hours, c.live_sessions,
                   i.name AS instructor_name
            FROM courses c
            LEFT JOIN instructors i ON c.instructor_id = i.id
            ORDER BY c.id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(mapCourse(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch courses", e);
        }

        return courses;
    }

    public Course getById(int id) {
        String sql = """
            SELECT c.id, c.name, c.price, c.type, c.instructor_id,
                   c.video_hours, c.live_sessions,
                   i.name AS instructor_name
            FROM courses c
            LEFT JOIN instructors i ON c.instructor_id = i.id
            WHERE c.id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCourse(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch course", e);
        }

        throw new ResourceNotFoundException("Course not found");
    }

    public void update(int id, Course course) {
        String sql = """
            UPDATE courses
            SET name = ?, price = ?, type = ?, instructor_id = ?, video_hours = ?, live_sessions = ?
            WHERE id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getName());
            ps.setDouble(2, course.getPrice());
            ps.setString(3, course.getCourseType());

            if (course.getInstructor() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, course.getInstructor().getId());
            }

            if (course instanceof VideoCourse vc) {
                ps.setInt(5, vc.getVideoHours());
                ps.setNull(6, Types.INTEGER);
            } else if (course instanceof LiveCourse lc) {
                ps.setNull(5, Types.INTEGER);
                ps.setInt(6, lc.getLiveSessions());
            } else {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Course not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update course", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Course not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete course", e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        int courseId = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String type = rs.getString("type");

        Integer instructorId = (Integer) rs.getObject("instructor_id");
        String instructorName = rs.getString("instructor_name");

        Instructor instructor = null;
        if (instructorId != null) {
            instructor = new Instructor(instructorId, instructorName);
        }

        if ("VIDEO".equalsIgnoreCase(type)) {
            int hours = rs.getInt("video_hours"); // 0 if NULL in SQLite; OK for demo
            return new VideoCourse(courseId, name, price, instructor, hours);
        }

        if ("LIVE".equalsIgnoreCase(type)) {
            int sessions = rs.getInt("live_sessions");
            return new LiveCourse(courseId, name, price, instructor, sessions);
        }

        throw new DatabaseOperationException("Unknown course type in DB: " + type, null);
    }
}
