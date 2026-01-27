package utils;

import exception.DatabaseOperationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaRunner {

    public static void run() {
        Path schemaPath = Path.of("resources", "schema.sql").toAbsolutePath();

        try {
            if (!Files.exists(schemaPath)) {
                throw new DatabaseOperationException("schema.sql not found at: " + schemaPath, null);
            }

            String sql = Files.readString(schemaPath);

            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement()) {

                for (String part : sql.split(";")) {
                    String s = part.trim();
                    if (!s.isEmpty()) {
                        stmt.executeUpdate(s);
                    }
                }
            }

            System.out.println("Schema executed successfully from: " + schemaPath);

        } catch (IOException | SQLException e) {
            throw new DatabaseOperationException("Failed to run schema.sql from: " + schemaPath + " | " + e.getMessage(), e);
        }
    }
}
