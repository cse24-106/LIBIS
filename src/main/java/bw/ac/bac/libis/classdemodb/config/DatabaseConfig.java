package bw.ac.bac.libis.classdemodb.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Context context = new InitialContext();
                dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            } catch (Exception e) {
                throw new RuntimeException("Failed to lookup DataSource", e);
            }
        }
        return dataSource;
    }

    public static boolean testConnection() {
        try (Connection conn = getDataSource().getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test FAILED: " + e.getMessage());
            return false;
        }
    }
}
