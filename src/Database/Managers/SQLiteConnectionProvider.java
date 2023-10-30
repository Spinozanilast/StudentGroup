package Database.Managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider {
    private static final String DB_URL = "jdbc:sqlite:D:\\3КУРС\\Java\\CourseWork\\src\\Database\\DatabaseFiles\\StudentGroupDB.sqlite";

    private boolean isConnectionClosed = false;
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
                isConnectionClosed = false;
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
                isConnectionClosed = true;
                System.out.println("Connection to SQLite database closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}