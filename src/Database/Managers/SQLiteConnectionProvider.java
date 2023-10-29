package Database.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider {
    private static final String DB_URL = "jdbc:sqlite:/StudentGroupDB.db";

    private Connection connection;

    public SQLiteConnectionProvider() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connected to SQLite database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection to SQLite database closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}