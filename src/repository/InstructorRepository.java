package repository;

import model.Instructor;
import utils.DatabaseConnection;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import exception.DuplicateResourceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorRepository implements repository.interfaces.InstructorRepositoryI {

    public void create(Instructor instructor) {
        String sql = "INSERT INTO instructors (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, instructor.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DuplicateResourceException("Instructor already exists");
        }
    }

    public List<Instructor> getAll() {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM instructors";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                instructors.add(
                    new Instructor(
                        rs.getInt("id"),
                        rs.getString("name")
                    )
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch instructors", e);
        }

        return instructors;
    }

    public Instructor getById(Integer id) {
        String sql = "SELECT * FROM instructors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Instructor(
                    rs.getInt("id"),
                    rs.getString("name")
                );
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch instructor", e);
        }

        throw new ResourceNotFoundException("Instructor not found");
    }

    public void update(Integer id, Instructor instructor) {
        String sql = "UPDATE instructors SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, instructor.getName());
            ps.setInt(2, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Instructor not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update instructor", e);
        }
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM instructors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() == 0) {
                throw new ResourceNotFoundException("Instructor not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete instructor", e);
        }
    }
}
